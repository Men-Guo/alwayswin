package com.example.alwayswin.service.Impl;

import com.example.alwayswin.entity.Bidding;
import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductStatus;
import com.example.alwayswin.mapper.BiddingMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.service.BiddingService;
import com.example.alwayswin.service.impl.BiddingServiceImpl;
import org.apache.commons.collections.map.HashedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BiddingServiceImplTest {
    private static BiddingService biddingService;
    @Mock
    private BiddingMapper biddingMapper;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    public void init() {
        biddingMapper = mock(BiddingMapper.class);
        productMapper = mock(ProductMapper.class);
        biddingService = new BiddingServiceImpl(biddingMapper, productMapper);
    }


    //////////       getByBid           //////////////
    @Test
    public void happyPathWithGetByBid() {
        Bidding bidding = new Bidding(1, 1,1,1, new Timestamp(0));
        when(biddingMapper.getByBid(anyInt())).thenReturn(bidding);

        assertEquals(1, biddingService.getByBid(1).getOffer());
    }

    @Test
    public void exceptionWithGetByBid() {
        when(biddingMapper.getByBid(anyInt())).thenReturn(null);
        assertNull(biddingService.getByBid(0));
    }

    //////////       getByUid           //////////////
    @Test
    public void happyPathWithGetByUid() {
        Bidding bidding = new Bidding(1, 1,1,1, new Timestamp(0));
        List<Bidding> biddingList = new ArrayList<>();
        biddingList.add(bidding);

        when(biddingMapper.getByUid(anyInt())).thenReturn(biddingList);

        assertEquals(1, biddingService.getBidsByUid(1).size());
    }

    @Test
    public void exceptionWithGetByUid() {
        when(biddingMapper.getByUid(anyInt())).thenReturn(null);
        assertNull(biddingService.getBidsByUid(0));
    }

    //////////       getByPid           //////////////
    @Test
    public void happyPathWithGetByPid() {
        Bidding bidding = new Bidding(1, 1,1,1, new Timestamp(0));
        List<Bidding> biddingList = new ArrayList<>();
        biddingList.add(bidding);

        when(biddingMapper.getByPid(anyInt())).thenReturn(biddingList);

        assertEquals(1, biddingService.getBidsByPid(1).size());
    }

    @Test
    public void exceptionWithGetByPid() {
        when(biddingMapper.getByPid(anyInt())).thenReturn(null);
        assertNull(biddingService.getBidsByPid(0));
    }


    ///////////////     add bid          /////////////

    @Test
    public void notInAuctionExceptionWithAddBid() {
        ProductStatus productStatus = new ProductStatus();
        productStatus.setStatus("waiting");

        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);
        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "120");
        assertEquals(-1, biddingService.addBid(param));
    }

    @Test
    public void AuctionEndedExceptionWithAddBid() {
        ProductStatus productStatus = new ProductStatus();
        productStatus.setStatus("extended3");
        productStatus.setEndTime(new Timestamp(System.currentTimeMillis() - 60 * 1000));  // 1 min ago

        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);
        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "120");
        assertEquals(-2, biddingService.addBid(param));
    }

    @Test
    public void buyYourOwnProductExceptionWithAddBid() {
        ProductStatus productStatus = new ProductStatus(0, 8, 100, "bidding",
                new Timestamp(System.currentTimeMillis() + 10 * 60 * 1000));  // 10 min
        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);

        Product product = new Product();
        product.setUid(2);
        when(productMapper.getByPid(anyInt())).thenReturn(product);

        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "120");
        assertEquals(-3, biddingService.addBid(param));
    }

    @Test
    public void invalidOfferExceptionWithAddBid() {
        ProductStatus productStatus = new ProductStatus();
        productStatus.setStatus("bidding");
        productStatus.setPrice(100);
        productStatus.setEndTime(new Timestamp(System.currentTimeMillis() + 10 * 60 * 1000));
        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);

        Product product = new Product();
        product.setMinIncrement(20);
        when(productMapper.getByPid(anyInt())).thenReturn(product);

        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "110");
        assertEquals(-4, biddingService.addBid(param));
    }

    @Test
    public void autoWinWithAddBid() {
        ProductStatus productStatus = new ProductStatus(0, 8, 100, "bidding",
                new Timestamp(System.currentTimeMillis() + 10 * 60 * 1000));  // 10 min
        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);

        Product product = new Product();
        product.setPid(8);
        product.setUid(1);
        product.setStartPrice(100);
        product.setAutoWinPrice(150);
        product.setMinIncrement(10);
        when(productMapper.getByPid(anyInt())).thenReturn(product);

        when(biddingMapper.add(any(Bidding.class))).thenReturn(1);
        when(productMapper.updateProductStatus(any(ProductStatus.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "150");
        assertEquals(2, biddingService.addBid(param));
        assertEquals("success", productStatus.getStatus());
    }

    // bidding阶段正常bid
    @Test
    public void happyPathWithAddBid() {
        ProductStatus productStatus = new ProductStatus(0, 8, 100, "bidding",
                new Timestamp(System.currentTimeMillis() + 10 * 60 * 1000));  // 10 min
        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);

        Product product = new Product();
        product.setPid(8);
        product.setUid(1);
        product.setStartPrice(100);
        product.setAutoWinPrice(150);
        product.setMinIncrement(10);
        when(productMapper.getByPid(anyInt())).thenReturn(product);

        when(biddingMapper.add(any(Bidding.class))).thenReturn(1);
        when(productMapper.updateProductStatus(any(ProductStatus.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "120");

        assertEquals(1, biddingService.addBid(param));
        assertEquals("bidding", productStatus.getStatus());
        assertEquals(120, productStatus.getPrice());
    }

    // bidding阶段的最后一分钟
    @Test
    public void lastMinWithAddBid() {
        ProductStatus productStatus = new ProductStatus(0, 8, 100, "bidding",
                new Timestamp(System.currentTimeMillis() + 50 * 1000));  // 50s
        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);

        Product product = new Product();
        product.setPid(8);
        product.setUid(1);
        product.setStartPrice(100);
        product.setAutoWinPrice(150);
        product.setMinIncrement(10);
        when(productMapper.getByPid(anyInt())).thenReturn(product);

        when(biddingMapper.add(any(Bidding.class))).thenReturn(1);
        when(productMapper.updateProductStatus(any(ProductStatus.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "120");

        assertEquals(1, biddingService.addBid(param));
        assertEquals("extended1", productStatus.getStatus());
        assertEquals(120, productStatus.getPrice());
        // bidding最后一分钟抢拍，加10min
        assertTrue(productStatus.getEndTime().after(new Timestamp(System.currentTimeMillis() + 10 * 60 * 1000)));
    }

    // extend1阶段正常bid
    @Test
    public void extended1WithAddBid() {
        ProductStatus productStatus = new ProductStatus(0, 8, 100, "extended1",
                new Timestamp(System.currentTimeMillis() + 8 * 60 * 1000));  // 8 min
        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);

        Product product = new Product();
        product.setPid(8);
        product.setUid(1);
        product.setStartPrice(100);
        product.setAutoWinPrice(150);
        product.setMinIncrement(10);
        when(productMapper.getByPid(anyInt())).thenReturn(product);

        when(biddingMapper.add(any(Bidding.class))).thenReturn(1);
        when(productMapper.updateProductStatus(any(ProductStatus.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "120");

        assertEquals(1, biddingService.addBid(param));
        assertEquals("extended1", productStatus.getStatus());
        assertEquals(120, productStatus.getPrice());
        assertTrue(productStatus.getEndTime().before(new Timestamp(System.currentTimeMillis() + 8 * 60 * 1000)));
        assertTrue(productStatus.getEndTime().after(new Timestamp(System.currentTimeMillis() + 7 * 60 * 1000)));
    }

    // extend1最后一分钟
    @Test
    public void extended1LastMinWithAddBid() {
        ProductStatus productStatus = new ProductStatus(0, 8, 100, "extended1",
                new Timestamp(System.currentTimeMillis() + 50 * 1000));  // 50s
        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);

        Product product = new Product();
        product.setPid(8);
        product.setUid(1);
        product.setStartPrice(100);
        product.setAutoWinPrice(150);
        product.setMinIncrement(10);
        when(productMapper.getByPid(anyInt())).thenReturn(product);

        when(biddingMapper.add(any(Bidding.class))).thenReturn(1);
        when(productMapper.updateProductStatus(any(ProductStatus.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "120");

        assertEquals(1, biddingService.addBid(param));
        assertEquals("extended2", productStatus.getStatus());
        assertEquals(120, productStatus.getPrice());
        // extended1 last min 加5 min
        assertTrue(productStatus.getEndTime().after(new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000)));
    }

    // extended2阶段正常bid
    @Test
    public void extended2WithAddBid() {
        ProductStatus productStatus = new ProductStatus(0, 8, 100, "extended2",
                new Timestamp(System.currentTimeMillis() + 3 * 60 * 1000));  // 3 min
        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);

        Product product = new Product();
        product.setPid(8);
        product.setUid(1);
        product.setStartPrice(100);
        product.setAutoWinPrice(150);
        product.setMinIncrement(10);
        when(productMapper.getByPid(anyInt())).thenReturn(product);

        when(biddingMapper.add(any(Bidding.class))).thenReturn(1);
        when(productMapper.updateProductStatus(any(ProductStatus.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "120");

        assertEquals(1, biddingService.addBid(param));
        assertEquals("extended2", productStatus.getStatus());
        assertEquals(120, productStatus.getPrice());
        assertTrue(productStatus.getEndTime().before(new Timestamp(System.currentTimeMillis() + 3 * 60 * 1000)));
        assertTrue(productStatus.getEndTime().after(new Timestamp(System.currentTimeMillis() + 2 * 60 * 1000)));
    }

    // extended2阶段last min
    @Test
    public void extended2LastMinWithAddBid() {
        ProductStatus productStatus = new ProductStatus(0, 8, 100, "extended2",
                new Timestamp(System.currentTimeMillis() + 50 * 1000));  // 50s
        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);

        Product product = new Product();
        product.setPid(8);
        product.setUid(1);
        product.setStartPrice(100);
        product.setAutoWinPrice(150);
        product.setMinIncrement(10);
        when(productMapper.getByPid(anyInt())).thenReturn(product);

        when(biddingMapper.add(any(Bidding.class))).thenReturn(1);
        when(productMapper.updateProductStatus(any(ProductStatus.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "120");

        assertEquals(1, biddingService.addBid(param));
        assertEquals("extended3", productStatus.getStatus());
        assertEquals(120, productStatus.getPrice());
        // extended3 last min 加1 min
        assertTrue(productStatus.getEndTime().after(new Timestamp(System.currentTimeMillis() + 1 * 60 * 1000)));
    }

    // extended3 阶段拍必得
    @Test
    public void extended3WithAddBid() {
        ProductStatus productStatus = new ProductStatus(0, 8, 100, "extended3",
                new Timestamp(System.currentTimeMillis() + 50 * 1000));  // 50s
        when(productMapper.getProductStatusByPid(anyInt())).thenReturn(productStatus);

        Product product = new Product();
        product.setPid(8);
        product.setUid(1);
        product.setStartPrice(100);
        product.setAutoWinPrice(150);
        product.setMinIncrement(10);
        when(productMapper.getByPid(anyInt())).thenReturn(product);

        when(biddingMapper.add(any(Bidding.class))).thenReturn(1);
        when(productMapper.updateProductStatus(any(ProductStatus.class))).thenReturn(1);
        Map<String, String> param = new HashedMap();
        param.put("uid", "2");
        param.put("pid", "8");
        param.put("offer", "120");
        assertEquals(2, biddingService.addBid(param));
        assertEquals("success", productStatus.getStatus());
    }
}
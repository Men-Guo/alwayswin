package com.example.alwayswin.service.Impl;

import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductPreview;
import com.example.alwayswin.entity.ProductStatus;
import com.example.alwayswin.service.ProductService;
import com.example.alwayswin.service.impl.ProductServiceImpl;
import com.example.alwayswin.utils.enumUtil.ProductStatusCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    ProductService productService = new ProductServiceImpl();

    @Test
    public void happyPathQueryProduct(){
        Product product = productService.displayProductDetail(1);
        System.out.println(product);
        assertEquals(1, product.getPid());
        ProductStatus productStatus = product.getProductStatus();
        System.out.println(productStatus);
        assertEquals(1, productStatus.getPid());
        Figure figure = product.getThumbnail();
        assertTrue(figure.isThumbnail());
        List<Figure> figures = product.getFigures();
        System.out.println(figures);
        assertEquals(3, figures.size());
    }

    @Test
    public void noSuchProduct(){
        assertNull(productService.displayProductDetail(-1));
    }

    @Test
    public void happyPathUpdateProduct(){
        Product product = productService.displayProductDetail(8);
        product.setCanceled(false);
        assertEquals(8,productService.updateProduct(product));
    }

    @Test
    public void happyPathUpdateProductStatus(){
        Product product = productService.displayProductDetail(8);
        ProductStatus productStatus = product.getProductStatus();
        productStatus.setStatus("waiting");
        assertEquals(1,productService.updateProductStatusService(productStatus));
    }

    @Test
    public void happyPathCancelProduct(){
        assertEquals(1,productService.cancelProduct(34));
        Product product = productService.displayProductDetail(34);
        ProductStatus productStatus = product.getProductStatus();
        assertTrue(product.isCanceled());
        assertEquals("canceled",productStatus.getStatus());
    }

    @Test
    public void noSuchProductThatCanBeCancel(){
        assertNull(productService.cancelProduct(-1));
    }

    @Test
    public void isCancelable(){
        assertTrue(ProductStatusCode.isCancelable("waiting"));
    }

    @Test
    public void productAlreadyCanceled(){
        assertNull(productService.cancelProduct(24));
    }

    @Test
    public void cancelNotExistProduct(){
        assertNull(productService.cancelProduct(-1));
    }

    @Test
    public void cancelProductThatCantCancel(){
        assertNull(productService.cancelProduct(10));
    }

    @Test
    public void cancelProductWithNoProductStatus(){assertNull(productService.cancelProduct(27));}

    @Test
    public void happyPathCreateProduct(){
        Product product = productService.displayProductDetail(7);
        assertEquals(1, productService.createProduct(product));
    }

    @Test
    public void CreateProductWithNoAutoWinPrice(){
        Product product = productService.displayProductDetail(7);
        product.setAutoWinPrice(0);
        assertNull(productService.createProduct(product));
    }

    @Test
    public void CreateProductUsingDefaultStartTime(){
        Product product = productService.displayProductDetail(24);
        product.setAutoWinPrice(100);
        product.setEndTime(new Timestamp(System.currentTimeMillis()-2000));
        product.setStartTime(new Timestamp(System.currentTimeMillis()-1000));
        assertEquals(1,productService.createProduct(product));
    }

    @Test
    public void CreateProductWithIllegalCate(){
        Product product = productService.displayProductDetail(24);
        product.setAutoWinPrice(100);
        product.setCate1("human");
        assertNull(productService.createProduct(product));
    }

    @Test
    public void happyPathDisplayAllProductPreviews(){
        List<ProductPreview> productPreviews = productService.displayAllProduct();
        System.out.println(productPreviews);
    }

    @Test
    public void happyPathDisplayAllProductPreviewsOrders(){
        List<ProductPreview> productPreviews = productService.displayAllProductSorted("auto_win_price","asc");
        System.out.println(productPreviews);
        assertEquals(23,productPreviews.size());

        List<ProductPreview> productPreviews2 = productService.displayAllProductSorted("auto_win_price","ASC");
        System.out.println(productPreviews2);


        productPreviews = productService.displayAllProductSorted("auto_win_price","DESC");
        System.out.println(productPreviews);
        assertEquals(23,productPreviews.size());
        productPreviews2 = productService.displayAllProductSorted("auto_win_price","desc");
        System.out.println(productPreviews2);


        productPreviews = productService.displayAllProductSorted("autoWinPrice","asc");
        System.out.println(productPreviews);
        assertEquals(23,productPreviews.size());

        productPreviews2 = productService.displayAllProductSorted("autoWinPrice","ASC");
        System.out.println(productPreviews2);

        productPreviews = productService.displayAllProductSorted("autoWinPrice","DESC");
        System.out.println(productPreviews);
        assertEquals(23,productPreviews.size());
        productPreviews2 = productService.displayAllProductSorted("autoWinPrice","desc");
        System.out.println(productPreviews2);


        productPreviews = productService.displayAllProductSorted("auto_win_price","asc");
        System.out.println(productPreviews);
        assertEquals(23,productPreviews.size());

        productPreviews2 = productService.displayAllProductSorted("price","ASC");
        System.out.println(productPreviews2);


        productPreviews = productService.displayAllProductSorted("price","DESC");
        System.out.println(productPreviews);
        assertEquals(23,productPreviews.size());
        productPreviews2 = productService.displayAllProductSorted("price","desc");
        System.out.println(productPreviews2);

    }

    @Test
    public void unhappyPathDisplayAllProductPreviews(){
        assertNull(productService.displayAllProductSorted("auto_price","asc"));
        assertNull(productService.displayAllProductSorted("auto_win_price","bbc"));
    }

    @Test
    public void displayProductsByUid(){
        assertNull(productService.displayAllProductsByUid(-1));
        assertEquals(3,productService.displayAllProductsByUid(1).size());
    }

    @Test
    public void displayProductsByCate(){
        assertNull(productService.displayAllProductsByCate("food"));
        assertEquals(3, productService.displayAllProductsByCate("gaming console").size());
    }

    @Test
    public void displayProductsByCateOrder(){
        List<ProductPreview> productPreviews = productService.displayProductsByCateAndSorted("cell phone","auto_win_price","asc");
        System.out.println(productPreviews);
        assertEquals(17,productPreviews.size());

        List<ProductPreview> productPreviews2 = productService.displayProductsByCateAndSorted("cell phone","auto_win_price","ASC");
        System.out.println(productPreviews2);


        productPreviews = productService.displayProductsByCateAndSorted("cell phone","auto_win_price","DESC");
        System.out.println(productPreviews);
        assertEquals(17,productPreviews.size());
        productPreviews2 = productService.displayProductsByCateAndSorted("cell phone","auto_win_price","desc");
        System.out.println(productPreviews2);


        productPreviews = productService.displayProductsByCateAndSorted("cell phone","autoWinPrice","asc");
        System.out.println(productPreviews);
        assertEquals(17,productPreviews.size());

        productPreviews2 = productService.displayProductsByCateAndSorted("cell phone","autoWinPrice","ASC");
        System.out.println(productPreviews2);

        productPreviews = productService.displayProductsByCateAndSorted("cell phone","autoWinPrice","DESC");
        System.out.println(productPreviews);
        assertEquals(17,productPreviews.size());
        productPreviews2 = productService.displayProductsByCateAndSorted("cell phone","autoWinPrice","desc");
        System.out.println(productPreviews2);


        productPreviews = productService.displayProductsByCateAndSorted("cell phone","auto_win_price","asc");
        System.out.println(productPreviews);
        assertEquals(17,productPreviews.size());

        productPreviews2 = productService.displayProductsByCateAndSorted("cell phone","price","ASC");
        System.out.println(productPreviews2);


        productPreviews = productService.displayProductsByCateAndSorted("cell phone","price","DESC");
        System.out.println(productPreviews);
        assertEquals(17,productPreviews.size());
        productPreviews2 = productService.displayProductsByCateAndSorted("cell phone","price","desc");
        System.out.println(productPreviews2);
    }

    @Test
    public void unhappyPathDisplayProductByCateOrder(){
        assertNull(productService.displayProductsByCateAndSorted("cell phone","auto_win_price","xxx"));
        assertNull(productService.displayProductsByCateAndSorted("cell phone","xxx","ASC"));
        assertNull(productService.displayProductsByCateAndSorted("cell","auto_win_price","ASC"));
    }

    @Test
    public void queryProductStatus(){
        assertNull(productService.displayProductStatus(-1));
        assertEquals(1,productService.displayProductStatus(1).getPid());
    }

    @Test
    public void unhappyPathUpdateProductStatues(){
        ProductStatus productStatus = new ProductStatus();
        productStatus.setStatus("xxxx");
        assertNull(productService.updateProductStatusService(productStatus));
    }

    @Test
    public void testSearch(){
        System.out.println(productService.displaySearchProducts("pho"));
    }
}

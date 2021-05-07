package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Bidding;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BiddingMapperTest {
    @Autowired
    private BiddingMapper biddingMapper;

    ////////////////////        getByBid     //////////////////
    @Test
    public void happyPathWithGetByBid() {
        Bidding bidding = biddingMapper.getByBid(1);
        assertNotNull(bidding);
        assertEquals("Play Station 1000", bidding.getProductPreview().getTitle());
    }

    @Test
    public void biddingNotFoundWithGetByBid() {
        Bidding bidding = biddingMapper.getByBid(0);
        assertNull(bidding);
    }

    ///////////////        getByUid     ///////////////////////
    @Test
    public void happyPathWithGetByUid() {
        List<Bidding> biddingList = biddingMapper.getByUid(1);
        assertNotNull(biddingList);
        assertEquals(2, biddingList.size());
    }

    @Test
    public void biddingListNotFoundWithGetByBid() {
        List<Bidding> biddingList = biddingMapper.getByUid(0);
        assertEquals(0, biddingList.size());
    }

    ///////////////        getByPid     ///////////////////////
    @Test
    public void happyPathWithGetByPid() {
        List<Bidding> biddingList = biddingMapper.getByPid(11);
        assertNotNull(biddingList);
        assertEquals(2, biddingList.size());
    }

    @Test
    public void biddingListNotFoundWithGetByPid() {
        List<Bidding> biddingList = biddingMapper.getByUid(0);
        assertEquals(0, biddingList.size());
    }

    /////////////////       AddBidding        //////////////////////////
    @Test
    public void happyPathWithAddBidding() {
        // (Integer bid, Integer uid, Integer pid, double offer, Timestamp createTime) {
        Bidding bidding = new Bidding(0, 2, 1, 400, new Timestamp(System.currentTimeMillis()));
        assertEquals(biddingMapper.add(bidding), 1);

        List<Bidding> biddingList = biddingMapper.getByPid(1);
        assertNotNull(biddingList);
        assertEquals(1, biddingList.size());

        int bid = 0;
        for (Bidding b: biddingList)
            if (b.getPid() == 1)
                bid = b.getBid();
        assertEquals(1, biddingMapper.delete(bid));
    }

    @Test
    public void exceptionWithAddBidding() {
        // invalid pid
        Bidding bidding = new Bidding(0, 2, 0, 400, new Timestamp(System.currentTimeMillis()));

        assertThrows(Exception.class, () -> biddingMapper.add(bidding));
    }
}
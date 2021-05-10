package com.example.alwayswin.service;

import com.example.alwayswin.entity.Bidding;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BiddingService {

    Bidding getByBid(int bid);

    List<Bidding> getBidsByUid(int uid);

    List<Bidding> getBidsByPid(int pid);

    int addBid(Map param);

}

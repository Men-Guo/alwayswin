package com.example.alwayswin.service;

import com.example.alwayswin.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AddressService {

    Address showAddress(int aid);

    List<Address> showAllAddresses(int uid);

    int addAddress(int uid, Map param);

    int updateAddress(int aid, Map param);

    int deleteAddress(int aid);
}

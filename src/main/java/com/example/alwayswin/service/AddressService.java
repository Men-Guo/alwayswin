package com.example.alwayswin.service;

import com.example.alwayswin.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AddressService {

    Address getAddressByAid(int aid);

    List<Address> getAllAddresses(int uid);

    int addAddress(Map param);

    int updateAddress(int aid, Map param);

    int deleteAddress(int aid);
}

package com.example.alwayswin.service;

import com.example.alwayswin.entity.ResponseMsg;

import java.util.Map;

public interface AddressService {

    ResponseMsg showAddress(int aid);

    ResponseMsg showAllAddresses(int uid);

    ResponseMsg addAddress(int uid, String name, String phone, String location, String state, String zipCode);

    ResponseMsg editAddress(int aid, Map map);

    ResponseMsg deleteAddress(int aid);
}

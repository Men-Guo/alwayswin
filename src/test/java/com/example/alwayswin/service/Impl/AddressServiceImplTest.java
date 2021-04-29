package com.example.alwayswin.service.Impl;

import com.example.alwayswin.entity.Address;
import com.example.alwayswin.mapper.AddressMapper;
import com.example.alwayswin.service.AddressService;
import com.example.alwayswin.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class AddressServiceImplTest {
    private static AddressService addressService;
    @Mock
    private AddressMapper addressMapper;

    @BeforeEach
    public void init() {
        addressMapper = mock(AddressMapper.class);
        addressService = new AddressServiceImpl(addressMapper);
    }

    //////////      getByAid        //////////////////////
    @Test
    public void happyPathWithGetAddressByAid() {
        // Integer aid, Integer uid, String name, String phone,
        //                   String location, String state, String zipCode
        Address address = new Address(1, 1 , "xiaoming", "110", "xxx", "NM", "2333");
        when(addressMapper.getByAid(anyInt())).thenReturn(address);

        Address address1 = addressService.getAddressByAid(1);
        assertNotNull(address1);
        assertEquals("xiaoming", address1.getName());
    }

    @Test
    public void InvalidAidExceptionWithGetAddressByAid() {
        when(addressMapper.getByAid(anyInt())).thenReturn(null);
        Address address1 = addressService.getAddressByAid(0);
        assertNull(address1);
    }


    //////////      getByUid        /////////////////
    @Test
    public void happyPathWithGetAddressByUid() {
        List<Address> addressList = new ArrayList<>();
        Address address = new Address(1, 1 , "xiaoming", "110", "xxx", "NM", "2333");
        addressList.add(address);

        when(addressMapper.getByUid(anyInt())).thenReturn(addressList);

        List<Address> addressList1 = addressService.getAllAddresses(1);
        assertNotNull(addressList1);
        assertEquals(1, addressList1.size());
    }

    @Test
    public void InvalidUidExceptionWithGetAddressByUid() {
        when(addressMapper.getByUid(anyInt())).thenReturn(null);
        List<Address> addressList1 = addressService.getAllAddresses(0);

        assertNull(addressList1);
    }

    //////////      addAddress        /////////////////
    @Test
    public void happyPathWithAddAddress() {
        when(addressMapper.add(any())).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("uid", "1");
        param.put("name", "xiaoming");
        param.put("location", "NYPD");
        addressService.addAddress(param);
    }

    //////////      updateAddress        /////////////////
    public void happyPathWithUpdateAddress() {
        when(addressMapper.update(any(Address.class))).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("name", "xiaoming");
        param.put("location", "NYPD");
        addressService.updateAddress(1, param);
    }


    //////////      deleteAddress        /////////////////
    @Test
    public void happyPathWithDeleteAddress() {
        when(addressMapper.delete(anyInt())).thenReturn(1);
        assertEquals(1, addressService.deleteAddress(1));
    }

    @Test
    public void InvalidAidExceptionWithDeleteAddress() {
        when(addressMapper.delete(anyInt())).thenReturn(0);
        assertEquals(0, addressService.deleteAddress(0));
    }

}
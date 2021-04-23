package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Address;
import com.example.alwayswin.mapper.AddressMapper;
import com.example.alwayswin.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AddressServiceImpl
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-22
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressMapper addressMapper;

    public AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Address showAddress(int aid) {
        return addressMapper.getByAid(aid);
    }

    public List<Address> showAllAddresses(int uid) {
        return addressMapper.getByUid(uid);
    }

    public int addAddress(int uid, Map param) {
        Address address = new Address();
        BeanUtils.copyProperties(param,address);
        return addressMapper.add(address);
    }

    public int editAddress(int aid, Map param) {
        Address address = new Address();
        BeanUtils.copyProperties(param,address);
        address.setAid(aid);
        return addressMapper.update(address);
    }

    public int deleteAddress(int aid) {
        return addressMapper.delete(aid);
    }
}

package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Address;
import com.example.alwayswin.mapper.AddressMapper;
import com.example.alwayswin.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.apache.commons.beanutils.BeanUtils;

import javax.annotation.Resource;
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
    private static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Resource
    private AddressMapper addressMapper;

    public AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Address getAddressByAid(int aid) {
        return addressMapper.getByAid(aid);
    }

    public List<Address> getAllAddresses(int uid) {
        return addressMapper.getByUid(uid);
    }

    public int addAddress(Map param) {
        Address address = new Address();
        try {
            BeanUtils.populate(address, param);
        }catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
        return addressMapper.add(address);
    }

    public int updateAddress(int aid, Map param) {
        Address address = new Address();
        try {
            BeanUtils.populate(address, param);
            address.setAid(aid);
        }catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
        return addressMapper.update(address);
    }

    public int deleteAddress(int aid) {
        return addressMapper.delete(aid);
    }
}

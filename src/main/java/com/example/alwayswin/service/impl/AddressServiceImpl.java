package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Address;
import com.example.alwayswin.entity.ResponseMsg;
import com.example.alwayswin.mapper.AddressMapper;
import com.example.alwayswin.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class AddressServiceImpl implements AddressService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private AddressMapper addressMapper;

    public AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public ResponseMsg showAddress(int aid) {
        ResponseMsg msg = new ResponseMsg();
        try {
            Address address = addressMapper.getByAid(aid);
            if (address == null)
                msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "No such address");
            else {
                msg.setStatusAndMessage(HttpServletResponse.SC_OK, "Get address " + aid);
                msg.getResponseMap().put("address", address);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg showAllAddresses(int uid) {
        ResponseMsg msg = new ResponseMsg();
        try {
            List<Address> addressList = addressMapper.getByUid(uid);
            if (addressList == null)
                msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "No such user " + uid);
            else {
                msg.setStatusAndMessage(HttpServletResponse.SC_OK, "Get address list");
                msg.getResponseMap().put("addressList", addressList);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg addAddress(int uid, String name, String phone, String location, String state, String zipCode) {
        ResponseMsg msg = new ResponseMsg();
        try {
           Address address = new Address(0, uid, name, phone, location, state, zipCode);
            if (addressMapper.add(address) != 1)
                msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "Can't add address");
            else {
                msg.setStatusAndMessage(HttpServletResponse.SC_OK, "Address added");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg editAddress(int aid, Map param) {
        ResponseMsg msg = new ResponseMsg();
        try {
            Address address = addressMapper.getByAid(aid);
            if (address == null)
                msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "No such address");
            else {
                if (param.containsKey("name"))
                    address.setName(param.get("name").toString());
                if (param.containsKey("phone"))
                    address.setPhone(param.get("phone").toString());
                if (param.containsKey("location"))
                    address.setLocation(param.get("location").toString());
                if (param.containsKey("state"))
                    address.setState(param.get("state").toString());
                if (param.containsKey("zipCode"))
                    address.setZipCode(param.get("zipCode").toString());

                if (addressMapper.update(address) != 1)
                    msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "Can't update address");
                else
                    msg.setStatusAndMessage(HttpServletResponse.SC_OK, "Address updated");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    public ResponseMsg deleteAddress(int aid) {
        ResponseMsg msg = new ResponseMsg();
        try {
            if (addressMapper.delete(aid) != 1)
                msg.setStatusAndMessage(HttpServletResponse.SC_BAD_REQUEST, "Can't delete address");
            else
                msg.setStatusAndMessage(HttpServletResponse.SC_OK, "Address deleted");
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return msg;
    }
}

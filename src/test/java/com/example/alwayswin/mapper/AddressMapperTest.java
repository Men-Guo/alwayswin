package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Address;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    ////////////////////        getByAid     ///////////////////////////////////
    @Test
    public void happyPathWithGetByAid() {
        Address address = addressMapper.getByAid(1);
        assertNotNull(address);
        assertEquals("Arthur", address.getName());
    }

    @Test
    public void addressNotFoundWithGetByAid() {
        Address address = addressMapper.getByAid(-1);
        assertNull(address);
    }

    ////////////////////        getByUid     ///////////////////////////////////
    @Test
    public void happyPathWithGetByUid() {
        List<Address> addressList = addressMapper.getByUid(1);
        assertNotNull(addressList);
        assertEquals(2, addressList.size());
    }

    @Test
    public void addressListNotFoundWithGetByAid() {
        List<Address> addressList = addressMapper.getByUid(0);
        assertEquals(0, addressList.size());
    }

    @Test
    public void happyPathWithAddAddress() {
        Address address = new Address(0, 1, "Amy", "110",
                "police office", "CA", "92615");
        assertEquals(addressMapper.add(address), 1);

        assertEquals(3, addressMapper.getByUid(1).size());

        List<Address> addressList = addressMapper.getByUid(1);
        assertNotNull(addressList);

        int aid = 0;
        for (Address a: addressList)
            if (a.getName().equals("Amy"))
                aid = a.getAid();
        assertEquals(1, addressMapper.delete(aid));
    }

    @Test
    public void exceptionWithAddAddress() {
        // invalid uid
        Address address = new Address(0, 0, "Amy", "110",
                "police office", "CA", "92615");

        assertThrows(Exception.class, () -> addressMapper.add(address));
    }

    @Test
    public void happyPathWithEditAddress() {
        Address address = addressMapper.getByAid(1);
        address.setZipCode("00000");

        assertEquals(1, addressMapper.update(address));

        address = addressMapper.getByAid(1);
        assertEquals("00000", address.getZipCode());
    }

    @Test
    public void happyPathWithDeleteAddress() {
        Address address = new Address(0, 3,"ccc", "123456", "Plaza", "CA", "92617");
        assertEquals(1, addressMapper.add(address));

        List<Address> addressList = addressMapper.getByUid(3);
        for (Address a: addressList)
            if (a.getName().equals("ccc"))
                address = a;
        assertEquals(1, addressMapper.delete(address.getAid()));
    }

    @Test
    public void exceptionWithDeleteAddress() {
        assertEquals(0, addressMapper.delete(-1));
    }
}
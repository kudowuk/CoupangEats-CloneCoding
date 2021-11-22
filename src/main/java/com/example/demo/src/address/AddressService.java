package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.src.address.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional(rollbackFor = BaseException.class)
public class AddressService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AddressDao addressDao;
    private final AddressProvider addressProvider;

    @Autowired
    public AddressService(AddressDao addressDao, AddressProvider addressProvider) {
        this.addressDao = addressDao;
        this.addressProvider = addressProvider;
    }

    //POST
    public PostAddressRes createAddress(int userIdx, PostAddressReq postAddressReq) throws BaseException {
        try {
            int addressIdx = addressDao.createAddress(userIdx, postAddressReq);
            return new PostAddressRes(addressIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyAddress(PatchAddressReq patchAddressReq) throws BaseException {
        try {
            int result = addressDao.modifyAddress(patchAddressReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_ADDRESS);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

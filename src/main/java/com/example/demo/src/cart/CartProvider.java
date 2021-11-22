package com.example.demo.src.cart;

import com.example.demo.config.BaseException;
import com.example.demo.src.cart.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class CartProvider {

    private final CartDao cartDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CartProvider(CartDao cartDao) {
        this.cartDao = cartDao;
    }

//    public List<GetCartRes> getCarts() throws BaseException {
//        try{
//            List<GetCartRes> getCartRes = cartDao.getCarts();
//            return getCartRes;
//        }
//        catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    // GET 카트 담기 조회 API
    public GetCartRes getCart(int userIdx, int cartIdx) throws BaseException {
        GetCartRes getCartRes = cartDao.getCart(userIdx, cartIdx);
        return getCartRes;
    }

}

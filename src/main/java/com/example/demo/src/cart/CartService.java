package com.example.demo.src.cart;

import com.example.demo.config.BaseException;
import com.example.demo.src.address.model.PatchAddressReq;
import com.example.demo.src.cart.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional(rollbackFor = BaseException.class)
public class CartService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CartDao cartDao;
    private final CartProvider cartProvider;

    @Autowired
    public CartService(CartDao cartDao, CartProvider cartProvider) {
        this.cartDao = cartDao;
        this.cartProvider = cartProvider;
    }

    // POST 카트 담기 등록 API
    public PostCartRes createCart(int userIdx, PostCartReq postCartReq) throws BaseException {
        try {
            // Insert Cart 큰
            int cartIdx = cartDao.createCart(userIdx, postCartReq);

            return new PostCartRes(cartIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // POST 카트 담기 등록 API
//    public PostCartRes createCart(int userIdx, PostCartReq postCartReq) throws BaseException {
//        try {
//            // Insert Cart 큰 카트틀 추가하기
//            int cartIdx = cartDao.createCart(userIdx, postCartReq);
//
//            // Insert CartDetail 작은 카트 디테일 추가하기
//            for (PostCartDetailReq menuList : postCartReq.getMenuList()){
//                cartDao.createCart(cartIdx, menuList);
//            }
//            PostCartRes postCartRes = new PostCartRes(cartIdx);
//            return new PostCartRes(cartIdx);
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }


    //POST
    public PostCartDetailRes createCartDetail(int cartIdx, PostCartDetailReq postCartDetailReq) throws BaseException {
        try {
            int cartDetailIdx = cartDao.createCartDetail(cartIdx, postCartDetailReq);
            return new PostCartDetailRes(cartDetailIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // PATCH 카트 담기 수정 API
    public void modifyCart(PatchCartReq patchCartReq) throws BaseException {
        try {
            int result = cartDao.modifyCart(patchCartReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_CART);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

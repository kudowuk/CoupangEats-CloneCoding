package com.example.demo.src.cart;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.cart.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/carts")
public class CartController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CartProvider cartProvider;
    @Autowired
    private final CartService cartService;
    @Autowired
    private final JwtService jwtService;


    public CartController(CartProvider cartProvider, CartService cartService, JwtService jwtService){
        this.cartProvider = cartProvider;
        this.cartService = cartService;
        this.jwtService = jwtService;
    }

//    @ResponseBody
//    @GetMapping("/{userIdx}")
//    public BaseResponse<List<GetCartRes>> getCarts(@PathVariable("userIdx") int userIdx) {
//        try {
//            List<GetCartRes> getCartRes = cartProvider.getCarts();
//            return new BaseResponse<>(getCartRes);
//
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

    // GET 카트 담기 조회 API
    @ResponseBody
    @GetMapping("/{userIdx}/{cartIdx}") // (GET) 127.0.0.1:9000/app/carts/cartIdx?=1
    public BaseResponse<GetCartRes> getCart(@PathVariable("userIdx") int userIdx, @PathVariable("cartIdx") int cartIdx) {
        // Get Cart
        try {

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            GetCartRes getCartRes = cartProvider.getCart(userIdx, cartIdx);
            return new BaseResponse<>(getCartRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 카트등록 API
     * [POST] /carts/
     * @return BaseResponse<PostCartRes>
     */
    // Body
    // 패스배리어블로 받아오기
    @ResponseBody
    @PostMapping("/{userIdx}")
    public BaseResponse<PostCartRes> createCart(@PathVariable("userIdx") int userIdx, @RequestBody PostCartReq postCartReq) {

        if(postCartReq.getToOwner().length() >= 50){
            return new BaseResponse<>(POST_CARTS_OVER_TOOWNER);
        }
        if(postCartReq.getToDriver().length() >= 50){
            return new BaseResponse<>(POST_CARTS_OVER_TODRIVER);
        }

        try{

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PostCartRes postCartRes = cartService.createCart(userIdx, postCartReq);
            return new BaseResponse<>(postCartRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 카트디테일등록 API
     * [POST] /carts/{cartIdx}/cartDetails
     * @return BaseResponse<PostCartRes>
     */
    // Body
    // POST 카드 담기 등록 API
    @ResponseBody
    @PostMapping("/{cartIdx}/cartDetails")
    public BaseResponse<PostCartDetailRes> createCartDetail(@PathVariable("cartIdx") int cartIdx, @RequestBody PostCartDetailReq postCartDetailReq) {
        try{
            PostCartDetailRes postCartDetailRes = cartService.createCartDetail(cartIdx, postCartDetailReq);
            return new BaseResponse<>(postCartDetailRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // PATCH 카트 담기 수정 API
    @ResponseBody
    @PatchMapping("/{userIdx}/{cartIdx}")
    public BaseResponse<String> modifyCart(@PathVariable("userIdx") int userIdx, @PathVariable("cartIdx") int cartIdx, @RequestBody Cart cart){
        try {

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchCartReq patchCartReq = new PatchCartReq(userIdx, cartIdx, cart.getStoreIdx(), cart.getCouponIdx(), cart.getToOwner(), cart.getToDriver(), cart.getDisposableAt());
            cartService.modifyCart(patchCartReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



}

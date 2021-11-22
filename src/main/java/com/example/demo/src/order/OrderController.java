package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.model.GetOrderRes;
import com.example.demo.src.order.model.GetReceiptRes;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.order.model.PostOrderRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/orders")
public class OrderController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrderProvider orderProvider;
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;

    public OrderController(OrderProvider orderProvider, OrderService orderService, JwtService jwtService){
        this.orderProvider = orderProvider;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    // GET 전체 주문 내역 조회 API
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetOrderRes>> getOrders(@PathVariable("userIdx") int userIdx) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetOrderRes> getOrderRes = orderProvider.getOrders(userIdx);
            return new BaseResponse<>(getOrderRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // GET 주문내역에 특정 영수증 보기 API
    @ResponseBody
    @GetMapping("/{userIdx}/{orderIdx}")
    public BaseResponse<GetReceiptRes> getReceipt(@PathVariable("userIdx") int userIdx, @PathVariable("orderIdx") int orderIdx) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }


            GetReceiptRes getReceiptRes = orderProvider.getReceipt(userIdx, orderIdx);
            return new BaseResponse<>(getReceiptRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // POST 주문 등록 API
    @ResponseBody
    @PostMapping("/{userIdx}")
    public BaseResponse<PostOrderRes> createOrder(@PathVariable("userIdx") int userIdx, @RequestBody PostOrderReq postOrderReq) {
        try{

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PostOrderRes postOrderRes = orderService.createOrder(userIdx, postOrderReq);
            return new BaseResponse<>(postOrderRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }




}

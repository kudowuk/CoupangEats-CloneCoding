package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.order.model.PostOrderRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(rollbackFor = BaseException.class)
public class OrderService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderDao orderDao;
    private final OrderProvider orderProvider;

    @Autowired
    public OrderService(OrderDao orderDao, OrderProvider orderProvider) {
        this.orderDao = orderDao;
        this.orderProvider = orderProvider;
    }

    //POST
    public PostOrderRes createOrder(int userIdx, PostOrderReq postOrderReq) throws BaseException {
        try {
            int orderIdx = orderDao.createOrder(userIdx, postOrderReq);
            return new PostOrderRes(orderIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

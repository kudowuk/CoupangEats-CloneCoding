package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.OrderDao;
import com.example.demo.src.order.model.GetOrderRes;
import com.example.demo.src.order.model.GetReceiptRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class OrderProvider {

    private final OrderDao orderDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderProvider(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<GetOrderRes> getOrders(int userIdx) throws BaseException {
        try{
            List<GetOrderRes> getOrderRes = orderDao.getOrders(userIdx);
            return getOrderRes;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetReceiptRes getReceipt(int userIdx, int orderIdx) throws BaseException {
        GetReceiptRes getReceiptRes = orderDao.getReceipt(userIdx, orderIdx);
        return getReceiptRes;
    }


}

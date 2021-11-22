package com.example.demo.src.order;

import com.example.demo.src.order.model.GetOrderRes;
import com.example.demo.src.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); }

    // GET 전체 주문 내역 조회 API
    public List<GetOrderRes> getOrders(int userIdx) {
        String getOrderQuery = "SELECT O.orderIdx, S.storeName, O.createdAt, S.storeProfile\n" +
                "FROM Orders O\n" +
                "JOIN Store S on O.storeIdx = S.storeIdx\n" +
                "WHERE O.userIdx = ? AND O.status='Y';";
        int getOrdersByUserIdxParams = userIdx;

        List<GetOrderRes> result = new ArrayList<>();

        List<OrderVO> orderList = this.jdbcTemplate.query(getOrderQuery,
                (rs, rowNum) -> new OrderVO(
                        rs.getInt("orderIdx"),
                        rs.getString("storeName"),
                        rs.getString("createdAt"),
                        rs.getString("storeProfile")),
                getOrdersByUserIdxParams);

        for (OrderVO orderVO : orderList) {
            String sql = "SELECT M.menuName, M.menuPrice, CD.quantity\n" +
                    "FROM Menu M\n" +
                    "JOIN CartDetail CD on M.menuIdx = CD.menuIdx\n" +
                    "join Cart C on CD.cartIdx = C.cartIdx\n" +
                    "join Orders O on C.cartIdx = O.cartIdx\n" +
                    "where O.userIdx = ? and O.orderIdx = ?;";

            List<Menu> menuList = this.jdbcTemplate.query(sql,
                    (rs, rowNum) -> new Menu(
                            rs.getString("menuName"),
                            rs.getInt("menuPrice"),
                            rs.getInt("quantity")),
                    getOrdersByUserIdxParams, orderVO.getOrderIdx());

            GetOrderRes getOrderRes = new GetOrderRes(orderVO.getOrderIdx(), orderVO.getStoreName(), orderVO.getCreatedAt(), orderVO.getStoreProfile(), menuList);

            result.add(getOrderRes);

        }

        return result;

    }

    // GET 주문내역에 특정 영수증 보기 API
    public GetReceiptRes getReceipt(int userIdx, int orderIdx) {
        String getOrderQuery = "SELECT O.orderIdx, S.storeName, O.createdAt, S.deliveryFee, C.discountRate, P.paidType, P.paidAmount\n" +
                "FROM Orders O\n" +
                "JOIN Store S on O.storeIdx = S.storeIdx\n" +
                "JOIN Paid P on O.orderIdx = P.orderIdx\n" +
                "JOIN CouponInput CI on O.userIdx = CI.userIdx\n" +
                "JOIN Coupon C on CI.couponIdx = C.couponIdx\n" +
                "WHERE O.orderIdx = ? AND O.userIdx = ?;";
        int getOrderByOrderIdxParams = orderIdx;
        int getOrderByUserIdxParams = userIdx;

        List<GetReceiptRes> result = new ArrayList<>();

        ReceiptVo receipt = this.jdbcTemplate.queryForObject(getOrderQuery,
                (rs, rowNum) -> new ReceiptVo(
                        rs.getInt("orderIdx"),
                        rs.getString("storeName"),
                        rs.getString("createdAt"),
                        rs.getInt("deliveryFee"),
                        rs.getInt("discountRate"),
                        rs.getString("paidType"),
                        rs.getInt("paidAmount")),
                getOrderByOrderIdxParams, getOrderByUserIdxParams);


            String sql = "SELECT M.menuName, M.menuPrice, CD.quantity\n" +
                    "FROM Menu M\n" +
                    "JOIN CartDetail CD on M.menuIdx = CD.menuIdx\n" +
                    "join Cart C on CD.cartIdx = C.cartIdx\n" +
                    "join Orders O on C.cartIdx = O.cartIdx\n" +
                    "where O.userIdx = ? and O.orderIdx = ?;";

            List<Menu> menuList = this.jdbcTemplate.query(sql,
                    (rs, rowNum) -> new Menu(
                            rs.getString("menuName"),
                            rs.getInt("menuPrice"),
                            rs.getInt("quantity")),
                    getOrderByUserIdxParams, receipt.getOrderIdx());


        GetReceiptRes getReceiptRes = new GetReceiptRes(receipt.getOrderIdx(), receipt.getStoreName(), receipt.getCreatedAt(), receipt.getDeliveryFee(), receipt.getDiscountRate(), receipt.getPaidType(), receipt.getPaidAmount(), menuList);

        result.add(getReceiptRes);

        return getReceiptRes;
    }

    // POST 주문 등록 API
    public int createOrder(int userIdx, PostOrderReq postOrderReq){
        String createOrderQuery = "insert into Orders (userIdx, storeIdx, cartIdx) VALUES (?,?,?)";
        Object[] createOrderParams = new Object[]{userIdx, postOrderReq.getStoreIdx(), postOrderReq.getCartIdx() };
        this.jdbcTemplate.update(createOrderQuery, createOrderParams);

        String lastInsertAdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertAdQuery,int.class);
    }
}

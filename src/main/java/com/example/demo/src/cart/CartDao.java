package com.example.demo.src.cart;

import com.example.demo.src.cart.model.*;
import com.example.demo.src.order.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); }

//    public List<GetCartRes> getCarts() {
//        String getCartQuery = "select menuIdx, optionTypeIdx, cartIdx, storeIdx, toOwner,toDriver,disposableAt from Cart";
//        return this.jdbcTemplate.query(getCartQuery,
//                (rs, rowNum) -> new GetCartRes(
//                        rs.getInt("cartIdx"),
//                        rs.getInt("storeIdx"),
//                        rs.getInt("couponIdx"),
//                        rs.getString("toOwner"),
//                        rs.getString("toDriver"),
//                        rs.getString("disposableAt"))
//        );
//    }

    // GET 카트 담기 조회 API
    public GetCartRes getCart(int userIdx, int cartIdx) {
        String getCartQuery = "SELECT C.cartIdx, A.roadName, A.sections, S.storeName, S.minAmount, C.couponIdx, C.toOwner, C.toDriver, C.disposableAt\n" +
                "FROM Cart C\n" +
                "JOIN Store S on C.storeIdx = S.storeIdx\n" +
                "JOIN Address A on C.userIdx = A.userIdx\n" +
                "WHERE C.cartIdx = ? AND C.userIdx = ? AND C.status = 'Y' AND A.status = 'ACTIVE'";
        int getCartParams = cartIdx;
        int getCartByUserIdxParams = userIdx;

        List<GetCartRes> result = new ArrayList<>();

        CartVo cart = this.jdbcTemplate.queryForObject(getCartQuery,
                (rs, rowNum) -> new CartVo(
                        rs.getInt("cartIdx"),
                        rs.getString("roadName"),
                        rs.getString("sections"),
                        rs.getString("storeName"),
                        rs.getInt("minAmount"),
                        rs.getInt("couponIdx"),
                        rs.getString("toOwner"),
                        rs.getString("toDriver"),
                        rs.getString("disposableAt")),
                getCartParams, getCartByUserIdxParams);

        String sql = "SELECT M.menuName, M.menuPrice, CD.quantity\n" +
                "FROM Menu M\n" +
                "JOIN CartDetail CD on M.menuIdx = CD.menuIdx\n" +
                "join Cart C on CD.cartIdx = C.cartIdx\n" +
                "where C.userIdx = ? and C.cartIdx = ?;";

        List<Menu> menuList = this.jdbcTemplate.query(sql,
                (rs, rowNum) -> new Menu(
                        rs.getString("menuName"),
                        rs.getInt("menuPrice"),
                        rs.getInt("quantity")),
                getCartByUserIdxParams, cart.getCartIdx());

        GetCartRes getCartRes = new GetCartRes(cart.getCartIdx(), cart.getRoadName(), cart.getSections(), cart.getStoreName(), cart.getMinAmount(), cart.getCouponIdx(), cart.getToOwner(), cart.getToDriver(), cart.getDisposableAt(), menuList);

        result.add(getCartRes);

        return getCartRes;
    }

    // POST 카트 담기 등록 API
    public int createCart(int userIdx, PostCartReq postCartReq){
        String createCartQuery = "insert into Cart (userIdx, storeIdx, couponIdx, toOwner, toDriver, disposableAt ) VALUES (?,?,?,?,?,?)";
        Object[] createCartParams = new Object[]{userIdx, postCartReq.getStoreIdx(), postCartReq.getCouponIdx(), postCartReq.getToOwner(), postCartReq.getToDriver(), postCartReq.getDisposableAt() };
        this.jdbcTemplate.update(createCartQuery, createCartParams);

        String lastInsertAdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertAdQuery,int.class);
    }

    public int createCartDetail(int cartIdx, PostCartDetailReq postCartDetailReq){
        String createCartDetailQuery = "insert into CartDetail (cartIdx, menuIdx, quantity, optionTypeIdx, optionIdx) VALUES (?,?,?,?,?)";
        Object[] createCartDetailParams = new Object[]{cartIdx, postCartDetailReq.getMenuIdx(), postCartDetailReq.getQuantity(), postCartDetailReq.getOptionTypeIdx(), postCartDetailReq.getOptionIdx()};
        this.jdbcTemplate.update(createCartDetailQuery, createCartDetailParams);

        String lastInsertAdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertAdQuery,int.class);
    }

    // PATCH 카트 담기 수정 API
    public int modifyCart(PatchCartReq patchCartReq){
        String modifyCartQuery = "update Cart set storeIdx = ?, couponIdx = ?, toOwner = ?, toDriver = ?, disposableAt = ? where cartIdx = ? AND userIdx = ?";
        Object[] modifyCartParams = new Object[]{patchCartReq.getStoreIdx(), patchCartReq.getCouponIdx(), patchCartReq.getToOwner(), patchCartReq.getToDriver(), patchCartReq.getDisposableAt(), patchCartReq.getUserIdx(), patchCartReq.getCartIdx()};

        return this.jdbcTemplate.update(modifyCartQuery,modifyCartParams);
    }

}

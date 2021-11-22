package com.example.demo.src.coupon;

import com.example.demo.src.coupon.model.GetCouponRes;
import com.example.demo.src.coupon.model.PostCouponReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CouponDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); }

    public List<GetCouponRes> getCoupons(int userIdx, int storeIdx) {
        String getCouponQuery = "SELECT storeName, ";
        int getCouponsByUserIdxParams = userIdx;
        int getStoresByStoreIdxParams = storeIdx;
        return this.jdbcTemplate.query(getCouponQuery,
                (rs, rowNum) -> new GetCouponRes(
                        rs.getString("storeName"),
                        rs.getString("couponName"),
                        rs.getInt("minAmount"),
                        rs.getInt("discountRate"),
                        rs.getDate("expiryDate")),
                getCouponsByUserIdxParams, getStoresByStoreIdxParams);
    }

    // POST 리뷰 등록 API
    public int createCoupon(int userIdx, PostCouponReq postCouponReq){
        String createCouponQuery = "insert into CouponInput (userIdx, couponNo) VALUES (?,?)";
        Object[] createCouponParams = new Object[]{userIdx, postCouponReq.getCouponNo() };
        this.jdbcTemplate.update(createCouponQuery, createCouponParams);

        String lastInsertAdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertAdQuery,int.class);
    }

}

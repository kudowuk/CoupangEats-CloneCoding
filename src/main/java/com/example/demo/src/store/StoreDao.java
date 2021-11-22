package com.example.demo.src.store;

import com.example.demo.src.store.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StoreDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); }

    public List<GetStoreRes> getStores(String tag) {
        String getStoresQuery = "select * from Store WHERE storeName = ?";
        return this.jdbcTemplate.query(getStoresQuery,
                (rs, rowNum) -> new GetStoreRes(
                        rs.getInt("storeIdx"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("brandIdx"),
                        rs.getString("storeProfile"),
                        rs.getString("storeName"),
                        rs.getString("storePhone"),
                        rs.getString("storeAddress"),
                        rs.getString("storeInfo"),
                        rs.getString("storeHours"),
                        rs.getString("storeIntro"),
                        rs.getString("storeNotice"),
                        rs.getString("originInfo"),
                        rs.getString("deliveryTime"),
                        rs.getString("deliveryFee"),
                        rs.getString("deliveryInfo"),
                        rs.getString("minAmount"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt"),
                        rs.getString("tag"),
                        rs.getString("status")),tag
        );
    }


    public List<GetStoreRes> getStoresByStoreName(String storeName){
        String getStoresByUserNameQuery = "select * from Store where storeName =?";
        String getStoresByUserNameParams = storeName;
        return this.jdbcTemplate.query(getStoresByUserNameQuery,
                (rs, rowNum) -> new GetStoreRes(
                        rs.getInt("storeIdx"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("brandIdx"),
                        rs.getString("storeProfile"),
                        rs.getString("storeName"),
                        rs.getString("storePhone"),
                        rs.getString("storeAddress"),
                        rs.getString("storeInfo"),
                        rs.getString("storeHours"),
                        rs.getString("storeIntro"),
                        rs.getString("storeNotice"),
                        rs.getString("originInfo"),
                        rs.getString("deliveryTime"),
                        rs.getString("deliveryFee"),
                        rs.getString("deliveryInfo"),
                        rs.getString("minAmount"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt"),
                        rs.getString("tag"),
                        rs.getString("status")),
                getStoresByUserNameParams);
    }

    public GetStoreDetailRes getStoreDetail(int storeIdx) {
        String getStoreDetailQuery = "select storeName, storePhone, storeAddress, storeInfo, storeHours, storeIntro, storeNotice, originInfo from Store where storeIdx = ?";
        int getStoreDetailParams = storeIdx;
        return this.jdbcTemplate.queryForObject(getStoreDetailQuery,
                (rs, rowNum) -> new GetStoreDetailRes(
                        rs.getString("storeName"),
                        rs.getString("storePhone"),
                        rs.getString("storeAddress"),
                        rs.getString("storeInfo"),
                        rs.getString("storeHours"),
                        rs.getString("storeIntro"),
                        rs.getString("storeNotice"),
                        rs.getString("originInfo")),
                getStoreDetailParams);
    }

    public int createStore(PostStoreReq postStoreReq) {
        String createStoreQuery = "insert into Store (categoryIdx, brandIdx, storeProfile, storeName, storePhone, storeAddress, storeInfo, storeHours, storeIntro, storeNotice, originInfo, deliveryTime, deliveryFee, deliveryInfo, minAmount, createdAt, updatedAt, tag, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createStoreParams = new Object[]{postStoreReq.getCategoryIdx(), postStoreReq.getBrandIdx(), postStoreReq.getStoreProfile(), postStoreReq.getStoreName(), postStoreReq.getStorePhone(), postStoreReq.getStoreAddress(), postStoreReq.getStoreInfo(), postStoreReq.getStoreHours(), postStoreReq.getStoreIntro(), postStoreReq.getStoreNotice(), postStoreReq.getOriginInfo(), postStoreReq.getDeliveryTime(), postStoreReq.getDeliveryFee(), postStoreReq.getDeliveryInfo(), postStoreReq.getMinAmount(), postStoreReq.getCreatedAt(), postStoreReq.getUpdatedAt(), postStoreReq.getTag(), postStoreReq.getStatus()};
        this.jdbcTemplate.update(createStoreQuery, createStoreParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int modifyStoreName(PatchStoreReq patchStoreReq) {
        String modifyStoreNameQuery = "update Store set storeName = ? where storeIdx = ? ";
        Object[] modifyStoreNameParams = new Object[]{patchStoreReq.getStoreName(), patchStoreReq.getStoreIdx()};

        return this.jdbcTemplate.update(modifyStoreNameQuery, modifyStoreNameParams);
    }

    public GetStorepageRes getStorepage(int storeIdx) {
        String getStoreQuery = "SELECT S.storeName, S.storeAddress, S.storeHours, S.deliveryFee, S.deliveryInfo, CONCAT(S.deliveryTime, '분') AS deliveryTime,\n" +
                "       S.minAmount ,M.menuCategory, M.menuName, M.menuPrice, M.menuIntro, R.ReviewContent, R.reviewImg1, RA.*\n" +
                "FROM Store S\n" +
                "JOIN (SELECT AVG(reviewScore) AS avgScore, COUNT(*) as reviewCount FROM Review WHERE storeIdx=?) RA\n" +
                "JOIN Menu M on S.storeIdx = M.storeIdx\n" +
                "JOIN Review R on S.storeIdx = R.storeIdx\n" +
                "WHERE S.storeIdx=? AND R.status = 'Y' AND M.status = 'Y' LIMIT 1";
        int getStoreParams = storeIdx;
        return this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs, rowNum) -> new GetStorepageRes(
                        rs.getString("storeName"),
                        rs.getString("storeAddress"),
                        rs.getString("storeHours"),
                        rs.getInt("deliveryFee"),
                        rs.getString("deliveryInfo"),
                        rs.getString("deliveryTime"),
                        rs.getInt("minAmount"),
                        rs.getString("menuCategory"),
                        rs.getString("menuName"),
                        rs.getInt("menuPrice"),
                        rs.getString("menuIntro"),
                        rs.getString("ReviewContent"),
                        rs.getString("reviewImg1"),
                        rs.getDouble("avgScore"),
                        rs.getInt("reviewCount")),
                getStoreParams);
    }

//    메인화면
    public List<GetMainRes> getMain() {
        String getStoresQuery = "SELECT RA.*, S.storeName, S.storeProfile, CONCAT(S.deliveryTime, '분') AS deliveryTime, S.deliveryFee, C.categoryName, C.categoryImg\n" +
                "FROM Store S\n" +
                "JOIN (SELECT AVG(reviewScore) AS avgReview, COUNT(*) as cntReview FROM Review) RA\n" +
                "JOIN Category C on S.storeIdx = C.storeIdx\n" +
                "WHERE S.status='Y' AND C.status='Y'";
        return this.jdbcTemplate.query(getStoresQuery,
                (rs, rowNum) -> new GetMainRes(
                        rs.getDouble("avgReview"),
                        rs.getInt("cntReview"),
                        rs.getString("storeName"),
                        rs.getString("storeProfile"),
                        rs.getString("deliveryTime"),
                        rs.getInt("deliveryFee"),
                        rs.getString("categoryName"),
                        rs.getString("categoryImg"))
                );
        }

    public List<GetMainRes> getMainByStoreName(String storeName){
        String getMainByUserNameQuery = "SELECT RA.*, S.storeName, S.storeProfile, CONCAT(S.deliveryTime, '분') AS deliveryTime, S.deliveryFee, C.categoryName, C.categoryImg\n" +
                "FROM Store S\n" +
                "JOIN (SELECT AVG(reviewScore) AS avgReview, COUNT(*) as cntReview FROM Review WHERE storeIdx=?) RA\n" +
                "JOIN Category C on S.storeIdx = C.storeIdx\n" +
                "WHERE S.status='Y' AND C.status='Y'";
        String getMainByUserNameParams = storeName;
        return this.jdbcTemplate.query(getMainByUserNameQuery,
                (rs, rowNum) -> new GetMainRes(
                        rs.getDouble("avgReview"),
                        rs.getInt("cntReview"),
                        rs.getString("storeName"),
                        rs.getString("storeProfile"),
                        rs.getString("deliveryTime"),
                        rs.getInt("deliveryFee"),
                        rs.getString("categoryNam"),
                        rs.getString("categoryImg")),
                getMainByUserNameParams);
    }


}
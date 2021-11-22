package com.example.demo.src.like;

import com.example.demo.src.address.model.PatchAddressReq;
import com.example.demo.src.like.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class LikeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); }

    // 유저가 즐켜찾기한 가게 조회하기
    public List<GetLikeRes> getLikes(int userIdx, String sort) {
        String getLikeQuery = "SELECT L.likeIdx, L.userIdx, L.storeIdx\n" +
                "FROM Likes L\n" +
                "JOIN Users U on L.userIdx = U.userIdx\n" +
                "WHERE L.userIdx = ? AND L.status = 'Y' AND U.status='Y'";
        int getLikesByUserIdxParams = userIdx;

        if(sort.equalsIgnoreCase("recentAdd")) {
            getLikeQuery += "ORDER BY L.createdAt desc";
        } else if(sort.equalsIgnoreCase("recentOrder")) {

        } else if(sort.equalsIgnoreCase("frequentOrder")) {

        }

        return this.jdbcTemplate.query(getLikeQuery,
                (rs, rowNum) -> new GetLikeRes(
                        rs.getInt("likeIdx"),
                        rs.getInt("userIdx"),
                        rs.getInt("storeIdx")),
                getLikesByUserIdxParams);
    }

    // 즐겨찾기 등록
    public int createLike(int userIdx, PostLikeReq postLikeReq){
        String createLikeQuery = "INSERT INTO Likes (userIdx, storeIdx) VALUES (?,?)";
        Object[] createLikeParams = new Object[]{userIdx, postLikeReq.getStoreIdx() };
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);

        String lastInsertAdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertAdQuery,int.class);
    }

    public int modifyLike(PatchLikeReq patchLikeReq){
        String modifyLikeQuery = "update Likes set status = ? where userIdx = ? AND likeIdx = ?";
        Object[] modifyLikeParams = new Object[]{patchLikeReq.getStatus(), patchLikeReq.getUserIdx(), patchLikeReq.getLikeIdx()};

        return this.jdbcTemplate.update(modifyLikeQuery,modifyLikeParams);
    }

    // 가게스토어 유무 확인
    public int checkStoreIdx(int storeIdx){
        String checkUserIdxQuery = "select exists(select storeIdx from Store where storeIdx = ?)";
        int checkUserIdxParams = storeIdx;
        return this.jdbcTemplate.queryForObject(checkUserIdxQuery, int.class, checkUserIdxParams);
    }

    // 가게 활성화 확인
    public int checkStatusStoreIdx(int storeIdx){
        String checkUserIdxQuery = "select exists(select storeIdx from Store where storeIdx = ? AND Store.status = 'N')";
        int checkUserIdxParams = storeIdx;
        return this.jdbcTemplate.queryForObject(checkUserIdxQuery, int.class, checkUserIdxParams);
    }

    //
//    public int checkDuplicateStoreIdx(int storeIdx){
//        String checkUserIdxQuery = "select userIdx from Likes where storeIdx = ?)";
//        int checkUserIdxParams = storeIdx;
//        return this.jdbcTemplate.queryForObject(checkUserIdxQuery, int.class, checkUserIdxParams);
//    }

}

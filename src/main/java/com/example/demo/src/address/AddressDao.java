package com.example.demo.src.address;

import com.example.demo.src.address.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AddressDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); }

    public List<GetAddressRes> getAddresses(int userIdx) {
        String getAddressQuery = "SELECT A.addressIdx, A.name, A.roadName, A.latitude, A.longitude, A.sections\n" +
                "FROM Address A\n" +
                "JOIN Users U on A.userIdx = U.userIdx\n" +
                "WHERE A.userIdx = ? AND A.status = 'Y' AND U.status='Y'";
        int getAddressesByUserIdxParams = userIdx;
        return this.jdbcTemplate.query(getAddressQuery,
                (rs, rowNum) -> new GetAddressRes(
                        rs.getInt("addressIdx"),
                        rs.getString("name"),
                        rs.getString("roadName"),
                        rs.getString("latitude"),
                        rs.getString("longitude"),
                        rs.getString("sections")),
                getAddressesByUserIdxParams);
    }
    
    // 유저 특정 주소 조회 API
    public GetAddressRes getAddress(int userIdx, int addressIdx) {
        String getAddressQuery = "SELECT A.addressIdx, A.name, A.roadName, A.latitude, A.longitude, A.sections\n" +
                "FROM Address A\n" +
                "JOIN Users U on A.userIdx = U.userIdx\n" +
                "WHERE A.addressIdx = ? AND A.userIdx = ? AND A.status = 'Y' AND U.status='Y';";
        int getAddressByAddressIdxParams = addressIdx;
        int getAddressByUserIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getAddressQuery,
                (rs, rowNum) -> new GetAddressRes(
                        rs.getInt("addressIdx"),
                        rs.getString("name"),
                        rs.getString("roadName"),
                        rs.getString("latitude"),
                        rs.getString("longitude"),
                        rs.getString("sections")),
                getAddressByAddressIdxParams, getAddressByUserIdxParams);
    }

    // 유저 주소 등록 API
    public int createAddress(int userIdx, PostAddressReq postAddressReq){
        String createAddressQuery = "insert into Address (userIdx, name, roadName, latitude, longitude, sections) VALUES (?,?,?,?,?,?)";
        Object[] createAddressParams = new Object[]{userIdx, postAddressReq.getName(), postAddressReq.getRoadName(), postAddressReq.getLatitude(), postAddressReq.getLongitude(), postAddressReq.getSections() };
        this.jdbcTemplate.update(createAddressQuery, createAddressParams);

        String lastInsertAdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertAdQuery,int.class);
    }

    // PATCH 주소 수정 API
    public int modifyAddress(PatchAddressReq patchAddressReq){
        String modifyAddressQuery = "update Address set name = ?, roadName = ?, latitude = ?, longitude = ?, sections = ? where addressIdx = ? AND userIdx = ?";
        Object[] modifyAddressParams = new Object[]{patchAddressReq.getName(), patchAddressReq.getRoadName(), patchAddressReq.getLatitude(), patchAddressReq.getLongitude(), patchAddressReq.getSections(), patchAddressReq.getUserIdx(), patchAddressReq.getAddressIdx()};

        return this.jdbcTemplate.update(modifyAddressQuery,modifyAddressParams);
    }

}

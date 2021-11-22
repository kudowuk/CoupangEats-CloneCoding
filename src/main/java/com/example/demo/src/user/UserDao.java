package com.example.demo.src.user;

import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from Users";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userPhone"))
        );
    }

    public List<GetUserRes> getUsersByEmail(String userId){
        String getUsersByEmailQuery = "select * from Users where userId =?";
        String getUsersByEmailParams = userId;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userPhone")),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(int userIdx){
        String getUserQuery = "select userIdx, userPhone from Users where userIdx = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userPhone")),
                getUserParams);
    }


    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into Users (userId, userPwd, userName, userPhone) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserId(), postUserReq.getUserPwd(), postUserReq.getUserName(), postUserReq.getUserPhone()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    // 유저 유무 확인
    public int checkUserIdx(int userIdx){
        String checkUserIdxQuery = "select exists(select userIdx from Users where userIdx = ?)";
        int checkUserIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserIdxQuery, int.class, checkUserIdxParams);
    }

    // 유저 탈퇴 확인
    public int checkStatusUserIdx(int userIdx){
        String checkUserIdxQuery = "select exists(select userIdx from Users where userIdx = ? AND Users.status = 'N')";
        int checkUserIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserIdxQuery, int.class, checkUserIdxParams);
    }

    // 유저 이메일 확인
    public int checkEmail(String userId){
        String checkEmailQuery = "select exists(select userId from Users where userId = ?)";
        String checkEmailParams = userId;
        return this.jdbcTemplate.queryForObject(checkEmailQuery, int.class, checkEmailParams);
    }

    // 유저 이메일 중복 확인
    public int checkCurrentUserId(String userId){
        //유저인덱스를 쿼리를 통해서 찾아서 아이디를 비교해준다.
        String checkUserIdQuery = "select userIdx from Users where userId = ?";
        String checkUserIdParams = userId;
        return this.jdbcTemplate.queryForObject(checkUserIdQuery, int.class, checkUserIdParams);
    }
//    // 유저 네임 중복 확인
//    public int checkCurrentName(String userName){
//        String checkNameQuery = "select exists(select userName from Users where userName = ?)";
//        String checkNameParams = userName;
//        return this.jdbcTemplate.queryForObject(checkNameQuery,
//                int.class,
//                checkNameParams);
//    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update Users set userId = ?, userPwd = ?, userName = ?, userPhone = ? where userIdx = ?";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserId(), patchUserReq.getUserPwd(), patchUserReq.getUserName(), patchUserReq.getUserPhone(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, userId, userPwd, userName, userPhone from Users where userId = ?";
        String getPwdParams = postLoginReq.getUserId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("userId"),
                        rs.getString("userPwd"),
                        rs.getString("userName"),
                        rs.getString("userPhone")
                ),
                getPwdParams
        );

    }



}

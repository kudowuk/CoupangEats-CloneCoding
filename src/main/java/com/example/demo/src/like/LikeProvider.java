package com.example.demo.src.like;

import com.example.demo.config.BaseException;
import com.example.demo.src.like.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class LikeProvider {

    private final LikeDao likeDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public LikeProvider(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    public List<GetLikeRes> getLikes(int userIdx, String sort) throws BaseException {
        try{
            List<GetLikeRes> getLikeRes = likeDao.getLikes(userIdx, sort);
            return getLikeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 가게 상태 확인
//    public int checkStatus(int storeIdx) throws BaseException{
//        try{
//            return likeDao.checkStatus(storeIdx);
//        } catch (Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

}

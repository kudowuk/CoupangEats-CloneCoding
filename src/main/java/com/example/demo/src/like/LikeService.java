package com.example.demo.src.like;

import com.example.demo.config.BaseException;
import com.example.demo.src.like.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional(rollbackFor = BaseException.class)
public class LikeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LikeDao likeDao;
    private final LikeProvider likeProvider;

    @Autowired
    public LikeService(LikeDao likeDao, LikeProvider likeProvider) {
        this.likeDao = likeDao;
        this.likeProvider = likeProvider;
    }

    //POST
    public PostLikeRes createLike(int userIdx, PostLikeReq postLikeReq) throws BaseException {

//        if(likeProvider.checkStatus(postLikeReq.getStoreIdx()) == 1){
//            throw new BaseException(PATCH_USERID_EXISTS_EMAIL);
//        }

        try {
            int likeIdx = likeDao.createLike(userIdx, postLikeReq);
            return new PostLikeRes(likeIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyLike(PatchLikeReq patchLikeReq) throws BaseException {
        try {
            int result = likeDao.modifyLike(patchLikeReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_LIKE);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}

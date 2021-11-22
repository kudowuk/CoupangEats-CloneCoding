package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.src.store.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class StoreProvider {

    private final StoreDao storeDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public StoreProvider(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    public List<GetStoreRes> getStores(String tag) throws BaseException {
        try{
            List<GetStoreRes> getStoreRes = storeDao.getStores(tag);
            return getStoreRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetStoreRes> getStoresByStoreName(String storeName) throws BaseException{
        try{
            List<GetStoreRes> getStoresRes = storeDao.getStoresByStoreName(storeName);
            return getStoresRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 가게상세정보
    public GetStoreDetailRes getStoreDetail(int storeIdx) throws BaseException {
        try {
            GetStoreDetailRes getStoreDetailRes = storeDao.getStoreDetail(storeIdx);
            return getStoreDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

//  가게 화면
    public GetStorepageRes getStorepage(int storeIdx) throws BaseException {
//        try {
//            GetStorepageRes getStorepageRes = storeDao.getStorepage(storeIdx);
//            return getStorepageRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }

        GetStorepageRes getStorepageRes = storeDao.getStorepage(storeIdx);
        return getStorepageRes;
    }

//    메인 화면
    public List<GetMainRes> getMain() throws BaseException {
        try{
            List<GetMainRes> getMainRes = storeDao.getMain();
            return getMainRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
    }

}


    public List<GetMainRes> getMainByStoreName(String storeName) throws BaseException{
        try{
            List<GetMainRes> getMainRes = storeDao.getMainByStoreName(storeName);
            return getMainRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}

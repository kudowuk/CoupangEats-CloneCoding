package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.src.store.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional(rollbackFor = BaseException.class)
public class StoreService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StoreDao storeDao;
    private final StoreProvider storeProvider;

    @Autowired
    public StoreService(StoreDao storeDao, StoreProvider storeProvider) {
        this.storeDao = storeDao;
        this.storeProvider = storeProvider;
    }

}

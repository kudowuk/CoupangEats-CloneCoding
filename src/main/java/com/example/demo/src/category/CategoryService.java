package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.src.category.model.*;
import com.example.demo.src.store.model.PostStoreRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL_USERNAME;

@Service
@Transactional(rollbackFor = BaseException.class)
public class CategoryService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CategoryDao categoryDao;
    private final CategoryProvider categoryProvider;

    @Autowired
    public CategoryService(CategoryDao categoryDao, CategoryProvider categoryProvider) {
        this.categoryDao = categoryDao;
        this.categoryProvider = categoryProvider;
    }


}

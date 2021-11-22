package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.src.category.model.*;
import com.example.demo.src.store.model.GetMainRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class CategoryProvider {

    private final CategoryDao categoryDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CategoryProvider(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<GetCategoryRes> getCategories() throws BaseException {
        try{
            List<GetCategoryRes> getCategoryRes = categoryDao.getCategories();
            return getCategoryRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCategoryRes> getCategoriesByCategoryName(String categoryName) throws BaseException{
        try{
            List<GetCategoryRes> getCategoryRes = categoryDao.getCategoriesByCategoryName(categoryName);
            return getCategoryRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}

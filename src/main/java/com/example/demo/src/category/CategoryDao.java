package com.example.demo.src.category;

import com.example.demo.src.category.model.*;
import com.example.demo.src.store.model.PatchStoreReq;
import com.example.demo.src.store.model.PostStoreReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CategoryDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource); }

    public List<GetCategoryRes> getCategories(){
        String getCategoryQuery = "SELECT categoryName, categoryImg FROM Category WHERE status='Y'";
        return this.jdbcTemplate.query(getCategoryQuery,
                (rs, rowNum) -> new GetCategoryRes(
                        rs.getString("categoryName"),
                        rs.getString("categoryImg"))
        );
    }

    public List<GetCategoryRes> getCategoriesByCategoryName(String categoryName){
        String getCategoryByCategoryNameQuery = "SELECT categoryName, categoryImg FROM Category WHERE status='Y'";
        String getCategoryByCategoryNameParams = categoryName;
        return this.jdbcTemplate.query(getCategoryByCategoryNameQuery,
                (rs, rowNum) -> new GetCategoryRes(
                        rs.getString("categoryName"),
                        rs.getString("categoryImg")),
                getCategoryByCategoryNameParams);
    }



}

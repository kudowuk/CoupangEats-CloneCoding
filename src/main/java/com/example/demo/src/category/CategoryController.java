package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.category.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/categories")
public class CategoryController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CategoryProvider categoryProvider;
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private final JwtService jwtService;

    public CategoryController(CategoryProvider categoryProvider, CategoryService categoryService, JwtService jwtService){
        this.categoryProvider = categoryProvider;
        this.categoryService = categoryService;
        this.jwtService = jwtService;
    }

    /**
     * 카테고리 조회 API
     * [GET] /category
     * 카테고리 검색 조회 API
     * [GET] /cateogory? categoryName=
     * @return BaseResponse<List<GetCategoryRes>>
     */
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/categories
    public BaseResponse<List<GetCategoryRes>> getCategories(@RequestParam(required = false) String categoryName) {
        try{
            // 이것만해도됨
            //jwtService.getUserIdx();

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdxByJwt != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

                if (categoryName == null) {
                List<GetCategoryRes> getCategoryRes = categoryProvider.getCategories();
                return new BaseResponse<>(getCategoryRes);
            }
            // Get
            List<GetCategoryRes> getCategoryRes = categoryProvider.getCategoriesByCategoryName(categoryName);
            return new BaseResponse<>(getCategoryRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}

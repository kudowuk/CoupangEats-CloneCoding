package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.address.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

@RestController
@RequestMapping("/app/users")
public class AddressController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AddressProvider addressProvider;
    @Autowired
    private final AddressService addressService;
    @Autowired
    private final JwtService jwtService;

    public AddressController(AddressProvider addressProvider, AddressService addressService, JwtService jwtService){
        this.addressProvider = addressProvider;
        this.addressService = addressService;
        this.jwtService = jwtService;
    }

    // 유저가 갖고있는 주소들 조회하기
    @ResponseBody
    @GetMapping("/{userIdx}/addresses") // (GET) 127.0.0.1:9000/app/userIdx?=1/addresses/
    public BaseResponse<List<GetAddressRes>> getAddresses(@PathVariable("userIdx") int userIdx) {

        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetAddressRes> getAddressRes = addressProvider.getAddresses(userIdx);
            return new BaseResponse<>(getAddressRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    
    // 유저가 갖고 있는 하나의 주소를 주회하기
    @ResponseBody
    @GetMapping("/{userIdx}/addresses/{addressIdx}") // (GET) 127.0.0.1:9000/app/userIdx?=1/addresses/addressIdx?=1/
    public BaseResponse<GetAddressRes> getAddress(@PathVariable("userIdx") int userIdx, @PathVariable("addressIdx") int addressIdx) {
        try {

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            GetAddressRes getAddressRes = addressProvider.getAddress(userIdx, addressIdx);
            return new BaseResponse<>(getAddressRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 주소등록 API
     * [POST] /addresses
     * @return BaseResponse<PostAddressRes>
     */
    // Body
    // 패스배리어블로 받아오기
    @ResponseBody
    @PostMapping("/{userIdx}/addresses")
    public BaseResponse<PostAddressRes> createAddress(@PathVariable("userIdx") int userIdx, @RequestBody PostAddressReq postAddressReq) {

        if(postAddressReq.getName().length() > 30) {
            return new BaseResponse<>(POST_ADDRESSES_OVER_NAME);
        }

        //비밀번호 정규표현
        if(!isRegexLatitude(postAddressReq.getLatitude())){
            return new BaseResponse<>(POST_ADDRESSES_INVALID_LATITUDE);
        }
        if(!isRegexLongitude(postAddressReq.getLongitude())){
            return new BaseResponse<>(POST_ADDRESSES_INVALID_LONGITUDE);
        }

        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PostAddressRes postAddressRes = addressService.createAddress(userIdx, postAddressReq);
            return new BaseResponse<>(postAddressRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}/addresses/{addressIdx}")
    public BaseResponse<String> modifyAddress(@PathVariable("userIdx") int userIdx, @PathVariable("addressIdx") int addressIdx, @RequestBody Address address){
        if(address.getName().length() > 30) {
            return new BaseResponse<>(POST_ADDRESSES_OVER_NAME);
        }
        //비밀번호 정규표현
        if(!isRegexLatitude(address.getLatitude())){
            return new BaseResponse<>(POST_ADDRESSES_INVALID_LATITUDE);
        }
        if(!isRegexLongitude(address.getLongitude())){
            return new BaseResponse<>(POST_ADDRESSES_INVALID_LONGITUDE);
        }


        try {

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchAddressReq patchAddressReq = new PatchAddressReq(userIdx, addressIdx, address.getName(), address.getRoadName(), address.getLatitude(), address.getLongitude(), address.getSections());
            addressService.modifyAddress(patchAddressReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }




}

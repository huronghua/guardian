package com.banmatrip.guardian.dto.response.base;

import com.banmatrip.guardian.core.constants.Constants;
import com.banmatrip.guardian.core.enums.ResponseStatusEnum;
import org.springframework.stereotype.Component;

/**
 * 响应对象处理器
 */
@Component
public class ResponseHandler {
    public BaseResponse getBaseResponse(Object obj, ResponseStatusEnum responseStatus) {
        BaseResponse baseResponse = getBaseResponse(responseStatus);
        baseResponse.setData(obj == null ? Constants.NULL_DATA : obj);

        return baseResponse;
    }

    public BaseResponse getBaseResponse(ResponseStatusEnum responseStatus) {
        BaseResponse baseResponse = new BaseResponse();
        if (responseStatus != null) {
            baseResponse.setMessage(responseStatus.getMessage());
            baseResponse.setCode(responseStatus.getCode());
            baseResponse.setData(Constants.NULL_DATA);
        }

        return baseResponse;
    }
}
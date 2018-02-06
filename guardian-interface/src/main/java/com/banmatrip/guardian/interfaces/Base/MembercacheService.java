package com.banmatrip.guardian.interfaces.Base;

import io.swagger.models.auth.In;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface MembercacheService {
    /*设置membercache中对应orange用户Id的权限变动flag为1*/

    public void notifyPermissionChangeByAdminId(List<Integer> adminIdList);
}

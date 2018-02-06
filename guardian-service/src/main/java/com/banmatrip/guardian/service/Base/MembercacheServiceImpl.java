package com.banmatrip.guardian.service.Base;

import com.banmatrip.guardian.interfaces.Base.MembercacheService;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Eric-hu
 * @Description:
 * @create 2018-01-19 13:51
 * @Copyright: 2018 www.banmatrip.com All rights reserved.
 **/

@Service
public class MembercacheServiceImpl implements MembercacheService{

    @Override
    public void notifyPermissionChangeByAdminId(List<Integer> adminIdList){
        MemcachedClientBuilder memcachedClientBuilder = new XMemcachedClientBuilder(AddrUtil.getAddresses("127.0.0.1:11222"),new int[]{3});
        MemcachedClient memcachedClient = null;
        try {
            memcachedClient = memcachedClientBuilder.build();
            for(Integer adminId : adminIdList){
                memcachedClient.set("update_power_cache_"+adminId,0,"abcd");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
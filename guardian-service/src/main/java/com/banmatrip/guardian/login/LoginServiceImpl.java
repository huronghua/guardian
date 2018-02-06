package com.banmatrip.guardian.login;

import com.banmatrip.guardian.domain.LoginLog;
import com.banmatrip.guardian.domain.User;
import com.banmatrip.guardian.interfaces.login.LoginService;
import com.banmatrip.guardian.repository.mapper.login.LoginLogMapper;
import com.banmatrip.guardian.vo.login.SystemConfigVo;
import com.banmatrip.guardian.vo.login.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jepson
 * @Description:
 * @create 2017-09-22 23:30
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    LoginLogMapper loginLogMapper;


    /**
     * 登录
     *
     * @param loginLog
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = RuntimeException.class)
    public void insertLoginLog(LoginLog loginLog) {
        try {
            loginLogMapper.insertLoginLog(loginLog);
        } catch (Exception e) {
            throw new RuntimeException("保存登录日志失败！" + e.getMessage());
        }
    }

    /***
     *  登出

     * @param loginLog
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = RuntimeException.class)
    public void updateSignoutLog(LoginLog loginLog) {
         try {
             loginLogMapper.updateLoginLog(loginLog);
         } catch(Exception e) {
            throw new RuntimeException("修改登出日志失败!" + e.getMessage());
         }
    }

    /**
     * 通过账号获取用户信息
     *
     * @param account
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public UserVo getUserByAccount(String account) {
        UserVo userVo = null;
        try {
            userVo = loginLogMapper.selectUserByAccount(account);
        } catch(Exception e) {
            throw new RuntimeException("查询用户信息失败！" + e.getMessage());
        }
        return userVo;
    }

    /**
     * 通过账号获取最近登录日志
     *
     * @param account
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public LoginLog getLatestLoginLogByAccount(String account) {
        LoginLog loginLog = null;
        try {
           loginLog = loginLogMapper.selectLatestLoginLogByAccount(account);
        } catch(Exception e) {
            throw new RuntimeException("获取最新登录记录失败！" + e.getMessage());
        }
        return loginLog;
    }

    /**
     * 获取系统配置列表
     *
     * @return
     */
    @Override
    public List<SystemConfigVo> getSystemConfigList() {
        List<SystemConfigVo> list = null;
        try {
            list = loginLogMapper.selectSystemConfigList();
        } catch(Exception e) {
            throw new RuntimeException("获取系统配置列表失败！" + e.getMessage());
        }
        return list;
    }
}
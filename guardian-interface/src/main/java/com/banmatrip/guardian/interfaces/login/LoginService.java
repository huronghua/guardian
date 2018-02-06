package com.banmatrip.guardian.interfaces.login;

import com.banmatrip.guardian.domain.LoginLog;
import com.banmatrip.guardian.vo.login.SystemConfigVo;
import com.banmatrip.guardian.vo.login.UserVo;

import java.util.List;

/**
 *  create by jepson on 2017/09/22
 */
public interface LoginService {

    /**
     *  登录
     * @param loginLog
     */
    public void insertLoginLog(LoginLog loginLog);

    /***
     *  登出
     * @param loginLog
     */
    public void updateSignoutLog(LoginLog loginLog);

    /**
     *
     *  通过账号获取用户信息
     *
     * @param account
     * @return
     */
    public UserVo getUserByAccount(String account);

    /**
     * 通过账号获取最近登录日志
     *
     * @param account
     * @return
     */
    public LoginLog getLatestLoginLogByAccount(String account);


    /**
     * 获取系统配置列表
     *
     * @return
     */
    public List<SystemConfigVo> getSystemConfigList();

}

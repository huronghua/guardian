package com.banmatrip.guardian.repository.mapper.login;

import com.banmatrip.guardian.domain.LoginLog;
import com.banmatrip.guardian.vo.login.SystemConfigVo;
import com.banmatrip.guardian.vo.login.UserVo;

import java.util.List;

/***
 *  create by jepson on 2017/09/22
 */
public interface LoginLogMapper {

    /**
     * 新增登录日志
     * @param loginLog
     * @return
     */
    int insertLoginLog(LoginLog loginLog);

    /**
     * 修改登录日志
     * @param loginLog
     * @return
     */
    int updateLoginLog(LoginLog loginLog);

    /**
     * 查询用户信息
     *
     * @param account
     * @return
     */
    UserVo selectUserByAccount(String account);

    /**
     * 查询最近一条用户登录记录
     * @param account
     * @return
     */
    LoginLog selectLatestLoginLogByAccount(String account);

    /**
     * 查询系统配置列表
     *
     * @return
     */
    List<SystemConfigVo> selectSystemConfigList();
}

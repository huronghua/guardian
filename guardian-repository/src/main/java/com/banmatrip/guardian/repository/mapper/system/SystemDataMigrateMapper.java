package com.banmatrip.guardian.repository.mapper.system;

/**
 * 系统数据迁移
 */
public interface SystemDataMigrateMapper {


    /**
     * adminAccount插入SsoUser
     *
     * @return
     */
    public int adminAccountInsertIntoSsoUser();

    /**
     * ssoUser表信息插入AdminAccount
     *
     * @return
     */
    public int ssoUserInsertIntoAdminAccount();

    /**
     * 密码更新
     * @return
     */
    public int updatePassword();

    /**
     * 删除对照表
     * @return
     */
    public int deleteSsoContrastId();

    /**
     * 插入对照表信息
     * @return
     */
    public int insertIntoSsoContrastId();

    /**
     * 插入未知部门
     *
     * @return
     */
    public int insertIntoNoneDepartment();

    /**
     * 插入部门组信息
     *
     * @return
     */
    public int insertIntoSsoDepartmentGroup();


    /**
     * 更新部门ID
     *
     * @return
     */
    public int updateDepartmentIdOfSsoUser();


    /**
     * 密码初始化
     */
    public int updateInitPassword();
}

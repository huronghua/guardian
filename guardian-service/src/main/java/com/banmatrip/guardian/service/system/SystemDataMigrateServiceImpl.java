package com.banmatrip.guardian.service.system;

import com.banmatrip.guardian.interfaces.system.SystemDataMigrateService;
import com.banmatrip.guardian.repository.mapper.system.SystemDataMigrateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jepson
 * @Description: 数据迁移
 * @create 2017-12-21 17:40
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Service
public class SystemDataMigrateServiceImpl implements SystemDataMigrateService {

    /**
     * 日志句柄
     */
    private static final Logger log = LoggerFactory.getLogger(SystemDataMigrateServiceImpl.class);

    @Autowired
    SystemDataMigrateMapper systemDataMigrateMapper;


    /**
     * 数据迁移
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = RuntimeException.class, timeout = 600)
    public void migrateSystemData() {
        log.info("开始迁移...");
        //0.密码初始化
        int updateInitPasswordCount = systemDataMigrateMapper.updateInitPassword();
        log.info("密码更新记录条数" + updateInitPasswordCount);
        //1.将admin_acocunt表迁入sso_user表中
        int ssoUserCount = systemDataMigrateMapper.adminAccountInsertIntoSsoUser();
        //2.将sso_user表信息迁入admin_account表中
        int adminAccountCount = systemDataMigrateMapper.ssoUserInsertIntoAdminAccount();
        log.info("admin_account迁入sso_user表记录条数" +
                ssoUserCount + "sso_user迁入admin_account表记录条数" + adminAccountCount);
        /**有需要的则做迁移**/
        if (ssoUserCount > 0 || adminAccountCount > 0) {
            log.info("进入迁移逻辑...");
            //3.更改sso_user表中的部门ID
            systemDataMigrateMapper.updateDepartmentIdOfSsoUser();
            //4.将sso_user表密码更改成admin_account表中的密码
            systemDataMigrateMapper.updatePassword();
            //5.清除sso_contrast_id表
            systemDataMigrateMapper.deleteSsoContrastId();
            //6.sso_contrast_id表重新加入数据
            systemDataMigrateMapper.insertIntoSsoContrastId();
            //7.初始化未知部门
            systemDataMigrateMapper.insertIntoNoneDepartment();
            //8.sso_department_group表加入数据
            systemDataMigrateMapper.insertIntoSsoDepartmentGroup();
        }
        log.info("迁移结束...");
    }
}
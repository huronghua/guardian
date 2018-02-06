package com.banmatrip.guardian.service;

import com.banmatrip.guardian.domain.Department;
import com.banmatrip.guardian.domain.Role;
import com.banmatrip.guardian.domain.User;
import com.banmatrip.guardian.dto.response.user.UserSecurityDetail;
import com.banmatrip.guardian.repository.mapper.membership.DepartmentMapper;
import com.banmatrip.guardian.repository.mapper.membership.UserMapper;
import com.banmatrip.guardian.repository.mapper.membership.UserRoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jepson
 * @Description: 用户详情服务
 * @create 2017-09-18 14:55
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 加载用户账户信息
     * @param account
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        UserSecurityDetail user = this.assembleUserSecurityDetail(account);
        if (null == user) {
            throw new UsernameNotFoundException("UserAccount " + account + " not found");
        }
        return new SecurityUser(user);
    }

    /**
     * 查询用户
     * @param account
     * @return
     */
    public User getUser(String account) {
        return userMapper.selectByAccount(account);
    }

    /**
     * 查询用户部门
     * @param departmentGroupId
     * @return
     */
    public List<Department> getDepartment(Integer departmentGroupId) {
        return departmentMapper.selectDepartmentByGroupId(departmentGroupId);
    }

    /**
     * 获取用户角色
     * @param userId
     * @return
     */
    public List<Role> getRole(Integer userId) {
        return userRoleMapper.selectRoleByUserId(userId);
    }


    /**
     * 组装用户安全实体类
     * @return
     */
    public UserSecurityDetail assembleUserSecurityDetail(String account) {
        UserSecurityDetail userSecurityDetail = new UserSecurityDetail();
        /**查询用户信息**/
        User user = this.getUser(account);
        if (null != user) {
            BeanUtils.copyProperties(user, userSecurityDetail);
            /**根据部门ID查询部门信息**/
            List<Department> listDepartment = this.getDepartment(user.getDepartmentId());
            /**根据用户ID查询权限信息**/
            List<Role> listRole = this.getRole(user.getId());
            userSecurityDetail.setDepartments(listDepartment);
            userSecurityDetail.setRoles(listRole);
        }
        return userSecurityDetail;
    }
}

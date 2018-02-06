package com.banmatrip.guardian.service;

import com.banmatrip.guardian.domain.Role;
import com.banmatrip.guardian.domain.User;
import com.banmatrip.guardian.dto.response.user.UserSecurityDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author jepson
 * @Description: 用户安全信息
 * @create 2017-09-18 15:41
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public class SecurityUser extends UserSecurityDetail implements UserDetails{
    public SecurityUser(UserSecurityDetail user) {
        if(user != null)
        {
            this.setAccount(user.getAccount());
            this.setName(user.getName());
            this.setCellphone(user.getCellphone());
            this.setEmployeeId(user.getEmployeeId());
            this.setOrangeAccount(user.getOrangeAccount());
            this.setPositionId(user.getPositionId());
            this.setCreateId(user.getCreateId());
            this.setCreateTime(user.getCreateTime());
            this.setUpdateId(user.getUpdateId());
            this.setUpdateTime(user.getUpdateTime());
            this.setEmail(user.getEmail());
            this.setPassword(user.getPassword());
            this.setDepartments(user.getDepartments());
            this.setRoles(user.getRoles());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<Role> roles = this.getRoles();
        if(roles != null)
        {
            for (Role role : roles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return super.getAccount();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
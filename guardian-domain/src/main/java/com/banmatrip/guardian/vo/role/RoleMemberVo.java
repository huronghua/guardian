package com.banmatrip.guardian.vo.role;

import com.banmatrip.guardian.domain.User;
import lombok.Data;

@Data
public class RoleMemberVo {
    private Integer id;

    private String memberName;

    private String positionId;

    private String position;

    private Integer roleId;

    private String email;

    private String employeeId;

    private String cellphone;

    private String textSearch;
}

package com.ktjiaoyu.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 王伟杰
 * @since 2021-09-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")

public class SysUser implements Serializable {


    @TableId(value = "usr_id", type = IdType.AUTO)
    @ApiModelProperty(value="用户id")
    private Integer usrId;

    private String usrName;

    private String usrPassword;

    @TableField("usr_role_id")
    private Integer usrRoleId;

    private Integer usrFlag;

    @TableField(exist = false,value = "role_name")
    private String roleName;
    @TableField(exist = false)
    private int pageIndex;
    @TableField(exist = false)
    private int pageSize;

}

package com.ktjiaoyu.demo.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("sys_role_right")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleRight implements Serializable {

  @TableId(value = "rf_id", type = IdType.AUTO)
  private Integer rfId;
  @TableField("rf_role_id")
  private Integer rfRoleId;
  @TableField("rf_right_code")
  private String rfRightCode;

}

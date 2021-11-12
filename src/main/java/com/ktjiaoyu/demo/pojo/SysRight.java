package com.ktjiaoyu.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@TableName("sys_right")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRight implements Serializable {

  @TableId(value = "right_code")

  private String rightCode;
  @TableField("right_Parent_code")
  private String rightParentCode;
  @TableField("right_type")
  private String rightType;
  @TableField("right_text")
  private String rightText;
  @TableField("right_url")
  private String rightUrl;
  @TableField("right_tip")
  private String rightTip;

  @TableField(exist = false)
  private int pageIndex;
  @TableField(exist = false)
  private int pageSize;

  @TableField(value = "count",exist = false)
  private int count;
}

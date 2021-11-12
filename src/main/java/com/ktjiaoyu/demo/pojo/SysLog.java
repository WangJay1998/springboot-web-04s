package com.ktjiaoyu.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_log")
public class SysLog {
  @TableId(value = "id")
  private Long id;
  @TableField("user_code")
  private String userCode;
  private String ip;
  private String type;
  private String descs;
  private String model;
  @TableField("cur_time")

  private Date curTime;
}

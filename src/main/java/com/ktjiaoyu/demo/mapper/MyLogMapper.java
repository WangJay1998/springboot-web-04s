package com.ktjiaoyu.demo.mapper;

import com.ktjiaoyu.demo.pojo.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MyLogMapper {
    @Insert("insert into sys_log(user_code,ip,`type`,`descs`,model,cur_time) values (#{userCode},#{ip},#{type},#{descs},#{model},#{curTime})")
    void insert(SysLog sysLog);
}

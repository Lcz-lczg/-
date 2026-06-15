package com.spboot.app.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spboot.app.pojo.Qiudui;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("球队")
public interface QiuduiMapper extends BaseMapper<Qiudui> {}

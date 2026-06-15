package com.spboot.app.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spboot.app.pojo.Jiaolian;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("教练")
public interface JiaolianMapper extends BaseMapper<Jiaolian> {}

package com.spboot.app.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spboot.app.pojo.Bisaibaoming;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("比赛报名")
public interface BisaibaomingMapper extends BaseMapper<Bisaibaoming> {}

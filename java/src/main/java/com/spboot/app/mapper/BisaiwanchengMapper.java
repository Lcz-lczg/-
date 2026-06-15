package com.spboot.app.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spboot.app.pojo.Bisaiwancheng;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("比赛完成")
public interface BisaiwanchengMapper extends BaseMapper<Bisaiwancheng> {}

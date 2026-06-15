package com.spboot.app.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spboot.app.pojo.Bisaixinxi;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("比赛信息")
public interface BisaixinxiMapper extends BaseMapper<Bisaixinxi> {}

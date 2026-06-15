package com.spboot.app.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spboot.app.pojo.Qiuyuanxinxi;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("球员信息")
public interface QiuyuanxinxiMapper extends BaseMapper<Qiuyuanxinxi> {}

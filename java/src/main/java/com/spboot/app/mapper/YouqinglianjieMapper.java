package com.spboot.app.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spboot.app.pojo.Youqinglianjie;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("友情链接")
public interface YouqinglianjieMapper extends BaseMapper<Youqinglianjie> {}

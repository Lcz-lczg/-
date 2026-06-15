package com.spboot.app.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spboot.app.pojo.Guanzhong;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("观众")
public interface GuanzhongMapper extends BaseMapper<Guanzhong> {}

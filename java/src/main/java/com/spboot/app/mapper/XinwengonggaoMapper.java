package com.spboot.app.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spboot.app.pojo.Xinwengonggao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("新闻公告")
public interface XinwengonggaoMapper extends BaseMapper<Xinwengonggao> {}

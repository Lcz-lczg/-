package com.spboot.app.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;
import java.util.*;

/**
 * 友情链接 表 youqinglianjie.
 */
@TableName("youqinglianjie")
public class Youqinglianjie implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 网站名称.
    private String wangzhanmingcheng;
    // 网址.
    private String wangzhi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWangzhanmingcheng() {
        return wangzhanmingcheng;
    }

    public void setWangzhanmingcheng(String wangzhanmingcheng) {
        this.wangzhanmingcheng = wangzhanmingcheng == null ? "" : wangzhanmingcheng.trim();
    }

    public String getWangzhi() {
        return wangzhi;
    }

    public void setWangzhi(String wangzhi) {
        this.wangzhi = wangzhi == null ? "" : wangzhi.trim();
    }
}

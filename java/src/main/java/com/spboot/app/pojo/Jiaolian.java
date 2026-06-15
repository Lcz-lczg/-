package com.spboot.app.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;
import java.util.*;

/**
 * 教练 表 jiaolian.
 */
@TableName("jiaolian")
public class Jiaolian implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 工号.
    private String gonghao;

    // 密码.
    @JsonIgnore // 这个字段不返回前端
    private String mima;

    // 职称.
    private String zhicheng;
    // 姓名.
    private String xingming;
    // 性别.
    // 可选项:男,女。
    private String xingbie;
    // 资质证书.
    private String zizhizhengshu;
    // 执教经验.
    private String zhijiaojingyan;
    // 联系电话.
    private String lianxidianhua;
    // 头像.
    private String touxiang;
    // 是否审核
    private String issh;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGonghao() {
        return gonghao;
    }

    public void setGonghao(String gonghao) {
        this.gonghao = gonghao == null ? "" : gonghao.trim();
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima == null ? "" : mima.trim();
    }

    public String getZhicheng() {
        return zhicheng;
    }

    public void setZhicheng(String zhicheng) {
        this.zhicheng = zhicheng == null ? "" : zhicheng.trim();
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming == null ? "" : xingming.trim();
    }

    public String getXingbie() {
        return xingbie;
    }

    public void setXingbie(String xingbie) {
        this.xingbie = xingbie == null ? "" : xingbie.trim();
    }

    public String getZizhizhengshu() {
        return zizhizhengshu;
    }

    public void setZizhizhengshu(String zizhizhengshu) {
        this.zizhizhengshu = zizhizhengshu == null ? "" : zizhizhengshu.trim();
    }

    public String getZhijiaojingyan() {
        return zhijiaojingyan;
    }

    public void setZhijiaojingyan(String zhijiaojingyan) {
        this.zhijiaojingyan = zhijiaojingyan == null ? "" : zhijiaojingyan.trim();
    }

    public String getLianxidianhua() {
        return lianxidianhua;
    }

    public void setLianxidianhua(String lianxidianhua) {
        this.lianxidianhua = lianxidianhua == null ? "" : lianxidianhua.trim();
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang == null ? "" : touxiang.trim();
    }

    public String getIssh() {
        return issh;
    }

    public void setIssh(String issh) {
        this.issh = issh == null ? "" : issh.trim();
    }
}

package com.spboot.app.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;
import java.util.*;

/**
 * 球员信息 表 qiuyuanxinxi.
 */
@TableName("qiuyuanxinxi")
public class Qiuyuanxinxi implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 账号.
    private String zhanghao;

    // 密码.
    @JsonIgnore // 这个字段不返回前端
    private String mima;

    // 姓名.
    private String xingming;
    // 性别.
    // 可选项:男,女。
    private String xingbie;
    // 联系电话.
    private String lianxidianhua;
    // 年龄.
    private Integer nianling;
    // 身高.
    // 单位：/cm。
    private String shengao;
    // 体重.
    // 单位：/kg。
    private String tizhong;
    // 所属球队.
    private String suoshuqiudui;
    // 球员位置.
    private String qiuyuanweizhi;
    // 球衣号.
    private String qiuyihao;
    // 个人简介.
    private String gerenjianjie;
    // 头像.
    private String touxiang;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZhanghao() {
        return zhanghao;
    }

    public void setZhanghao(String zhanghao) {
        this.zhanghao = zhanghao == null ? "" : zhanghao.trim();
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima == null ? "" : mima.trim();
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

    public String getLianxidianhua() {
        return lianxidianhua;
    }

    public void setLianxidianhua(String lianxidianhua) {
        this.lianxidianhua = lianxidianhua == null ? "" : lianxidianhua.trim();
    }

    public Integer getNianling() {
        return nianling;
    }

    public void setNianling(Integer nianling) {
        this.nianling = nianling == null ? 0 : nianling;
    }

    public String getShengao() {
        return shengao;
    }

    public void setShengao(String shengao) {
        this.shengao = shengao == null ? "" : shengao.trim();
    }

    public String getTizhong() {
        return tizhong;
    }

    public void setTizhong(String tizhong) {
        this.tizhong = tizhong == null ? "" : tizhong.trim();
    }

    public String getSuoshuqiudui() {
        return suoshuqiudui;
    }

    public void setSuoshuqiudui(String suoshuqiudui) {
        this.suoshuqiudui = suoshuqiudui == null ? "" : suoshuqiudui.trim();
    }

    public String getQiuyuanweizhi() {
        return qiuyuanweizhi;
    }

    public void setQiuyuanweizhi(String qiuyuanweizhi) {
        this.qiuyuanweizhi = qiuyuanweizhi == null ? "" : qiuyuanweizhi.trim();
    }

    public String getQiuyihao() {
        return qiuyihao;
    }

    public void setQiuyihao(String qiuyihao) {
        this.qiuyihao = qiuyihao == null ? "" : qiuyihao.trim();
    }

    public String getGerenjianjie() {
        return gerenjianjie;
    }

    public void setGerenjianjie(String gerenjianjie) {
        this.gerenjianjie = gerenjianjie == null ? "" : gerenjianjie.trim();
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang == null ? "" : touxiang.trim();
    }
}

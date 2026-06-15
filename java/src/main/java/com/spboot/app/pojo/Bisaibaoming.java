package com.spboot.app.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;
import java.util.*;

/**
 * 比赛报名 表 bisaibaoming.
 */
@TableName("bisaibaoming")
public class Bisaibaoming implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 比赛信息id.
    private Integer bisaixinxiid;
    // 比赛编号.
    private String bisaibianhao;
    // 比赛标题.
    private String bisaibiaoti;
    // 比赛类型.
    private Integer bisaileixing;
    // 报名编号.
    private String baomingbianhao;
    // 报名球队.
    private Integer baomingqiudui;
    // 球队编号.
    private String qiuduibianhao;
    // 球队名称.
    private String qiuduimingcheng;
    // 球队队员.
    private String qiuduiduiyuan;
    // 教练姓名.
    private String jiaolianxingming;
    // 球队教练.
    private String qiuduijiaolian;
    // 联系方式.
    private String lianxifangshi;
    // 备注.
    private String beizhu;
    // 状态.
    // 可选项:已报名,已编排。
    private String zhuangtai;
    // 添加时间.
    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBisaixinxiid() {
        return bisaixinxiid;
    }

    public void setBisaixinxiid(Integer bisaixinxiid) {
        this.bisaixinxiid = bisaixinxiid == null ? 0 : bisaixinxiid;
    }

    public String getBisaibianhao() {
        return bisaibianhao;
    }

    public void setBisaibianhao(String bisaibianhao) {
        this.bisaibianhao = bisaibianhao == null ? "" : bisaibianhao.trim();
    }

    public String getBisaibiaoti() {
        return bisaibiaoti;
    }

    public void setBisaibiaoti(String bisaibiaoti) {
        this.bisaibiaoti = bisaibiaoti == null ? "" : bisaibiaoti.trim();
    }

    public Integer getBisaileixing() {
        return bisaileixing;
    }

    public void setBisaileixing(Integer bisaileixing) {
        this.bisaileixing = bisaileixing == null ? 0 : bisaileixing;
    }

    public String getBaomingbianhao() {
        return baomingbianhao;
    }

    public void setBaomingbianhao(String baomingbianhao) {
        this.baomingbianhao = baomingbianhao == null ? "" : baomingbianhao.trim();
    }

    public Integer getBaomingqiudui() {
        return baomingqiudui;
    }

    public void setBaomingqiudui(Integer baomingqiudui) {
        this.baomingqiudui = baomingqiudui == null ? 0 : baomingqiudui;
    }

    public String getQiuduibianhao() {
        return qiuduibianhao;
    }

    public void setQiuduibianhao(String qiuduibianhao) {
        this.qiuduibianhao = qiuduibianhao == null ? "" : qiuduibianhao.trim();
    }

    public String getQiuduimingcheng() {
        return qiuduimingcheng;
    }

    public void setQiuduimingcheng(String qiuduimingcheng) {
        this.qiuduimingcheng = qiuduimingcheng == null ? "" : qiuduimingcheng.trim();
    }

    public String getQiuduiduiyuan() {
        return qiuduiduiyuan;
    }

    public void setQiuduiduiyuan(String qiuduiduiyuan) {
        this.qiuduiduiyuan = qiuduiduiyuan == null ? "" : qiuduiduiyuan.trim();
    }

    public String getJiaolianxingming() {
        return jiaolianxingming;
    }

    public void setJiaolianxingming(String jiaolianxingming) {
        this.jiaolianxingming = jiaolianxingming == null ? "" : jiaolianxingming.trim();
    }

    public String getQiuduijiaolian() {
        return qiuduijiaolian;
    }

    public void setQiuduijiaolian(String qiuduijiaolian) {
        this.qiuduijiaolian = qiuduijiaolian == null ? "" : qiuduijiaolian.trim();
    }

    public String getLianxifangshi() {
        return lianxifangshi;
    }

    public void setLianxifangshi(String lianxifangshi) {
        this.lianxifangshi = lianxifangshi == null ? "" : lianxifangshi.trim();
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu == null ? "" : beizhu.trim();
    }

    public String getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai(String zhuangtai) {
        this.zhuangtai = zhuangtai == null ? "" : zhuangtai.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? "" : addtime.trim();
    }
}

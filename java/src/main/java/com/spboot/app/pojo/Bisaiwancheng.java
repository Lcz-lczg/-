package com.spboot.app.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;
import java.util.*;

/**
 * 比赛完成 表 bisaiwancheng.
 */
@TableName("bisaiwancheng")
public class Bisaiwancheng implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 赛程表id.
    private Integer saichengbiaoid;
    // 比赛编号.
    private String bisaibianhao;
    // 比赛标题.
    private String bisaibiaoti;
    // 比赛类型.
    private Integer bisaileixing;
    // 球队A.
    private Integer qiuduia;
    // 球队B.
    private Integer qiuduib;
    // 完成编号.
    private String wanchengbianhao;
    // 胜利球队.
    private Integer shengliqiudui;
    // 完成内容.
    private String wanchengneirong;
    // 添加时间.
    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSaichengbiaoid() {
        return saichengbiaoid;
    }

    public void setSaichengbiaoid(Integer saichengbiaoid) {
        this.saichengbiaoid = saichengbiaoid == null ? 0 : saichengbiaoid;
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

    public Integer getQiuduia() {
        return qiuduia;
    }

    public void setQiuduia(Integer qiuduia) {
        this.qiuduia = qiuduia == null ? 0 : qiuduia;
    }

    public Integer getQiuduib() {
        return qiuduib;
    }

    public void setQiuduib(Integer qiuduib) {
        this.qiuduib = qiuduib == null ? 0 : qiuduib;
    }

    public String getWanchengbianhao() {
        return wanchengbianhao;
    }

    public void setWanchengbianhao(String wanchengbianhao) {
        this.wanchengbianhao = wanchengbianhao == null ? "" : wanchengbianhao.trim();
    }

    public Integer getShengliqiudui() {
        return shengliqiudui;
    }

    public void setShengliqiudui(Integer shengliqiudui) {
        this.shengliqiudui = shengliqiudui == null ? 0 : shengliqiudui;
    }

    public String getWanchengneirong() {
        return wanchengneirong;
    }

    public void setWanchengneirong(String wanchengneirong) {
        this.wanchengneirong = wanchengneirong == null ? "" : wanchengneirong.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? "" : addtime.trim();
    }
}

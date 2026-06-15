package com.spboot.app.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;
import java.util.*;

/**
 * 比赛信息 表 bisaixinxi.
 */
@TableName("bisaixinxi")
public class Bisaixinxi implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 比赛编号.
    private String bisaibianhao;
    // 比赛标题.
    private String bisaibiaoti;
    // 比赛类型.
    private Integer bisaileixing;
    // 比赛模式.
    private String bisaimoshi;
    // 比赛图片.
    private String bisaitupian;
    // 参加球队.
    private Integer canjiaqiudui;
    // 比赛场地.
    private String bisaichangdi;
    // 比赛内容.
    private String bisaineirong;
    // 添加人.
    private String tianjiaren;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBisaibaomingCount() {
        return DB.name("bisaibaoming").where("bisaixinxiid", id).count();
    }

    public Long getSaichengbiaoCount() {
        return DB.name("saichengbiao").where("bisaixinxiid", id).count();
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

    public String getBisaimoshi() {
        return bisaimoshi;
    }

    public void setBisaimoshi(String bisaimoshi) {
        this.bisaimoshi = bisaimoshi == null ? "" : bisaimoshi.trim();
    }

    public String getBisaitupian() {
        return bisaitupian;
    }

    public void setBisaitupian(String bisaitupian) {
        this.bisaitupian = bisaitupian == null ? "" : bisaitupian.trim();
    }

    public Integer getCanjiaqiudui() {
        return canjiaqiudui;
    }

    public void setCanjiaqiudui(Integer canjiaqiudui) {
        this.canjiaqiudui = canjiaqiudui == null ? 0 : canjiaqiudui;
    }

    public String getBisaichangdi() {
        return bisaichangdi;
    }

    public void setBisaichangdi(String bisaichangdi) {
        this.bisaichangdi = bisaichangdi == null ? "" : bisaichangdi.trim();
    }

    public String getBisaineirong() {
        return bisaineirong;
    }

    public void setBisaineirong(String bisaineirong) {
        this.bisaineirong = bisaineirong == null ? "" : bisaineirong.trim();
    }

    public String getTianjiaren() {
        return tianjiaren;
    }

    public void setTianjiaren(String tianjiaren) {
        this.tianjiaren = tianjiaren == null ? "" : tianjiaren.trim();
    }
}

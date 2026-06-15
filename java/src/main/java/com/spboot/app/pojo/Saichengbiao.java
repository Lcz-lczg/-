package com.spboot.app.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;
import java.util.*;

/**
 * 赛程表 表 saichengbiao.
 */
@TableName("saichengbiao")
public class Saichengbiao implements Serializable {

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
    // 球队A.
    private Integer qiuduia;
    // VS.
    private String vs;
    // 球队B.
    private Integer qiuduib;
    // 比分.
    private String bifen;
    // 状态.
    // 可选项:待开始,进行中,已结束。
    private String zhuangtai;
    // 比赛内容.
    private String bisaineirong;
    // 添加时间.
    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public Long getBisaiwanchengCount() {
        return DB.name("bisaiwancheng").where("saichengbiaoid", id).count();
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

    public Integer getQiuduia() {
        return qiuduia;
    }

    public void setQiuduia(Integer qiuduia) {
        this.qiuduia = qiuduia == null ? 0 : qiuduia;
    }

    public String getVs() {
        return vs;
    }

    public void setVs(String vs) {
        this.vs = vs == null ? "" : vs.trim();
    }

    public Integer getQiuduib() {
        return qiuduib;
    }

    public void setQiuduib(Integer qiuduib) {
        this.qiuduib = qiuduib == null ? 0 : qiuduib;
    }

    public String getBifen() {
        return bifen;
    }

    public void setBifen(String bifen) {
        this.bifen = bifen == null ? "" : bifen.trim();
    }

    public String getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai(String zhuangtai) {
        this.zhuangtai = zhuangtai == null ? "" : zhuangtai.trim();
    }

    public String getBisaineirong() {
        return bisaineirong;
    }

    public void setBisaineirong(String bisaineirong) {
        this.bisaineirong = bisaineirong == null ? "" : bisaineirong.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? "" : addtime.trim();
    }
}

package com.spboot.app.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;
import java.util.*;

/**
 * 球队 表 qiudui.
 */
@TableName("qiudui")
public class Qiudui implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 球队编号.
    private String qiuduibianhao;
    // 球队名称.
    private String qiuduimingcheng;
    // 球队图片.
    private String qiuduitupian;
    // 球队队员.
    private String qiuduiduiyuan;
    // 实力等级.
    private Integer shilidengji;
    // 球队积分.
    private Double qiuduijifen;
    // 球队详情.
    private String qiuduixiangqing;
    // 教练姓名.
    private String jiaolianxingming;
    // 球队教练.
    private String qiuduijiaolian;
    // 成立时间.
    private String chenglishijian;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getQiuduitupian() {
        return qiuduitupian;
    }

    public void setQiuduitupian(String qiuduitupian) {
        this.qiuduitupian = qiuduitupian == null ? "" : qiuduitupian.trim();
    }

    public String getQiuduiduiyuan() {
        return qiuduiduiyuan;
    }

    public void setQiuduiduiyuan(String qiuduiduiyuan) {
        this.qiuduiduiyuan = qiuduiduiyuan == null ? "" : qiuduiduiyuan.trim();
    }

    public Integer getShilidengji() {
        return shilidengji;
    }

    public void setShilidengji(Integer shilidengji) {
        this.shilidengji = shilidengji == null ? 0 : shilidengji;
    }

    public Double getQiuduijifen() {
        return qiuduijifen;
    }

    public void setQiuduijifen(Double qiuduijifen) {
        this.qiuduijifen = qiuduijifen == null ? 0.0f : qiuduijifen;
    }

    public String getQiuduixiangqing() {
        return qiuduixiangqing;
    }

    public void setQiuduixiangqing(String qiuduixiangqing) {
        this.qiuduixiangqing = qiuduixiangqing == null ? "" : qiuduixiangqing.trim();
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

    public String getChenglishijian() {
        return chenglishijian;
    }

    public void setChenglishijian(String chenglishijian) {
        this.chenglishijian = chenglishijian == null ? "" : chenglishijian.trim();
    }
}

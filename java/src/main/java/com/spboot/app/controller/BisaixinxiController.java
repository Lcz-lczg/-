package com.spboot.app.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.jntoo.db.DB;
import com.jntoo.db.utils.StringUtil;
import com.spboot.app.config.Configure;
import com.spboot.app.mapper.BisaixinxiMapper;
import com.spboot.app.pojo.Bisaixinxi;
import com.spboot.app.service.BisaixinxiService;
import com.spboot.app.utils.R;
import com.spboot.app.utils.SessionFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "比赛信息控制器" })
@RestController
@RequestMapping("/api/bisaixinxi")
public class BisaixinxiController {

    @Autowired
    public BisaixinxiService bisaixinxiService;

    @ApiOperation(value = "获取全部比赛信息", httpMethod = "GET")
    @RequestMapping("/selectAll")
    public R<List<Bisaixinxi>> selectAll() {
        return bisaixinxiService.selectAll();
    }

    @ApiOperation(value = "根据条件筛选获取管理员列表，并分页", httpMethod = "POST")
    @RequestMapping("/selectPages")
    public R selectPages(@RequestBody Map<String, Object> req) {
        return bisaixinxiService.selectPages(req);
    }

    @ApiOperation(value = "根据条件筛选获取添加人字段值为当前用户列表并分页", httpMethod = "POST")
    @RequestMapping("/selectTianjiaren")
    public R selectTianjiaren(@RequestBody Map<String, Object> req) {
        return bisaixinxiService.selectPagesTianjiaren(req);
    }

    @ApiOperation(value = "根据id获取信息", httpMethod = "GET")
    @RequestMapping("/findById")
    @ApiImplicitParam(name = "id", value = "比赛信息对应的id", dataType = "Integer")
    public R findById(@RequestParam Integer id) {
        return bisaixinxiService.findById(id);
    }

    @ApiOperation(value = "根据id更新数据", httpMethod = "POST")
    @RequestMapping("/update")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Bisaixinxi.class, paramType = "body")
    public R update(@RequestBody Map data) {
        Bisaixinxi post = BeanUtil.mapToBean(data, Bisaixinxi.class, true);
        return bisaixinxiService.update(post, data);
    }

    @ApiOperation(value = "插入一行数据，返回插入后的数据", httpMethod = "POST")
    @RequestMapping("/insert")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Bisaixinxi.class, paramType = "body")
    public R insert(@RequestBody Map data) {
        Bisaixinxi post = BeanUtil.mapToBean(data, Bisaixinxi.class, true);
        return bisaixinxiService.insert(post, data);
    }

    @ApiOperation(value = "根据id列表删除数据", httpMethod = "POST")
    @RequestMapping("/delete")
    @ApiImplicitParam(name = "id", value = "比赛信息对应的id", type = "json", dataTypeClass = List.class)
    public R delete(@RequestBody List<Integer> id) {
        return bisaixinxiService.delete(id);
    }

    @ApiOperation(value = "获取点赞", httpMethod = "GET")
    @GetMapping("/getdianzan")
    @ApiImplicitParam(name = "id", value = "比赛信息对应的id", type = "Integer", dataTypeClass = Integer.class)
    public R getdianzan(@RequestParam("id") Integer id) {
        long dianzanCount = DB.name("dianzan").where("biao", "bisaixinxi").where("biaoid", id).count();

        long is_dianzan = DB.name("dianzan").where("biao", "bisaixinxi").where("biaoid", id).where("username", SessionFactory.getUsername()).count();

        return R.success(R.result().set("dianzanCount", dianzanCount).set("is_dianzan", is_dianzan));
    }
}

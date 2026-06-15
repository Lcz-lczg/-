package com.spboot.app.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.jntoo.db.DB;
import com.jntoo.db.utils.StringUtil;
import com.spboot.app.config.Configure;
import com.spboot.app.mapper.XinwengonggaoMapper;
import com.spboot.app.pojo.Xinwengonggao;
import com.spboot.app.service.XinwengonggaoService;
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

@Api(tags = { "新闻公告控制器" })
@RestController
@RequestMapping("/api/xinwengonggao")
public class XinwengonggaoController {

    @Autowired
    public XinwengonggaoService xinwengonggaoService;

    @ApiOperation(value = "获取全部新闻公告", httpMethod = "GET")
    @RequestMapping("/selectAll")
    public R<List<Xinwengonggao>> selectAll() {
        return xinwengonggaoService.selectAll();
    }

    @ApiOperation(value = "根据条件筛选获取管理员列表，并分页", httpMethod = "POST")
    @RequestMapping("/selectPages")
    public R selectPages(@RequestBody Map<String, Object> req) {
        return xinwengonggaoService.selectPages(req);
    }

    @ApiOperation(value = "根据条件筛选获取添加人字段值为当前用户列表并分页", httpMethod = "POST")
    @RequestMapping("/selectTianjiaren")
    public R selectTianjiaren(@RequestBody Map<String, Object> req) {
        return xinwengonggaoService.selectPagesTianjiaren(req);
    }

    @ApiOperation(value = "根据id获取信息", httpMethod = "GET")
    @RequestMapping("/findById")
    @ApiImplicitParam(name = "id", value = "新闻公告对应的id", dataType = "Integer")
    public R findById(@RequestParam Integer id) {
        return xinwengonggaoService.findById(id);
    }

    @ApiOperation(value = "根据id更新数据", httpMethod = "POST")
    @RequestMapping("/update")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Xinwengonggao.class, paramType = "body")
    public R update(@RequestBody Map data) {
        Xinwengonggao post = BeanUtil.mapToBean(data, Xinwengonggao.class, true);
        return xinwengonggaoService.update(post, data);
    }

    @ApiOperation(value = "插入一行数据，返回插入后的数据", httpMethod = "POST")
    @RequestMapping("/insert")
    @ApiImplicitParam(name = "data", value = "使用json数据提交", type = "json", dataTypeClass = Xinwengonggao.class, paramType = "body")
    public R insert(@RequestBody Map data) {
        Xinwengonggao post = BeanUtil.mapToBean(data, Xinwengonggao.class, true);
        return xinwengonggaoService.insert(post, data);
    }

    @ApiOperation(value = "根据id列表删除数据", httpMethod = "POST")
    @RequestMapping("/delete")
    @ApiImplicitParam(name = "id", value = "新闻公告对应的id", type = "json", dataTypeClass = List.class)
    public R delete(@RequestBody List<Integer> id) {
        return xinwengonggaoService.delete(id);
    }

    @ApiOperation(value = "获取点赞", httpMethod = "GET")
    @GetMapping("/getdianzan")
    @ApiImplicitParam(name = "id", value = "新闻公告对应的id", type = "Integer", dataTypeClass = Integer.class)
    public R getdianzan(@RequestParam("id") Integer id) {
        long dianzanCount = DB.name("dianzan").where("biao", "xinwengonggao").where("biaoid", id).count();

        long is_dianzan = DB.name("dianzan").where("biao", "xinwengonggao").where("biaoid", id).where("username", SessionFactory.getUsername()).count();

        return R.success(R.result().set("dianzanCount", dianzanCount).set("is_dianzan", is_dianzan));
    }

    @ApiOperation(value = "前端详情触发后", httpMethod = "POST")
    @RequestMapping("/detailWeb")
    public R detailWeb(@RequestBody Map<String, Object> post) {
        Xinwengonggao map = DB.name(Xinwengonggao.class).find(post.get("id"));
        DB.execute("UPDATE xinwengonggao SET dianjiliang = dianjiliang+1 WHERE id=?", post.get("id"));

        return R.ok();
    }
}

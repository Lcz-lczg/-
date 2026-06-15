package com.spboot.app.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jntoo.db.DB;
import com.jntoo.db.utils.Convert;
import com.jntoo.db.utils.StringUtil;
import com.spboot.app.mapper.BisaiwanchengMapper;
import com.spboot.app.pojo.Bisaiwancheng;
import com.spboot.app.utils.*;
import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BisaiwanchengService {

    // 获取数据库操作类mapper
    @Resource
    private BisaiwanchengMapper mapper;

    /**
     *  根据id 获取一行数据
     */
    public R<Bisaiwancheng> findById(Integer id) {
        return R.success(mapper.selectById(id));
    }

    /**
     *  根据Wrapper 对象进行数据筛选
     */
    public R<List<Bisaiwancheng>> selectAll(Wrapper<Bisaiwancheng> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     *  直接筛选所有数据
     */
    public R<List<Bisaiwancheng>> selectAll() {
        QueryWrapper<Bisaiwancheng> wrapper = Wrappers.query();
        wrapper.orderByDesc("id");
        return selectAll(wrapper);
    }

    /**
     *  根据map 条件筛选数据
     *
     */
    public R selectAll(Map<String, Object> map) {
        // 获取筛选数据
        SelectPage selectPage = new SelectPage(map, 10, "id", "DESC");
        // 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象，执行动态查询
        QueryWrapper<Bisaiwancheng> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        return selectAll(wrapper);
    }

    /**
     *  根据map 条件筛选数据并分页
     *
     */
    public R selectPages(Map<String, Object> map) {
        // 获取筛选数据
        SelectPage selectPage = new SelectPage(map, 10, "id", "DESC");
        // 将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象，执行动态查询
        QueryWrapper<Bisaiwancheng> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     *   将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Bisaiwancheng> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Bisaiwancheng> wrapper = Wrappers.query();

        String where = " 1=1 ";
        // 以下是判断搜索框中是否有输入内容，判断是否前台是否有填写相关条件，符合则写入sql搜索语句

        // 判断URL 参数saichengbiaoid是否大于0
        if (!StringUtil.isNullOrEmpty(map.get("saichengbiaoid")) && Convert.toInt(map.get("saichengbiaoid")) > 0) {
            // 大于0 则写入条件
            wrapper.eq("saichengbiaoid", map.get("saichengbiaoid"));
        }

        if (!StringUtil.isNullOrEmpty(map.get("bisaibianhao"))) {
            wrapper.like("bisaibianhao", map.get("bisaibianhao"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("bisaibiaoti"))) {
            wrapper.like("bisaibiaoti", map.get("bisaibiaoti"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("wanchengbianhao"))) {
            wrapper.like("wanchengbianhao", map.get("wanchengbianhao"));
        }

        if (map.containsKey("session_name")) {
            wrapper.eq(map.get("session_name").toString(), SessionFactory.getUsername());
        }

        wrapper.apply(where);
        return wrapper;
    }

    public R selectPages(QueryWrapper<Bisaiwancheng> wrapper, IPage page) {
        Map result = new HashMap();
        result.put("lists", mapper.selectPage(page, wrapper));

        return R.success(result);
    }

    /**
     * 插入用户数据
     * @param entityData 插入的对象
     * @param post 提交的数据
     * @return 是否处理成功
     */
    public R insert(Bisaiwancheng entityData, Map post) {
        // 判断是否有填写完成编号。
        if (StringUtil.isNullOrEmpty(post.get("wanchengbianhao"))) {
            return R.error("请填写完成编号");
        }
        // 判断是否有填写胜利球队。
        if (StringUtil.isNullOrEmpty(post.get("shengliqiudui"))) {
            return R.error("请填写胜利球队");
        }

        Info.handlerNullEntity(entityData);
        entityData.setAddtime(Info.getDateStr());

        entityData.setId(null);
        mapper.insert(entityData);
        if (entityData.getId() != null) {
            Integer charuid = entityData.getId();
            DB.execute("UPDATE qiudui SET qiuduijifen = qiuduijifen+ 10 WHERE id = '" + post.get("shengliqiudui") + "'");

            DB.execute("UPDATE saichengbiao SET zhuangtai = '已完成' WHERE id='" + post.get("saichengbiaoid") + "'");

            return findById(entityData.getId());
        } else {
            return R.error("插入错误");
        }
    }

    /**
     * 根据id进行更新比赛完成数据
     * @param entityData 更新的数据
     * @param post 提交的数据
     * @return 是否处理成功
     */
    public R<Object> update(Bisaiwancheng entityData, Map post) {
        // 判断是否有填写完成编号。
        if (StringUtil.isNullOrEmpty(post.get("wanchengbianhao"))) {
            return R.error("请填写完成编号");
        }
        // 判断是否有填写胜利球队。
        if (StringUtil.isNullOrEmpty(post.get("shengliqiudui"))) {
            return R.error("请填写胜利球队");
        }

        mapper.updateById(entityData);

        return R.success(mapper.selectById(entityData.getId()));
    }

    /**
     * 根据 id列表 删除
     * @param ids  id 列表值
     * @return 是否成功
     */
    public R<Object> delete(List<Integer> ids) {
        try {
            for (Integer id : ids) {
                delete(id);
            }
            return R.success("操作成功");
        } catch (Exception e) {
            return R.error("操作失败");
        }
    }

    /**
     * 根据 id 删除
     * @param id  id 列表值
     * @return 是否成功
     */
    public R<Object> delete(Integer id) {
        try {
            mapper.deleteById(id);

            return R.success("操作成功");
        } catch (Exception e) {
            return R.error("操作失败");
        }
    }

    /**
     *  根据map 条件进行数据导出
     *
     */
    public void export(Map<String, Object> map, OutputStream outputStream) {
        String orderby = (String) map.get("orderby");
        String sort = (String) map.get("sort");
        Execl xls = new Execl(); // 生成导出类

        xls.addCol("bisaibianhao", "比赛编号"); // 添加一列 bisaibianhao，比赛编号
        xls.addCol("bisaibiaoti", "比赛标题"); // 添加一列 bisaibiaoti，比赛标题
        xls.addColSelect("bisaileixing", "比赛类型", "bisaileixing", "id", "leixingmingcheng", 0); //添加一个下拉框的行，表字段名，名称，下拉框表，值，名称、是否多选

        xls.addColSelect("qiuduia", "球队A", "qiudui", "id", "qiuduimingcheng", 0); //添加一个下拉框的行，表字段名，名称，下拉框表，值，名称、是否多选

        xls.addColSelect("qiuduib", "球队B", "qiudui", "id", "qiuduimingcheng", 0); //添加一个下拉框的行，表字段名，名称，下拉框表，值，名称、是否多选

        xls.addCol("wanchengbianhao", "完成编号"); // 添加一列 wanchengbianhao，完成编号
        xls.addColSelect("shengliqiudui", "胜利球队", "qiudui", "id", "qiuduimingcheng", 0); //添加一个下拉框的行，表字段名，名称，下拉框表，值，名称、是否多选

        xls.addCol("wanchengneirong", "完成内容"); // 添加一列 wanchengneirong，完成内容
        xls.addCol("addtime", "添加时间"); // 添加一列 addtime，添加时间

        // 判断是否有排序选项
        if (StringUtil.isNullOrEmpty(orderby)) {
            orderby = "id";
        }
        if (StringUtil.isNullOrEmpty(sort)) {
            sort = "DESC";
        }
        sort = sort.toUpperCase();
        if (!sort.equals("DESC") && !sort.equals("ASC")) {
            sort = "DESC";
        }
        // 搜索数据库数据，并把数据导入到  execl 导入导出类
        List<Map> list = DBQueryWrapper.make("bisaiwancheng").plus(mapToWrapper(map)).order(orderby, sort).select();
        xls.addData(list);
        // 执行导出
        xls.export(outputStream);
    }
}

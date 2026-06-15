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
import com.spboot.app.mapper.QiuduiMapper;
import com.spboot.app.pojo.Qiudui;
import com.spboot.app.utils.*;
import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class QiuduiService {

    // 获取数据库操作类mapper
    @Resource
    private QiuduiMapper mapper;

    /**
     *   根据Qiuduimingcheng字段参数获取一行数据
     */
    public Qiudui findByQiuduimingcheng(String username) {
        // 新建球队模块实体类Qiudui
        Qiudui pojo = new Qiudui();
        // 设置参数
        pojo.setQiuduimingcheng(username);
        // 根据实体类新建QueryWrapper查询条件类
        QueryWrapper<Qiudui> queryWrapper = Wrappers.query(pojo);
        Qiudui row = mapper.selectOne(queryWrapper);
        return row;
    }

    /**
     *   根据Qiuduimingcheng字段参数获取一行数据，并不包含某uid 参数得行
     */
    public Qiudui findByQiuduimingcheng(String username, Integer uid) {
        // 新建球队模块实体类Qiudui
        Qiudui pojo = new Qiudui();
        // 设置参数
        pojo.setQiuduimingcheng(username);
        // 根据实体类新建QueryWrapper查询条件类
        QueryWrapper<Qiudui> queryWrapper = Wrappers.query(pojo);
        // 设置参数 id != uid变量
        queryWrapper.ne("id", uid);
        // 根据queryWrapper 查询
        Qiudui row = mapper.selectOne(queryWrapper);
        return row;
    }

    /**
     *  根据id 获取一行数据
     */
    public R<Qiudui> findById(Integer id) {
        try {
            Qiudui qiudui = mapper.selectById(id);
            if (qiudui != null) {
                // 查询该球队的球员 ID 列表
                List<Map> players = DB.name("qiuyuanxinxi")
                    .where("suoshuqiudui", qiudui.getQiuduimingcheng())
                    .select();
                StringBuilder sb = new StringBuilder();
                for (Map player : players) {
                    if (sb.length() > 0) sb.append(",");
                    sb.append(player.get("id"));
                }
                qiudui.setQiuduiduiyuan(sb.toString());
            }
            return R.success(qiudui);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("查询失败：" + e.getMessage());
        }
    }

    /**
     *  根据Wrapper 对象进行数据筛选
     */
    public R<List<Qiudui>> selectAll(Wrapper<Qiudui> query) {
        return R.success(mapper.selectList(query));
    }

    /**
     *  直接筛选所有数据
     */
    public R<List<Qiudui>> selectAll() {
        QueryWrapper<Qiudui> wrapper = Wrappers.query();
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
        QueryWrapper<Qiudui> wrapper = mapToWrapper(map);
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
        QueryWrapper<Qiudui> wrapper = mapToWrapper(map);
        // 设置排序
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        // 设置分页数据
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     *  根据map 条件筛选qiuduijiaolian字段等于session.username的数据并分页
     *
     */
    public R selectPagesQiuduijiaolian(Map<String, Object> map) {
        // 获取前端给到列表基础参数
        SelectPage selectPage = new SelectPage(map, 10, "id", "DESC");
        // 将map参数转换为mybatis-plus的QueryWrapper类
        QueryWrapper<Qiudui> wrapper = mapToWrapper(map);
        // 设置为当前角色
        wrapper.eq("qiuduijiaolian", SessionFactory.getUsername());
        wrapper.orderBy(true, selectPage.isAsc(), selectPage.getOrderby());
        Page page = new Page(selectPage.getPage(), selectPage.getPagesize());
        return selectPages(wrapper, page);
    }

    /**
     *   将提交的参数转换成 mybatisplus 的QueryWrapper 筛选数据对象
     */
    public QueryWrapper<Qiudui> mapToWrapper(Map<String, Object> map) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Qiudui> wrapper = Wrappers.query();

        String where = " 1=1 ";
        // 以下是判断搜索框中是否有输入内容，判断是否前台是否有填写相关条件，符合则写入sql搜索语句

        if (!StringUtil.isNullOrEmpty(map.get("qiuduibianhao"))) {
            wrapper.like("qiuduibianhao", map.get("qiuduibianhao"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("qiuduimingcheng"))) {
            wrapper.like("qiuduimingcheng", map.get("qiuduimingcheng"));
        }
        if (!StringUtil.isNullOrEmpty(map.get("qiuduiduiyuan"))) {
            wrapper.like("qiuduiduiyuan", map.get("qiuduiduiyuan"));
        }

        if (map.containsKey("session_name")) {
            wrapper.eq(map.get("session_name").toString(), SessionFactory.getUsername());
        }

        wrapper.apply(where);
        return wrapper;
    }

    public R selectPages(QueryWrapper<Qiudui> wrapper, IPage page) {
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
    public R insert(Qiudui entityData, Map post) {
        // 判断是否有填写球队编号。
        if (StringUtil.isNullOrEmpty(post.get("qiuduibianhao"))) {
            return R.error("请填写球队编号");
        }
        // 判断是否有填写球队名称。
        if (StringUtil.isNullOrEmpty(post.get("qiuduimingcheng"))) {
            return R.error("请填写球队名称");
        }
        // 判断是否有填写球队名称,有则判断是否在数据中已经存在，存在则报错。
        if (findByQiuduimingcheng(entityData.getQiuduimingcheng()) != null) {
            return R.error("球队名称已经存在");
        }
        // 判断是否有填写球队图片。
        if (StringUtil.isNullOrEmpty(post.get("qiuduitupian"))) {
            return R.error("请填写球队图片");
        }

        Info.handlerNullEntity(entityData);

        entityData.setId(null);
        mapper.insert(entityData);
        if (entityData.getId() != null) {
            // 更新球员的所属球队为当前球队名称
            String qiuduiduiyuan = (String) post.get("qiuduiduiyuan");
            if (qiuduiduiyuan != null && !qiuduiduiyuan.isEmpty()) {
                DB.execute("UPDATE qiuyuanxinxi SET suoshuqiudui = '" + entityData.getQiuduimingcheng() + "' WHERE id IN (" + qiuduiduiyuan + ")");
                
                // 更新球队的 qiuduiduiyuan 字段为球员姓名列表
                List<Map> players = DB.name("qiuyuanxinxi")
                    .where("id", "in", qiuduiduiyuan.split(","))
                    .select();
                List<String> nameList = new ArrayList<>();
                for (Map player : players) {
                    nameList.add((String) player.get("xingming"));
                }
                String playerNames = String.join("、", nameList);
                
                entityData.setQiuduiduiyuan(playerNames);
                mapper.updateById(entityData);
            }

            return findById(entityData.getId());
        } else {
            return R.error("插入错误");
        }
    }

    /**
     * 根据id进行更新球队数据
     * @param entityData 更新的数据
     * @param post 提交的数据
     * @return 是否处理成功
     */
    public R<Object> update(Qiudui entityData, Map post) {
        // 判断是否有填写球队名称。
        if (StringUtil.isNullOrEmpty(post.get("qiuduimingcheng"))) {
            return R.error("请填写球队名称");
        }
        // 判断是否有填写球队名称,有则判断是否在数据中已经存在，存在则报错。
        if (findByQiuduimingcheng(entityData.getQiuduimingcheng(), entityData.getId()) != null) {
            return R.error("球队名称已经存在");
        }
        // 判断是否有填写球队图片。
        if (StringUtil.isNullOrEmpty(post.get("qiuduitupian"))) {
            return R.error("请填写球队图片");
        }

        mapper.updateById(entityData);

        // 更新球员的所属球队
        String qiuduiduiyuan = (String) post.get("qiuduiduiyuan");

        // 只有当提交了球员数据时才更新
        if (qiuduiduiyuan != null) {
            // 先将该球队原有的球员所属球队设为"无"
            DB.execute("UPDATE qiuyuanxinxi SET suoshuqiudui = '无' WHERE suoshuqiudui = '" + entityData.getQiuduimingcheng() + "'");

            // 再将新选择的球员所属球队设为当前球队名称
            if (!qiuduiduiyuan.isEmpty()) {
                DB.execute("UPDATE qiuyuanxinxi SET suoshuqiudui = '" + entityData.getQiuduimingcheng() + "' WHERE id IN (" + qiuduiduiyuan + ")");

                // 更新球队的 qiuduiduiyuan 字段为球员姓名列表
                List<Map> players = DB.name("qiuyuanxinxi")
                    .where("id", "in", qiuduiduiyuan.split(","))
                    .select();
                List<String> nameList = new ArrayList<>();
                for (Map player : players) {
                    nameList.add((String) player.get("xingming"));
                }
                String playerNames = String.join("、", nameList);

                entityData.setQiuduiduiyuan(playerNames);
                mapper.updateById(entityData);
            } else {
                // 没有球员，清空 qiuduiduiyuan
                entityData.setQiuduiduiyuan("");
                mapper.updateById(entityData);
            }
        }

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
            // 先获取球队信息，得到球队名称
            Qiudui qiudui = mapper.selectById(id);
            if (qiudui != null) {
                // 将该球队的所有球员所属球队设为"无"
                DB.execute("UPDATE qiuyuanxinxi SET suoshuqiudui = '无' WHERE suoshuqiudui = '" + qiudui.getQiuduimingcheng() + "'");
            }
            
            mapper.deleteById(id);

            return R.success("操作成功");
        } catch (Exception e) {
            return R.error("操作失败：" + e.getMessage());
        }
    }
}

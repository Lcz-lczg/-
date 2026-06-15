package com.spboot.app.controller;

import com.jntoo.db.DB;
import com.spboot.app.pojo.Saichengbiao;
import com.spboot.app.service.ScheduleGeneratorService;
import com.spboot.app.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 赛程编排控制器
 * 提供自动编排赛程功能
 */
@RestController
@RequestMapping("/api/schedule")
@Api(tags = { "赛程编排控制器" })
public class ScheduleController {

    @Autowired
    private ScheduleGeneratorService scheduleGeneratorService;

    @ApiOperation(value = "获取可编排赛程的球队列表", httpMethod = "GET")
    @GetMapping("/availableTeams")
    public R<List<Map<String, Object>>> getAvailableTeams(@RequestParam(required = false) Integer bisaixinxiid) {
        List<Map<String, Object>> teams = new ArrayList<>();

        // 获取所有球队及其实力等级
        List<Map> allTeams = DB.name("qiudui").select();

        for (Map team : allTeams) {
            Map<String, Object> teamInfo = new HashMap<>();
            teamInfo.put("id", team.get("id"));
            teamInfo.put("qiuduimingcheng", team.get("qiuduimingcheng"));
            teamInfo.put("qiuduibianhao", team.get("qiuduibianhao"));
            teamInfo.put("shilidengji", team.get("shilidengji"));
            teamInfo.put("jiaolianxingming", team.get("jiaolianxingming"));

            // 如果指定了比赛信息，检查球队是否已报名
            if (bisaixinxiid != null) {
                long baomingCount = DB.name("bisaibaoming")
                    .where("bisaixinxiid", bisaixinxiid)
                    .where("baomingqiudui", team.get("id"))
                    .count();
                teamInfo.put("yibaoming", baomingCount > 0);
            }

            teams.add(teamInfo);
        }

        return R.success(teams);
    }

    @ApiOperation(value = "获取比赛信息详情", httpMethod = "GET")
    @GetMapping("/matchInfo")
    public R<Map<String, Object>> getMatchInfo(@RequestParam Integer id) {
        Map matchInfo = DB.name("bisaixinxi").where("id", id).find();

        if (matchInfo == null) {
            return R.error("未找到比赛信息");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("id", matchInfo.get("id"));
        result.put("bisaibianhao", matchInfo.get("bisaibianhao"));
        result.put("bisaibiaoti", matchInfo.get("bisaibiaoti"));
        result.put("bisaileixing", matchInfo.get("bisaileixing"));
        result.put("bisaimoshi", matchInfo.get("bisaimoshi"));
        result.put("canjiaqiudui", matchInfo.get("canjiaqiudui"));
        result.put("bisaichangdi", matchInfo.get("bisaichangdi"));

        // 获取比赛类型名称
        Map leixing = DB.name("bisaileixing").where("id", matchInfo.get("bisaileixing")).find();
        if (leixing != null) {
            result.put("leixingmingcheng", leixing.get("leixingmingcheng"));
        }

        // 获取已报名的球队
        List<Map> baomingList = DB.name("bisaibaoming")
            .where("bisaixinxiid", id)
            .select();

        List<Map<String, Object>> teamList = new ArrayList<>();
        for (Map baoming : baomingList) {
            Map team = DB.name("qiudui").where("id", baoming.get("baomingqiudui")).find();
            if (team != null) {
                Map<String, Object> teamInfo = new HashMap<>();
                teamInfo.put("id", team.get("id"));
                teamInfo.put("qiuduimingcheng", team.get("qiuduimingcheng"));
                teamInfo.put("shilidengji", team.get("shilidengji"));
                teamInfo.put("baomingid", baoming.get("id"));
                teamList.add(teamInfo);
            }
        }
        result.put("teams", teamList);

        return R.success(result);
    }

    @ApiOperation(value = "自动编排赛程", httpMethod = "POST")
    @PostMapping("/generate")
    public R<Map<String, Object>> generateSchedule(@RequestBody Map<String, Object> req) {
        try {
            // 类型转换，处理可能的 Long 类型
            Object bisaixinxiidObj = req.get("bisaixinxiid");
            Integer bisaixinxiid = bisaixinxiidObj instanceof Long ? ((Long) bisaixinxiidObj).intValue() : (Integer) bisaixinxiidObj;
            
            List<Integer> teamIds = convertToIntegerList(req.get("teamIds"));
            String mode = (String) req.get("mode");
            Boolean levelBased = (Boolean) req.get("levelBased");

            System.out.println("=== 赛程编排请求 ===");
            System.out.println("bisaixinxiid: " + bisaixinxiid);
            System.out.println("teamIds: " + teamIds);
            System.out.println("mode: " + mode);
            System.out.println("levelBased: " + levelBased);

            if (bisaixinxiid == null) {
                return R.error("比赛信息 ID 不能为空");
            }

            if (teamIds == null || teamIds.isEmpty()) {
                return R.error("请选择参赛球队");
            }

            if (mode == null) {
                mode = "single";
            }

            if (levelBased == null) {
                levelBased = false;
            }

            // 验证球队数量
            if (teamIds.size() < 2) {
                return R.error("至少需要 2 支球队才能编排赛程");
            }

            // 淘汰赛需要队伍数量是 2 的幂次
            if ("knockout".equals(mode)) {
                int size = teamIds.size();
                boolean isPowerOf2 = (size & (size - 1)) == 0;
                if (!isPowerOf2) {
                    return R.error("淘汰赛制需要参赛队伍数量为 2 的幂次（如 2、4、8、16），当前队伍数量：" + size);
                }
            }

            // 调用服务生成赛程
            R<List<Saichengbiao>> result = scheduleGeneratorService.generateSchedule(
                bisaixinxiid, teamIds, mode, levelBased
            );

            System.out.println("=== 赛程编排结果 ===");
            System.out.println("code: " + result.get("code"));
            System.out.println("msg: " + result.get("msg"));

            if ((Integer) result.get("code") != 0) {
                return R.error((String) result.get("msg"));
            }

            List<Saichengbiao> schedules = result.getData();

            // 获取比赛信息
            Map matchInfo = DB.name("bisaixinxi").where("id", bisaixinxiid).find();
            if (matchInfo != null) {
                Object bisaileixingObj = matchInfo.get("bisaileixing");
                Integer bisaileixing = bisaileixingObj instanceof Long ? ((Long) bisaileixingObj).intValue() : (Integer) bisaileixingObj;
                for (Saichengbiao schedule : schedules) {
                    schedule.setBisaileixing(bisaileixing);
                    schedule.setBisaibianhao((String) matchInfo.get("bisaibianhao"));
                    schedule.setBisaibiaoti((String) matchInfo.get("bisaibiaoti"));
                }
            }

            // 返回预览数据
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("count", schedules.size());
            responseData.put("schedules", schedules);

            return R.success(responseData);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("赛程生成失败：" + e.getMessage());
        }
    }

    @ApiOperation(value = "保存编排的赛程", httpMethod = "POST")
    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public R<Map<String, Object>> saveSchedule(@RequestBody List<Map<String, Object>> schedules) {
        if (schedules == null || schedules.isEmpty()) {
            return R.error("没有需要保存的赛程");
        }

        List<Integer> savedIds = new ArrayList<>();
        Integer bisaixinxiid = null;

        try {
            for (Map<String, Object> scheduleData : schedules) {
                Saichengbiao schedule = new Saichengbiao();

                // 类型转换处理 Long
                Object bisaixinxiidObj = scheduleData.get("bisaixinxiid");
                Integer bisaixinxiidVal = bisaixinxiidObj instanceof Long ? ((Long) bisaixinxiidObj).intValue() : (Integer) bisaixinxiidObj;
                schedule.setBisaixinxiid(bisaixinxiidVal);
                
                schedule.setBisaibianhao((String) scheduleData.get("bisaibianhao"));
                schedule.setBisaibiaoti((String) scheduleData.get("bisaibiaoti"));
                
                Object bisaileixingObj = scheduleData.get("bisaileixing");
                Integer bisaileixing = bisaileixingObj instanceof Long ? ((Long) bisaileixingObj).intValue() : (Integer) bisaileixingObj;
                schedule.setBisaileixing(bisaileixing);
                
                Object qiuduiaObj = scheduleData.get("qiuduia");
                Integer qiuduia = qiuduiaObj instanceof Long ? ((Long) qiuduiaObj).intValue() : (Integer) qiuduiaObj;
                schedule.setQiuduia(qiuduia);
                
                Object qiuduibObj = scheduleData.get("qiuduib");
                Integer qiuduib = qiuduibObj instanceof Long ? ((Long) qiuduibObj).intValue() : (Integer) qiuduibObj;
                schedule.setQiuduib(qiuduib);
                
                schedule.setVs("VS");
                schedule.setBifen("");
                schedule.setZhuangtai("待开始");
                schedule.setBisaineirong("");
                schedule.setAddtime(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date()));

                // 插入数据库
                com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Saichengbiao> wrapper =
                    new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
                com.spboot.app.mapper.SaichengbiaoMapper mapper =
                    com.spboot.app.utils.SpringUtil.getBean(com.spboot.app.mapper.SaichengbiaoMapper.class);
                mapper.insert(schedule);

                savedIds.add(schedule.getId());

                if (bisaixinxiid == null) {
                    bisaixinxiid = schedule.getBisaixinxiid();
                }
            }

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("count", savedIds.size());
            responseData.put("ids", savedIds);
            responseData.put("bisaixinxiid", bisaixinxiid);

            return R.success(responseData);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存失败：" + e.getMessage());
        }
    }

    private List<Integer> convertToIntegerList(Object raw) {
        if (raw == null) return new ArrayList<>();
        if (raw instanceof List) {
            return ((List<?>) raw).stream()
                .map(e -> e instanceof Long ? ((Long) e).intValue() : (Integer) e)
                .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @ApiOperation(value = "清除已有赛程", httpMethod = "POST")
    @PostMapping("/clear")
    public R<Object> clearSchedule(@RequestParam Integer bisaixinxiid) {
        if (bisaixinxiid == null) {
            return R.error("比赛信息 ID 不能为空");
        }

        try {
            DB.name("saichengbiao").where("bisaixinxiid", bisaixinxiid).delete();

            return R.success("清除成功");
        } catch (Exception e) {
            return R.error("清除失败：" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取赛程编排预览", httpMethod = "GET")
    @GetMapping("/preview")
    public R<Map<String, Object>> getSchedulePreview(@RequestParam Integer bisaixinxiid) {
        if (bisaixinxiid == null) {
            return R.error("比赛信息 ID 不能为空");
        }

        List<Map> schedules = DB.name("saichengbiao")
            .where("bisaixinxiid", bisaixinxiid)
            .order("id", "ASC")
            .select();

        // 获取球队信息
        for (Map schedule : schedules) {
            Integer qiuduia = (Integer) schedule.get("qiuduia");
            Integer qiuduib = (Integer) schedule.get("qiuduib");

            if (qiuduia != null && qiuduia > 0) {
                Map teamA = DB.name("qiudui").where("id", qiuduia).find();
                if (teamA != null) {
                    schedule.put("qiuduia_name", teamA.get("qiuduimingcheng"));
                    schedule.put("qiuduia_level", teamA.get("shilidengji"));
                }
            }

            if (qiuduib != null && qiuduib > 0) {
                Map teamB = DB.name("qiudui").where("id", qiuduib).find();
                if (teamB != null) {
                    schedule.put("qiuduib_name", teamB.get("qiuduimingcheng"));
                    schedule.put("qiuduib_level", teamB.get("shilidengji"));
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("count", schedules.size());
        result.put("schedules", schedules);

        return R.success(result);
    }
}

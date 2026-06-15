package com.spboot.app.service;

import cn.hutool.core.util.RandomUtil;
import com.jntoo.db.DB;
import com.spboot.app.pojo.Saichengbiao;
import com.spboot.app.utils.R;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 赛程编排服务类
 * 支持单循环、双循环、淘汰制等比赛模式的赛程自动生成
 */
@Service
public class ScheduleGeneratorService {

    /**
     * 生成赛程
     * @param bisaixinxiid 比赛信息 ID
     * @param teamIds 参赛球队 ID 列表
     * @param mode 比赛模式：single(单循环)、double(双循环)、knockout(淘汰制)
     * @param levelBased 是否按实力水平编排（true: 避免强队过早相遇）
     * @return 生成的赛程列表
     */
    public R<List<Saichengbiao>> generateSchedule(Integer bisaixinxiid, List<Integer> teamIds, 
                                                   String mode, boolean levelBased) {
        if (teamIds == null || teamIds.size() < 2) {
            return R.error("参赛球队数量不足，至少需要 2 支球队");
        }

        // 获取球队实力等级
        Map<Integer, Integer> teamLevels = new HashMap<>();
        for (Integer teamId : teamIds) {
            Map teamInfo = DB.name("qiudui").where("id", teamId).find();
            if (teamInfo != null) {
                Object levelObj = teamInfo.get("shilidengji");
                Integer level = 0;
                if (levelObj instanceof Long) {
                    level = ((Long) levelObj).intValue();
                } else if (levelObj instanceof Integer) {
                    level = (Integer) levelObj;
                }
                teamLevels.put(teamId, level);
            } else {
                teamLevels.put(teamId, 0);
            }
        }

        List<Saichengbiao> schedules = new ArrayList<>();
        
        switch (mode.toLowerCase()) {
            case "single":
                schedules = generateSingleRoundRobin(teamIds, teamLevels, levelBased);
                break;
            case "double":
                schedules = generateDoubleRoundRobin(teamIds, teamLevels, levelBased);
                break;
            case "knockout":
                schedules = generateKnockoutSchedule(teamIds, teamLevels, levelBased);
                break;
            default:
                return R.error("不支持的比赛模式：" + mode);
        }

        // 设置比赛信息 ID 和默认值
        for (Saichengbiao schedule : schedules) {
            schedule.setBisaixinxiid(bisaixinxiid);
            schedule.setVs("VS");
            schedule.setBifen("");
            schedule.setZhuangtai("待开始");
            schedule.setAddtime(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }

        return R.success(schedules);
    }

    /**
     * 单循环赛制编排
     * 每支球队与其他球队各比赛一次
     * 使用贝格尔编排法（Berger pairing system）
     */
    private List<Saichengbiao> generateSingleRoundRobin(List<Integer> teamIds, 
                                                         Map<Integer, Integer> teamLevels, 
                                                         boolean levelBased) {
        List<Saichengbiao> schedules = new ArrayList<>();
        int n = teamIds.size();
        
        // 如果队伍数量是奇数，添加一个虚拟队伍（轮空）
        List<Integer> teams = new ArrayList<>(teamIds);
        if (n % 2 != 0) {
            teams.add(-1); // -1 表示轮空
            n++;
        }

        // 按实力排序（如果需要）
        if (levelBased) {
            teams.sort((a, b) -> {
                if (a == -1) return 1;
                if (b == -1) return -1;
                return teamLevels.getOrDefault(b, 0) - teamLevels.getOrDefault(a, 0);
            });
        } else {
            Collections.shuffle(teams); // 随机打乱
        }

        int rounds = n - 1; // 比赛轮数
        int matchesPerRound = n / 2; // 每轮比赛场次

        // 贝格尔编排法
        List<Integer> fixed = new ArrayList<>(teams);
        Integer first = fixed.remove(0); // 固定第一个位置

        for (int round = 0; round < rounds; round++) {
            // 构造当前轮的配对
            List<Integer> currentRound = new ArrayList<>();
            currentRound.add(first);
            currentRound.addAll(fixed);

            for (int i = 0; i < matchesPerRound; i++) {
                Integer teamA = currentRound.get(i);
                Integer teamB = currentRound.get(n - 1 - i);

                // 跳过轮空情况
                if (teamA == -1 || teamB == -1) {
                    continue;
                }

                Saichengbiao schedule = new Saichengbiao();
                schedule.setQiuduia(teamA);
                schedule.setQiuduib(teamB);
                schedule.setBisaibiaoti("第" + (round + 1) + "轮");
                schedule.setBisaileixing(1); // 默认 5 人制
                schedules.add(schedule);
            }

            // 旋转数组（固定第一个，其他顺时针旋转）
            if (!fixed.isEmpty()) {
                Integer last = fixed.remove(fixed.size() - 1);
                fixed.add(0, last);
            }
        }

        return schedules;
    }

    /**
     * 双循环赛制编排
     * 每支球队与其他球队各比赛两次（主客场）
     */
    private List<Saichengbiao> generateDoubleRoundRobin(List<Integer> teamIds, 
                                                         Map<Integer, Integer> teamLevels, 
                                                         boolean levelBased) {
        List<Saichengbiao> schedules = new ArrayList<>();
        
        // 先生成单循环
        List<Saichengbiao> singleSchedules = generateSingleRoundRobin(teamIds, teamLevels, levelBased);
        
        // 添加第二循环（主客场互换）
        for (Saichengbiao schedule : singleSchedules) {
            schedules.add(schedule);
            
            // 创建第二循环比赛（主客场互换）
            Saichengbiao returnSchedule = new Saichengbiao();
            returnSchedule.setQiuduia(schedule.getQiuduib()); // 互换
            returnSchedule.setQiuduib(schedule.getQiuduia());
            returnSchedule.setBisaibiaoti("第" + getRoundNumber(schedule.getBisaibiaoti()) + "轮 (次)");
            returnSchedule.setBisaileixing(schedule.getBisaileixing());
            schedules.add(returnSchedule);
        }

        return schedules;
    }

    /**
     * 淘汰赛制编排
     * 单败淘汰，可能需要轮空
     */
    private List<Saichengbiao> generateKnockoutSchedule(List<Integer> teamIds, 
                                                         Map<Integer, Integer> teamLevels, 
                                                         boolean levelBased) {
        List<Saichengbiao> schedules = new ArrayList<>();
        
        List<Integer> teams = new ArrayList<>(teamIds);
        
        // 按实力排序（如果需要），强队后相遇
        if (levelBased) {
            teams.sort((a, b) -> 
                teamLevels.getOrDefault(b, 0) - teamLevels.getOrDefault(a, 0)
            );
        } else {
            Collections.shuffle(teams);
        }

        // 计算最接近的 2 的幂次
        int size = teams.size();
        int powerOf2 = 1;
        while (powerOf2 < size) {
            powerOf2 *= 2;
        }

        // 计算轮空数量
        int byes = powerOf2 - size;
        
        // 第一轮比赛
        List<Integer> nextRound = new ArrayList<>();
        int round = 1;
        int matchNum = 1;

        // 处理轮空（实力强的队伍轮空）
        for (int i = 0; i < byes; i++) {
            nextRound.add(teams.get(i)); // 强队直接晋级
        }

        // 第一轮比赛配对
        for (int i = byes; i < size; i += 2) {
            if (i + 1 < size) {
                Saichengbiao schedule = new Saichengbiao();
                schedule.setQiuduia(teams.get(i));
                schedule.setQiuduib(teams.get(i + 1));
                schedule.setBisaibiaoti("第" + round + "轮 - 第" + matchNum + "场");
                schedule.setBisaileixing(1);
                schedules.add(schedule);
                
                // 胜者进入下一轮（用占位符表示，实际比赛后更新）
                nextRound.add(-1 - matchNum); // 负数表示待确定的胜者
                matchNum++;
            } else {
                // 奇数队伍，直接晋级
                nextRound.add(teams.get(i));
            }
        }

        // 后续轮次
        round++;
        while (nextRound.size() > 1) {
            List<Integer> currentRound = new ArrayList<>(nextRound);
            nextRound.clear();
            matchNum = 1;

            for (int i = 0; i < currentRound.size(); i += 2) {
                if (i + 1 < currentRound.size()) {
                    Integer teamA = currentRound.get(i);
                    Integer teamB = currentRound.get(i + 1);

                    // 如果都是占位符，表示后续比赛
                    if (teamA < 0 && teamB < 0) {
                        Saichengbiao schedule = new Saichengbiao();
                        schedule.setQiuduia(0); // 待确定
                        schedule.setQiuduib(0);
                        schedule.setBisaibiaoti("第" + round + "轮 - 第" + matchNum + "场");
                        schedule.setBisaileixing(1);
                        schedules.add(schedule);
                        nextRound.add(-100 - round * 10 - matchNum);
                    } else if (teamA < 0) {
                        // teamA 待定，teamB 轮空
                        nextRound.add(teamB);
                    } else if (teamB < 0) {
                        // teamB 待定，teamA 轮空
                        nextRound.add(teamA);
                    } else {
                        // 正常比赛
                        Saichengbiao schedule = new Saichengbiao();
                        schedule.setQiuduia(teamA);
                        schedule.setQiuduib(teamB);
                        schedule.setBisaibiaoti("第" + round + "轮 - 第" + matchNum + "场");
                        schedule.setBisaileixing(1);
                        schedules.add(schedule);
                        nextRound.add(-100 - round * 10 - matchNum);
                    }
                    matchNum++;
                } else {
                    nextRound.add(currentRound.get(i));
                }
            }
            round++;
        }

        return schedules;
    }

    /**
     * 从轮次字符串中提取轮次数字
     */
    private int getRoundNumber(String roundStr) {
        try {
            String num = roundStr.replaceAll("[^0-9]", "");
            return Integer.parseInt(num);
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * 根据球队实力水平进行种子队编排
     * 确保强队在后期相遇
     */
    public List<Integer> seedTeams(List<Integer> teamIds, Map<Integer, Integer> teamLevels) {
        List<Integer> seeded = new ArrayList<>(teamIds);
        
        // 按实力降序排序
        seeded.sort((a, b) -> 
            teamLevels.getOrDefault(b, 0) - teamLevels.getOrDefault(a, 0)
        );

        // 种子队编排：1 号种子 vs 最后种子，2 号种子 vs 倒数第二种子，以此类推
        List<Integer> result = new ArrayList<>();
        int n = seeded.size();
        
        for (int i = 0; i < n / 2; i++) {
            result.add(seeded.get(i)); // 强队
            result.add(seeded.get(n - 1 - i)); // 弱队
        }
        
        if (n % 2 != 0) {
            result.add(seeded.get(n / 2)); // 中间队伍
        }

        return result;
    }
}

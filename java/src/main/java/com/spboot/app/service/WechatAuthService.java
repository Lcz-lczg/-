package com.spboot.app.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.spboot.app.pojo.Guanzhong;
import com.jntoo.db.utils.InfoUtil;
import com.spboot.app.utils.JwtTokenUtils;
import com.spboot.app.utils.R;
import com.spboot.app.utils.Session;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WechatAuthService {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    @Resource
    private GuanzhongService guanzhongService;

    @Resource
    private JwtTokenUtils jwtTokenUtils;

    public R<Object> login(String code) {
        if (code == null || code.isEmpty()) {
            return R.error("code 不能为空");
        }

        String url = "https://api.weixin.qq.com/sns/jscode2session"
            + "?appid=" + appid
            + "&secret=" + secret
            + "&js_code=" + code
            + "&grant_type=authorization_code";

        String respStr;
        try {
            respStr = HttpUtil.get(url, 5000);
        } catch (Exception e) {
            return R.error("微信登录请求失败：" + e.getMessage());
        }

        JSONObject resp = JSONUtil.parseObj(respStr);
        String openid = resp.getStr("openid");
        if (openid == null) {
            String errMsg = resp.getStr("errmsg", "微信登录失败");
            return R.error(errMsg);
        }

        String username = "wx_" + (openid.length() > 8 ? openid.substring(0, 8) : openid);
        Guanzhong user = guanzhongService.findByYonghuming(username);

        if (user == null) {
            user = new Guanzhong();
            user.setYonghuming(username);
            user.setMima(openid);
            user.setXingming("微信用户");
            user.setXingbie("男");
            user.setIssh("是");
            guanzhongService.insert(user, BeanUtil.beanToMap(user));
            user = guanzhongService.findByYonghuming(username);
            if (user == null) {
                return R.error("用户创建失败");
            }
        }

        if (user.getIssh().equals("否")) {
            return R.error("账号已封禁");
        }

        Session session = guanzhongService.toSession(user);
        Map<String, Object> m = BeanUtil.beanToMap(session);
        m.remove("object");
        m.remove("pwd");
        long exp = 7 * 86400;
        m.put("exp", InfoUtil.time() + exp);
        String token = jwtTokenUtils.generateToken(session.getId(), m);

        return R.success(
            R.result()
                .set("user", session)
                .set("token", token)
                .set("role", session.getRoles())
        );
    }
}

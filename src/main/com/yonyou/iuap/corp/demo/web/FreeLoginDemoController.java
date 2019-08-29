package com.yonyou.iuap.corp.demo.web;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.corp.demo.service.AccessTokenService;
import com.yonyou.iuap.corp.demo.service.ApiFreeLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 描述:
 *
 * @author mantan
 * @create 2019-07-16 上午9:20
 */
@Controller
@RequestMapping("/")
public class FreeLoginDemoController {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private ApiFreeLoginService apiFreeLoginService;

    /**
     * 根据code获取免登信息
     * @param code
     * @param userMobileEmailFlag
     * @return
     * @throws Exception
     */
    @GetMapping("freelogin_view")
    public Object getFreeLoginInfo(@RequestParam String code,
                                   @RequestParam(required = false, defaultValue = "false") String userMobileEmailFlag,
                                   Model model) throws Exception {

        JSONObject result = JSONObject.parseObject(apiFreeLoginService.getFreeLoginInfo(code, userMobileEmailFlag, accessTokenService.getAccessToken()));

        if(result.getInteger("code") == 0){
            JSONObject data = result.getJSONObject("data");
            model.addAttribute("yhtUserId", data.getString("yhtUserId"));
            model.addAttribute("name", data.getString("name"));
            model.addAttribute("tenantId", data.getString("tenantId"));
            model.addAttribute("memberId", data.getString("memberId"));
            model.addAttribute("qzId", data.getString("qzId"));
            model.addAttribute("mobile", data.getString("mobile"));
            model.addAttribute("email", data.getString("email"));
            return "freelogin_view";
        } else {
            JSONObject data = result.getJSONObject("data");
            model.addAttribute("code", result.getInteger("code"));
            model.addAttribute("msg", result.getString("msg"));
            model.addAttribute("result", result.toJSONString());
            return "freelogin_error_view";
        }
    }
}

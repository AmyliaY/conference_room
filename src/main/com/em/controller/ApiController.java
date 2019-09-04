package main.com.em.controller;

import main.com.em.service.impl.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 描述: api接口
 *
 * @author mantan
 * @create 2019-04-09 下午1:02
 */
@Controller
@RequestMapping("/yonyouAPI")
public class ApiController {

    @Autowired
    private AccessTokenService accessTokenService;


    /**
     * 获得调用接口令牌access_token
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getAccessToken")
    @ResponseBody
    public Object getAccessToken() throws Exception {


        return accessTokenService.getAccessToken();
    }


}

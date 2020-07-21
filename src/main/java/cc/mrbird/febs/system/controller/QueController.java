package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.Md5Util;
import cc.mrbird.febs.system.entity.Que;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IQueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("que")
public class QueController extends BaseController {

    private final IQueService queService;



    @ResponseBody
    @PostMapping("/mque")
    public String mque(@RequestBody String queJson) throws JSONException {
        System.out.println(queJson);
        //System.out.println("666");
        try {
           // JSONObject mJsonObject = new JSONObject(loginJson);
            //String userName = mJsonObject.optString("user_name");
           // String userPassword = mJsonObject.optString("user_password");
           // userPassword = Md5Util.encrypt(userName.toLowerCase(), userPassword);
           // System.out.println("用户名：" + userName + "密码:" + userPassword);
           // User mUser = userService.findByName(userName);
             JSONObject mJsonObject = new JSONObject (queJson);
           String quetitle = mJsonObject.optString("quetitle");
            Que que = queService.findByName(quetitle);
            if(que!=null){
                //输入的用户名正确

                    return "yess";


            }
            else{
                //用户名错误
                return  "false";
            }

        } catch (Exception e) {
            System.out.println("login err");
            return "{\"type\":\"user_login\",\"data\":\"false\"}";
        }


    }


    @GetMapping("{username}")
    public Que getUser(@NotBlank(message = "{required}") @PathVariable String username) {
            return this.queService.findQueDetailList(username);
    }

    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username, String userId) {
        return this.queService.findByName(username) == null || StringUtils.isNotBlank(userId);
    }

    @GetMapping("list")
    @RequiresPermissions("que:view")
    public FebsResponse queList(Que que, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.queService.findQueDetailList(que, request));
        return new FebsResponse().success().data(dataTable);
    }

    @PostMapping
    @RequiresPermissions("que:add")
    @ControllerEndpoint(operation = "新增用户", exceptionMessage = "新增用户失败")
    public FebsResponse addque(@Valid Que que) {
        this.queService.createQue(que);
        return new FebsResponse().success();
    }


}

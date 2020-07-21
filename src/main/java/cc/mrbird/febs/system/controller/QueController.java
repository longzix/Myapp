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
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
//@RequestMapping("que")
public class QueController extends BaseController {

    private final IQueService queService;
//{"type":"que","data":[]}
//{"title":""，"que_a":"","que_b":"","que_c":"","que_d":""}

    @ResponseBody
    @PostMapping("/mque")
    public String mlogin(@RequestBody  String loginJson) throws JSONException {
        List<Que> ques = queService.findNQue(3);
        System.out.println(loginJson);
        System.out.println("666");
        String mj ="{\"type\":\"que\",\"data\":[]}";
        try {
            Que que = queService.findQueDetailList("dfdgg");
            Que que1 = queService.findQueDetailList("445ssssss");
            Que que2 = queService.findQueDetailList("dfdgg");
           // List<Que> ques = queService.findNQue(3);
           // Que que3 = ques.get(0);
           // System.out.println(ques.toString());
            JSONObject mJsonObject = new JSONObject(mj);

            //mJsonObject.put("data", que1);
           /* String s2 = "{\"title\":\"\"，\"que_a\":\"\",\"que_b\":\"\",\"que_c\":\"\",\"que_d\":\"\"}";
            JSONObject mJsonObject2 = new JSONObject(s2);
            mJsonObject2.put("title", que.getQueTitle());
            mJsonObject2.put("que_b", que.getQueB());
            mJsonObject2.put("que_c", que.getQueC());
            mJsonObject2.put("que_d", que.getQueD());*/
            mJsonObject.put("data", que.toString());
            if(que!=null){
                String s =que.getQueTitle();
                return mJsonObject.toString();
                //"{\"type\":\"que\",\"data\":}";
            }
            return "yess";

        } catch (Exception e) {
            System.out.println(ques.size());
            System.out.println();
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

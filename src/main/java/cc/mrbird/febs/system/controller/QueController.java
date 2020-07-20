package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Que;
import cc.mrbird.febs.system.service.IQueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    public FebsResponse userList(Que que, QueryRequest request) {
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

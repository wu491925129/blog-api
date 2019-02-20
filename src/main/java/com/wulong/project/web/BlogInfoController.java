package com.wulong.project.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.wulong.project.core.ProjectConstant;
import com.wulong.project.core.Result;
import com.wulong.project.core.ResultGenerator;
import com.wulong.project.model.BlogInfo;
import com.wulong.project.redis.service.RedisManagerService;
import com.wulong.project.service.BlogInfoService;
import com.wulong.project.tool.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
* Created by CodeGenerator on 2019/02/18.
*/
@CrossOrigin
@RestController
@Transactional(rollbackFor = Exception.class)
@RequestMapping("/info")
public class BlogInfoController {

    @Resource
    private BlogInfoService blogInfoService;

    @Autowired
    private RedisManagerService redisManagerService;

    @PostMapping("/add")
    public Result add(@RequestBody BlogInfo blogInfo, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(blogInfo.getId())) {
            blogInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            blogInfo.setCreateTime(new Date());
            blogInfo.setOpTime(new Date());
            blogInfo.setStatus(1);
            String token = request.getHeader(ProjectConstant.JWT_TOKEN);
            if (!Strings.isNullOrEmpty(token)) {
                // 从token中获取用户信息
                try {
                    Claims claims = JwtUtils.parseJWT(token);
                    JSONObject json = JSON.parseObject(claims.getSubject());
                    blogInfo.setOpUserId(json.getString("userId"));
                    blogInfo.setOpUserName(json.getString("userName"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            blogInfoService.save(blogInfo);
        } else {
            blogInfo.setOpTime(new Date());
            blogInfoService.update(blogInfo);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        blogInfoService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(BlogInfo blogInfo) {
        blogInfoService.update(blogInfo);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        BlogInfo blogInfo = blogInfoService.findById(id);
        return ResultGenerator.genSuccessResult(blogInfo);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<BlogInfo> list = blogInfoService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}

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
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    public Result delete(@RequestParam String id) {
        if (!Strings.isNullOrEmpty(id)){
            BlogInfo blogInfo = new BlogInfo();
            blogInfo.setId(id);
            blogInfo.setStatus(0);
            blogInfoService.updateByPrimaryKeySelective(blogInfo);
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("id不能为空");
    }

    @PostMapping("/update")
    public Result update(BlogInfo blogInfo) {
        blogInfoService.update(blogInfo);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable String id) {
        BlogInfo blogInfo = blogInfoService.findById(id);
        return ResultGenerator.genSuccessResult(blogInfo);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,HttpServletRequest request) {
        String token = request.getHeader(ProjectConstant.JWT_TOKEN);
        Map<String,Object> result = new HashMap<>();
        PageHelper.startPage(page, size);
        List<BlogInfo> list = new ArrayList<>();
        if (!Strings.isNullOrEmpty(token)) {
            // 从token中获取用户信息
            try {
                Claims claims = JwtUtils.parseJWT(token);
                JSONObject json = JSON.parseObject(claims.getSubject());
                String userId = json.getString("userId");
                Example example = new Example(BlogInfo.class);
                example.setOrderByClause("op_time DESC");
                example.createCriteria().andEqualTo("opUserId",userId).andEqualTo("status",1);
                list =blogInfoService.findByExample(example);
                PageInfo pageInfo = new PageInfo(list);
                result.put("opUserId",userId);
                result.put("pageInfo",pageInfo);
                return ResultGenerator.genSuccessResult(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}

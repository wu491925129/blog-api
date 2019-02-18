package com.wulong.project.service.impl;

import com.wulong.project.dao.BlogInfoMapper;
import com.wulong.project.model.BlogInfo;
import com.wulong.project.service.BlogInfoService;
import com.wulong.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/02/18.
 */
@Service
@Transactional
public class BlogInfoServiceImpl extends AbstractService<BlogInfo> implements BlogInfoService {
    @Resource
    private BlogInfoMapper blogInfoMapper;

}

package com.wulong.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "blog_info")
public class BlogInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 标签
     */
    private String tags;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "op_time")
    private Date opTime;

    /**
     * 状态
     */
    private int status;

    /**
     * 操作人id
     */
    @Column(name = "op_user_id")
    private String opUserId;

    /**
     * 操作人姓名
     */
    @Column(name = "op_user_name")
    private String opUserName;

    /**
     * 内容
     */
    private String content;

    /**
     * 文字描述
     */
    private String description;

    /**
     * 默认站位图id
     */
    @Column(name = "default_img_id")
    private String defaultImgId;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取标签
     *
     * @return tags - 标签
     */
    public String getTags() {
        return tags;
    }

    /**
     * 设置标签
     *
     * @param tags 标签
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return op_time - 更新时间
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * 设置更新时间
     *
     * @param opTime 更新时间
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 获取操作人id
     *
     * @return op_user_id - 操作人id
     */
    public String getOpUserId() {
        return opUserId;
    }

    /**
     * 设置操作人id
     *
     * @param opUserId 操作人id
     */
    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    /**
     * 获取操作人姓名
     *
     * @return op_user_name - 操作人姓名
     */
    public String getOpUserName() {
        return opUserName;
    }

    /**
     * 设置操作人姓名
     *
     * @param opUserName 操作人姓名
     */
    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String getDefaultImgId() {
        return defaultImgId;
    }

    public void setDefaultImgId(String defaultImgId) {
        this.defaultImgId = defaultImgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
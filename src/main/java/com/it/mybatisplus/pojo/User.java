package com.it.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author gxp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //@TableId(type = IdType.AUTO)
    /**
     * 主键生成方式：uuid、自增id、雪花算法、redis、zookeeper
     *
     *
     *
     * 默认为ID_WORKER，雪花算法
     * public enum IdType {
     *   AUTO(0),  //数据库id自增
     *   NONE(1), // 未设置主键
     *   INPUT(2), // 手动输入
     *   ID_WORKER(3), // 默认的全局唯一id
     *   UUID(4), // 全局唯一id uuid
     *   ID_WORKER_STR(5); //ID_WORKER 字符串表示法}
     */
    private Long id;
    private String name;
    private Integer age;
    private String email;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 乐观锁Version注解
     */
    @Version
    private Integer version;


}

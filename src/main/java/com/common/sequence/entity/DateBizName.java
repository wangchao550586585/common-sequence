package com.common.sequence.entity;


import cn.hutool.core.date.DateUtil;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/11/189:33
 */
public class DateBizName implements BizName {
    private String bizName;

    public DateBizName(String bizName) {
        this.bizName = bizName;
    }


    @Override
    public String create() {
        return bizName + DateUtil.today();
    }
}

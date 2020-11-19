package com.common.sequence.builder;


import com.common.sequence.sequence.Sequence;

/**
 * @author wangchao
 * @description: 分布式序列化构建者
 * @date 2020/11/17 9:32
 */
public interface SeqBuilder {

    Sequence build();
}

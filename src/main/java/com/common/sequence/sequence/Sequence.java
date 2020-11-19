package com.common.sequence.sequence;

import com.common.sequence.exception.SeqException;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/11/1713:59
 */
public interface Sequence {
    /**
     * 生成下一个序列
     * @return 系列号
     * @throws SeqException
     */
    long nextValue() throws SeqException;


    public String nextNo() throws SeqException;
    public String nextNoAddBizName() throws SeqException;

}

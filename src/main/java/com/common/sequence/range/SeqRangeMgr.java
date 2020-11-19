package com.common.sequence.range;

import com.common.sequence.entity.SeqRange;
import com.common.sequence.exception.SeqException;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/11/1714:04
 */
public interface SeqRangeMgr {
    /**
     * 获取指定bizName的下个区间
     *
     * @return
     * @throws SeqException
     */
    SeqRange nextSeqRange(String bizName) throws SeqException;


}

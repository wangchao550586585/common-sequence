package com.common.sequence.sequence;

import com.common.sequence.entity.BizName;
import com.common.sequence.range.SeqRangeMgr;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/11/1714:02
 */
public interface RangeSequence extends Sequence {

    /**
     * 设置区间管理器
     * @param seqRangeMgr
     */
    void setSeqRangeMgr(SeqRangeMgr seqRangeMgr);

    /**
     * 业务名
     * @param bizName
     */
    void setBizName(BizName bizName);



}

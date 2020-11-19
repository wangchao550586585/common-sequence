package com.common.sequence.sequence.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.common.sequence.entity.BizName;
import com.common.sequence.entity.SeqRange;
import com.common.sequence.exception.SeqException;
import com.common.sequence.range.SeqRangeMgr;
import com.common.sequence.sequence.RangeSequence;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/11/17 14:14
 */
public class DefaultRangeSequence implements RangeSequence {
    private final ReentrantLock lock = new ReentrantLock();
    private SeqRangeMgr seqRangeMgr;
    private BizName bizName;
    private SeqRange currentRange;

    @Override
    public long nextValue() throws SeqException {
        String name = bizName.create();
        if (Objects.isNull(currentRange)) {
            lock.lock();
            try {
                if (Objects.isNull(currentRange)) {
                    this.currentRange = seqRangeMgr.nextSeqRange(name);
                }
            } finally {
                lock.unlock();
            }
        }
        long value = currentRange.getAndIncrement();
        if (-1 == value) {
            lock.lock();
            try {
                while (true) {
                    if (currentRange.isRangeOverflow()) {
                        this.currentRange = seqRangeMgr.nextSeqRange(name);
                    }
                    value = currentRange.getAndIncrement();
                    if (-1 == value) {
                        continue;
                    }
                    break;
                }
            } finally {
                lock.unlock();
            }
        }

        if (value < 0) {
            throw new SeqException("序列号溢出, value = " + value);
        }
        return value;
    }


    @Override
    public void setSeqRangeMgr(SeqRangeMgr seqRangeMgr) {
        this.seqRangeMgr = seqRangeMgr;
    }

    @Override
    public void setBizName(BizName bizName) {
        this.bizName = bizName;
    }

    @Override
    public String nextNo() throws SeqException {
        return String.format("%s%06d", DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT), nextValue());
    }

    @Override
    public String nextNoAddBizName() throws SeqException {
        return String.format("%s_%s", bizName.create(), nextNo());
    }


}

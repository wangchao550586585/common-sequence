package com.common.sequence.entity;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/11/1714:16
 */
public class SeqRange {
    private final long start;
    private final long end;
    private final AtomicLong value;
    private volatile boolean rangeOverflow = false;

    public SeqRange(long start, long end) {
        this.start = start;
        this.end = end;
        this.value = new AtomicLong(start);
    }

    public long getAndIncrement() {
        long currentValue = value.getAndIncrement();
        if (currentValue > end) {
            rangeOverflow = true;
            return -1;
        }
        return currentValue;
    }

    public boolean isRangeOverflow() {
        return rangeOverflow;
    }
}

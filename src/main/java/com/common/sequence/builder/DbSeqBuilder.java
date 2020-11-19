package com.common.sequence.builder;

import com.common.sequence.entity.BizName;
import com.common.sequence.range.impl.DBSeqRangeMgr;
import com.common.sequence.sequence.Sequence;
import com.common.sequence.sequence.impl.DefaultRangeSequence;

import javax.sql.DataSource;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/11/1715:18
 */
public class DbSeqBuilder implements SeqBuilder {

    private BizName bizName;

    /**
     * DBSeqRangeMgr 所需参数
     */
    private DataSource dataSource;
    private String tableName = "range";
    private int retryTimes = 100;
    private int step = 100;
    private long stepStart = 0;

    public static DbSeqBuilder create() {
        DbSeqBuilder builder = new DbSeqBuilder();
        return builder;
    }

    @Override
    public Sequence build() {
        DBSeqRangeMgr seqRangeMgr = new DBSeqRangeMgr();
        seqRangeMgr.setDataSource(dataSource);
        seqRangeMgr.setTableName(tableName);
        seqRangeMgr.setRetryTimes(retryTimes);
        seqRangeMgr.setStep(step);
        seqRangeMgr.setStepStart(stepStart);
        seqRangeMgr.init();

        DefaultRangeSequence sequence = new DefaultRangeSequence();
        sequence.setSeqRangeMgr(seqRangeMgr);
        sequence.setBizName(bizName);
        return sequence;
    }

    public DbSeqBuilder bizName(BizName bizName) {
        this.bizName = bizName;
        return this;
    }

    public DbSeqBuilder dataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public DbSeqBuilder tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DbSeqBuilder retryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    public DbSeqBuilder step(int step) {
        this.step = step;
        return this;
    }

    public DbSeqBuilder stepStart(int stepStart) {
        this.stepStart = stepStart;
        return this;
    }


}

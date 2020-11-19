package com.common.sequence.range.impl;

import com.common.sequence.entity.SeqRange;
import com.common.sequence.exception.SeqException;
import com.common.sequence.helper.DbHelper;
import com.common.sequence.range.SeqRangeMgr;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/11/1714:50
 */
public class DBSeqRangeMgr implements SeqRangeMgr {
    private DataSource dataSource;
    private String tableName = "range";
    private int retryTimes = 100;
    private int step = 100;

    /**
     * 当前起始步长
     */
    private long stepStart = 0;

    @Override
    public SeqRange nextSeqRange(String bizName) throws SeqException {
        if (StringUtils.isEmpty(bizName)) {
            throw new SecurityException("[DBSeqRangeMgr-nextSeqRange] bizName名称为空");
        }
        Long oldValue;
        Long newValue;
        for (int i = 0; i < retryTimes; i++) {
            oldValue = DbHelper.selectRange(dataSource, tableName, bizName, stepStart);
            if (Objects.isNull(oldValue)) {
                continue;
            }
            newValue = oldValue + step;

            if (DbHelper.updateRange(dataSource, tableName, bizName, newValue, oldValue)) {
                return new SeqRange(oldValue + 1, newValue);
            }
        }
        throw new SeqException("重试太多次数,retryTime= " + retryTimes);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public long getStepStart() {
        return stepStart;
    }

    public void setStepStart(long stepStart) {
        this.stepStart = stepStart;
    }

    public void init() {
        checkParam();
        DbHelper.createTable(dataSource,tableName);
    }
    private void checkParam() {
        if (step <= 0) {
            throw new SecurityException("[DbSeqRangeMgr-checkParam] step must greater than 0.");
        }
        if (stepStart < 0) {
            throw new SecurityException("[DbSeqRangeMgr-setStepStart] stepStart < 0.");
        }
        if (retryTimes <= 0) {
            throw new SecurityException("[DbSeqRangeMgr-setRetryTimes] retryTimes must greater than 0.");
        }
        if (null == dataSource) {
            throw new SecurityException("[DbSeqRangeMgr-setDataSource] dataSource is null.");
        }
        if (StringUtils.isEmpty(tableName)) {
            throw new SecurityException("[DbSeqRangeMgr-setTableName] tableName is empty.");
        }
    }
}

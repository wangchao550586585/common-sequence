package com.common.sequence.config;

import com.common.sequence.builder.DbSeqBuilder;
import com.common.sequence.entity.DateBizName;
import com.common.sequence.sequence.Sequence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/11/1714:16
 */
@Configuration
public class SequenceConfig {

    @Bean(name = "sequenceForTest")
    public Sequence sequenceForTest(DataSource dataSource) {
        String s = "order_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
        return DbSeqBuilder.create().dataSource(dataSource)
                .tableName("range")
                .step(20)
                .stepStart(0)
                .retryTimes(50)
                .bizName(() -> s)
                .build();
    }

    @Bean(name = "sequenceForFXS")
    public Sequence sequenceForFXS(DataSource dataSource) {
        return DbSeqBuilder.create().dataSource(dataSource)
                .tableName("range")
                .step(20)
                .stepStart(0)
                .retryTimes(50)
                .bizName(new DateBizName("FXS"))
                .build();
    }


}

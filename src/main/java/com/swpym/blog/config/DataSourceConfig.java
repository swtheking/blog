package com.swpym.blog.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcConstants;
import com.swpym.blog.config.properties.DruidProperties;
import com.swpym.blog.config.properties.MutiDataSourceProperties;
import com.swpym.blog.mutidatesource.DSEnum;
import com.swpym.blog.mutidatesource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-03-10 16:41
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    DruidProperties druidProperties;

    @Autowired
    MutiDataSourceProperties mutiDataSourceProperties;

    private DruidDataSource coreDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }

    private DruidDataSource bizDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        mutiDataSourceProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 单数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "xncoding", name = "muti-datasource-open", havingValue = "false")
    public DruidDataSource singleDatasource() {
        return coreDataSource();
    }

    /**
     * 多数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "xncoding", name = "muti-datasource-open", havingValue = "true")
    public DynamicDataSource mutiDataSource() {
        DruidDataSource coreDataSource = coreDataSource();
        DruidDataSource bizDataSource = bizDataSource();
        try {
            coreDataSource.init();
            bizDataSource.init();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put(DSEnum.DATA_SOURCE_CORE, coreDataSource);
        hashMap.put(DSEnum.DATA_SOURCE_BIZ, bizDataSource);
        dynamicDataSource.setTargetDataSources(hashMap);
        dynamicDataSource.setDefaultTargetDataSource(coreDataSource);
        return dynamicDataSource;
    }


}

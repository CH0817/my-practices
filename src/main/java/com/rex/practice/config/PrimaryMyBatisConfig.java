package com.rex.practice.config;

import com.rex.practice.config.base.BaseMyBatisConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import javax.transaction.SystemException;

@Configuration
@MapperScan(basePackages = {"com.rex.practice.dao.mapper.primary"}, sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryMyBatisConfig extends BaseMyBatisConfig {

    @Value("${atomikos.datasource.primary.xa-data-source-class-name}")
    private String className;
    @Value("${atomikos.datasource.primary.xa-properties.url}")
    private String url;
    @Value("${atomikos.datasource.primary.xa-properties.user}")
    private String username;
    @Value("${atomikos.datasource.primary.xa-properties.password}")
    private String password;

    @Bean(name = "primaryTxManager")
    @Primary
    public PlatformTransactionManager transactionManager() throws SystemException {
        return defaultTransactionManager();
    }

    @DependsOn({"primaryTxManager"})
    @Bean(name = "primaryDataSource")
    @Primary
    public DataSource dataSource() {
        return createAtomikosDataSource(className, url, username, password);
    }

    @DependsOn({"primaryDataSource"})
    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource)
            throws Exception {
        return createSqlSessionFactory(primaryDataSource, "mybatis/mapper/primary/*.xml");
    }

    @DependsOn({"primarySqlSessionFactory"})
    @Bean(name = "primarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Profile({"dev", "test"})
    @DependsOn("primaryDataSource")
    @Bean(name = "primaryDataSourceInitializer")
    @Primary
    public DataSourceInitializer dataSourceInitializer(@Qualifier("primaryDataSource") DataSource datasource) {
        return createDataSourceInitializer(datasource, "db/schema-primary-dev.sql", "db/data/data-primary-dev.sql");
    }

}

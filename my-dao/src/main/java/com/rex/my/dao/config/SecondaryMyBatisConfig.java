package com.rex.my.dao.config;

import com.rex.my.dao.config.base.BaseMyBatisConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import javax.transaction.SystemException;

@Configuration
@MapperScan(basePackages = {"com.rex.my.dao.mapper.secondary"}, sqlSessionFactoryRef = "secondarySqlSessionFactory")
public class SecondaryMyBatisConfig extends BaseMyBatisConfig {

    @Value("${atomikos.datasource.secondary.xa-data-source-class-name}")
    private String className;
    @Value("${atomikos.datasource.secondary.xa-properties.url}")
    private String url;
    @Value("${atomikos.datasource.secondary.xa-properties.user}")
    private String username;
    @Value("${atomikos.datasource.secondary.xa-properties.password}")
    private String password;

    @Bean(name = "secondaryTxManager")
    public PlatformTransactionManager transactionManager() throws SystemException {
        return defaultTransactionManager();
    }

    @DependsOn({"secondaryTxManager"})
    @Bean(name = "secondaryDataSource")
    public DataSource dataSource() {
        return createAtomikosDataSource(className, url, username, password);
    }

    @DependsOn({"secondaryDataSource"})
    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("secondaryDataSource") DataSource secondaryDataSource)
            throws Exception {
        return createSqlSessionFactory(secondaryDataSource, "mybatis/mapper/secondary/*.xml");
    }

    @DependsOn({"secondarySqlSessionFactory"})
    @Bean(name = "secondarySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Profile({"dev", "test"})
    @DependsOn("secondaryDataSource")
    @Bean(name = "secondaryDataSourceInitializer")
    public DataSourceInitializer dataSourceInitializer(@Qualifier("secondaryDataSource") DataSource datasource) {
        return createDataSourceInitializer(datasource, "db/schema-secondary-dev.sql", "db/data/data-secondary-dev.sql");
    }

}

package com.rex.my.dao.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"com.rex.my.dao.mapper.primary"}, sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryMyBatisConfig {

    @Value("${atomikos.datasource.primary.xa-data-source-class-name}")
    private String className;
    @Value("${atomikos.datasource.primary.xa-properties.url}")
    private String url;
    @Value("${atomikos.datasource.primary.xa-properties.user}")
    private String username;
    @Value("${atomikos.datasource.primary.xa-properties.password}")
    private String password;

    @Bean(name = "primaryAtomikosTransactionManager")
    @Primary
    public TransactionManager atomikosTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean(name = "primaryUserTransaction")
    @Primary
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);
        return userTransactionImp;
    }

    @DependsOn({"primaryUserTransaction", "primaryAtomikosTransactionManager"})
    @Bean(name = "primaryTxManager")
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("primaryUserTransaction") UserTransaction userTransaction,
                                                         @Qualifier("primaryAtomikosTransactionManager") TransactionManager atomikosTransactionManager) {
        return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
    }

    @DependsOn({"primaryTxManager"})
    @Bean(name = "primaryDataSource")
    @Primary
    public DataSource dataSource() {
        AtomikosDataSourceBean result = new AtomikosDataSourceBean();
        result.setXaDataSourceClassName(className);
        Properties properties = new Properties();
        properties.put("URL", url);
        properties.put("user", username);
        properties.put("password", password);
        result.setXaProperties(properties);
        return result;
    }

    @DependsOn({"primaryDataSource"})
    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/primary/*.xml"));
        sessionFactory.setConfiguration(getMyBatisConfiguration());
        return sessionFactory.getObject();
    }

    @DependsOn({"primarySqlSessionFactory"})
    @Bean(name = "primarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    public org.apache.ibatis.session.Configuration getMyBatisConfiguration() {
        org.apache.ibatis.session.Configuration result = new org.apache.ibatis.session.Configuration();
        result.setLogImpl(Log4jImpl.class);
        return result;
    }

    @Profile({"dev", "test"})
    @DependsOn({"primaryDataSource", "primaryResourceDatabasePopulator"})
    @Bean(name = "primaryDataSourceInitializer")
    @Primary
    public DataSourceInitializer dataSourceInitializer(@Qualifier("primaryDataSource") DataSource datasource,
                                                       @Qualifier("primaryResourceDatabasePopulator") ResourceDatabasePopulator resourceDatabasePopulator) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(datasource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Profile({"dev", "test"})
    @Bean(name = "primaryResourceDatabasePopulator")
    @Primary
    public ResourceDatabasePopulator resourceDatabasePopulator() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("db/schema-primary-dev.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("db/data/data-primary-dev.sql"));
        return resourceDatabasePopulator;
    }

}

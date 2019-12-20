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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
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
@MapperScan(basePackages = {"com.rex.my.dao.mapper.secondary"}, sqlSessionFactoryRef = "secondarySqlSessionFactory")
public class SecondaryMyBatisConfig {

    @Value("${atomikos.datasource.secondary.xa-data-source-class-name}")
    private String className;
    @Value("${atomikos.datasource.secondary.xa-properties.url}")
    private String url;
    @Value("${atomikos.datasource.secondary.xa-properties.user}")
    private String username;
    @Value("${atomikos.datasource.secondary.xa-properties.password}")
    private String password;

    @Bean(name = "secondaryAtomikosTransactionManager")
    public TransactionManager atomikosTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean(name = "secondaryUserTransaction")
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);
        return userTransactionImp;
    }

    @DependsOn({"secondaryUserTransaction", "secondaryAtomikosTransactionManager"})
    @Bean(name = "secondaryTxManager")
    public PlatformTransactionManager transactionManager(@Qualifier("secondaryUserTransaction") UserTransaction userTransaction,
                                                         @Qualifier("secondaryAtomikosTransactionManager") TransactionManager atomikosTransactionManager) {
        return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
    }

    @DependsOn({"secondaryTxManager"})
    @Bean(name = "secondaryDataSource")
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

    @DependsOn({"secondaryDataSource"})
    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("secondaryDataSource") DataSource secondaryDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(secondaryDataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/secondary/*.xml"));
        sessionFactory.setConfiguration(getMyBatisConfiguration());
        return sessionFactory.getObject();
    }

    @DependsOn({"secondarySqlSessionFactory"})
    @Bean(name = "secondarySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    public org.apache.ibatis.session.Configuration getMyBatisConfiguration() {
        org.apache.ibatis.session.Configuration result = new org.apache.ibatis.session.Configuration();
        result.setLogImpl(Log4jImpl.class);
        return result;
    }

    @Profile({"dev", "test"})
    @DependsOn({"secondaryDataSource", "secondaryResourceDatabasePopulator"})
    @Bean(name = "secondaryDataSourceInitializer")
    public DataSourceInitializer dataSourceInitializer(@Qualifier("secondaryDataSource") DataSource datasource,
                                                       @Qualifier("secondaryResourceDatabasePopulator") ResourceDatabasePopulator resourceDatabasePopulator) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(datasource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Profile({"dev", "test"})
    @Bean(name = "secondaryResourceDatabasePopulator")
    public ResourceDatabasePopulator resourceDatabasePopulator() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("db/schema-secondary-dev.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("db/data/data-secondary-dev.sql"));
        return resourceDatabasePopulator;
    }

}

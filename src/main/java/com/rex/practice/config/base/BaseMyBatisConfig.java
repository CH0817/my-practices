package com.rex.practice.config.base;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.util.Properties;

public abstract class BaseMyBatisConfig {

    protected PlatformTransactionManager defaultTransactionManager() throws SystemException {
        return new JtaTransactionManager(defaultUserTransaction(), defaultUserTransactionManager());
    }

    private TransactionManager defaultUserTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    private UserTransaction defaultUserTransaction() throws SystemException {
        UserTransaction userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);
        return userTransactionImp;
    }

    protected DataSource createAtomikosDataSource(String className, String url, String username, String password) {
        AtomikosDataSourceBean result = new AtomikosDataSourceBean();
        result.setXaDataSourceClassName(className);
        Properties properties = new Properties();
        properties.put("URL", url);
        properties.put("user", username);
        properties.put("password", password);
        result.setXaProperties(properties);
        return result;
    }

    protected SqlSessionFactory createSqlSessionFactory(DataSource dataSource, String mapperXmlLocation) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:" + mapperXmlLocation));
        sessionFactory.setConfiguration(getMyBatisConfiguration());
        return sessionFactory.getObject();
    }

    private Configuration getMyBatisConfiguration() {
        Configuration result = new Configuration();
        result.setLogImpl(Log4jImpl.class);
        return result;
    }

    protected DataSourceInitializer createDataSourceInitializer(DataSource datasource, String schemaSqlLocation, String dataSqlLocation) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(datasource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator(schemaSqlLocation, dataSqlLocation));
        return dataSourceInitializer;
    }

    private ResourceDatabasePopulator resourceDatabasePopulator(String schemaSqlLocation, String dataSqlLocation) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource(schemaSqlLocation));
        resourceDatabasePopulator.addScript(new ClassPathResource(dataSqlLocation));
        return resourceDatabasePopulator;
    }

}

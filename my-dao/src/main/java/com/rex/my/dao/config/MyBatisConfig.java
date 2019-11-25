package com.rex.my.dao.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;

@Configuration
@MapperScan(basePackages = {"com.rex.my.dao.mapper.mysql"}, sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
public class MyBatisConfig {

    @Bean(name = "mysqlDataSource")
    @Primary
    @ConfigurationProperties(prefix = "datasource.mysql")
    public DataSource mysqlDataSource() {
        return new AtomikosDataSourceBean();
    }

    @Bean(name = "transactionManager")
    @Primary
    public JtaTransactionManager transactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }

    @Bean(name = "mysqlSqlSessionFactory")
    @Primary
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource mysqlDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mysqlDataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/mysql/*.xml"));
        return sessionFactory.getObject();
    }

    @Bean(name = "mysqlSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate mysqlSqlSessionTemplate(
            @Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // private final MySqlProperties mySqlProperties;
    //
    // @Autowired
    // public MyBatisConfig(MySqlProperties mySqlProperties) {
    //     this.mySqlProperties = mySqlProperties;
    // }
    //
    // @Bean(name = "mysqlDataSource")
    // @Primary
    // public DataSource mysqlDataSource() {
    //     Properties properties = new Properties();
    //     properties.setProperty("url", mySqlProperties.getUrl());
    //     properties.setProperty("user", mySqlProperties.getUser());
    //     properties.setProperty("password", mySqlProperties.getPassword());
    //     AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
    //     dataSourceBean.setXaDataSourceClassName(mySqlProperties.getDriverClass());
    //     dataSourceBean.setXaProperties(properties);
    //     return dataSourceBean;
    // }
    //
    // @Bean(name = "transactionManager")
    // @Primary
    // public JtaTransactionManager transactionManager() {
    //     UserTransactionManager userTransactionManager = new UserTransactionManager();
    //     UserTransaction userTransaction = new UserTransactionImp();
    //     return new JtaTransactionManager(userTransaction, userTransactionManager);
    // }
    //
    // @Bean(name = "mysqlSqlSessionFactory")
    // @Primary
    // public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource mysqlDataSource)
    //         throws Exception {
    //     final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    //     sessionFactory.setDataSource(mysqlDataSource);
    //     sessionFactory.setMapperLocations(
    //             new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/mysql/*.xml"));
    //     return sessionFactory.getObject();
    // }
    //
    // @Bean(name = "mysqlSqlSessionTemplate")
    // @Primary
    // public SqlSessionTemplate mysqlSqlSessionTemplate(
    //         @Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    //     return new SqlSessionTemplate(sqlSessionFactory);
    // }

}

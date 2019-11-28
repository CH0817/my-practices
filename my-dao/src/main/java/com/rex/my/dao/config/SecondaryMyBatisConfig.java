package com.rex.my.dao.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.rex.my.dao.mapper.secondary"}, sqlSessionFactoryRef = "secondarySqlSessionFactory")
public class SecondaryMyBatisConfig {

    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties(prefix = "atomikos.datasource.secondary")
    public DataSource dataSource() {
        return new AtomikosDataSourceBean();
    }

    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("secondaryDataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/secondary/*.xml"));
        return sessionFactory.getObject();
    }

    @Profile("dev")
    @Bean(name = "secondaryDataSourceInitializer")
    public DataSourceInitializer dataSourceInitializer(@Qualifier("secondaryDataSource") DataSource datasource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(datasource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator());
        return dataSourceInitializer;
    }

    private ResourceDatabasePopulator resourceDatabasePopulator() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("db/schema-secondary-dev.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("db/data/data-secondary-dev.sql"));
        return resourceDatabasePopulator;
    }

}

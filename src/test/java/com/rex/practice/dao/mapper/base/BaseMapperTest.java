package com.rex.practice.dao.mapper.base;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@MybatisTest
@Transactional
@Ignore
public class BaseMapperTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

}

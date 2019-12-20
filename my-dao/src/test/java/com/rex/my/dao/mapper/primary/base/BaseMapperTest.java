package com.rex.my.dao.mapper.primary.base;

import com.rex.my.dao.config.DaoConfig;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DaoConfig.class)
@ActiveProfiles("test")
@Transactional
@Ignore
public class BaseMapperTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

}

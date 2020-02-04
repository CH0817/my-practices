package com.rex.practice.dao.mapper.primary.base;

import com.rex.practice.PracticeApplication;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PracticeApplication.class})
@Transactional
@Ignore
public class BaseMapperTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

}

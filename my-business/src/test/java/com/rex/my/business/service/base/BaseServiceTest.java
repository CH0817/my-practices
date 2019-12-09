package com.rex.my.business.service.base;

import com.rex.MyBusinessApplication;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyBusinessApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Ignore
public class BaseServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

}
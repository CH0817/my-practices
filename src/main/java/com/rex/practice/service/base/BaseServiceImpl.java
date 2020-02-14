package com.rex.practice.service.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = {Exception.class})
public abstract class BaseServiceImpl {

    protected Logger logger = LoggerFactory.getLogger(getClass());

}

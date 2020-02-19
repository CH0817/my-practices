package com.rex.practice.service.impl;

import com.rex.practice.dao.mapper.RegisterTokenMapper;
import com.rex.practice.dao.model.RegisterToken;
import com.rex.practice.service.TokenService;
import com.rex.practice.service.base.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class TokenServiceImpl extends BaseServiceImpl implements TokenService {

    private RegisterTokenMapper registerTokenMapper;

    @Autowired
    public TokenServiceImpl(RegisterTokenMapper registerTokenMapper) {
        this.registerTokenMapper = registerTokenMapper;
    }

    @Override
    public String createRegisterToken(String userId) {
        assert StringUtils.isNotBlank(userId);
        RegisterToken entity = new RegisterToken();
        entity.setUserId(userId);
        entity.setToken(UUID.randomUUID().toString().replace("-", ""));
        entity.setExpireDate(Date.from(LocalDate.now().plusDays(7L).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return registerTokenMapper.insertSelective(entity) == 1 ? entity.getToken() : "";
    }

    @Override
    public String getRegisterToken(String userId) {
        assert StringUtils.isNotBlank(userId);
        RegisterToken registerToken = registerTokenMapper.findByUserId(userId);
        return Objects.nonNull(registerToken) ? registerToken.getToken() : "";
    }

    @Override
    public boolean isTokenExpired(String userId) throws Exception {
        assert StringUtils.isNotBlank(userId);
        RegisterToken token = registerTokenMapper.findByUserId(userId);
        if (Objects.nonNull(token)) {
            return LocalDate.now().isAfter(token.getExpireDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        // TODO 自訂 Exception
        throw new Exception("cannot found register token by " + userId);
    }

    @Override
    public void deleteToken(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            registerTokenMapper.deleteByUserId(userId);
        }
    }

}

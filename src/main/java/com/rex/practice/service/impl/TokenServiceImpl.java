package com.rex.practice.service.impl;

import com.rex.practice.dao.mapper.RegisterTokenMapper;
import com.rex.practice.dao.model.RegisterToken;
import com.rex.practice.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private RegisterTokenMapper registerTokenMapper;

    @Autowired
    public TokenServiceImpl(RegisterTokenMapper registerTokenMapper) {
        this.registerTokenMapper = registerTokenMapper;
    }

    @Override
    public String createRegisterToken(String email) {
        assert StringUtils.isNotBlank(email);
        RegisterToken entity = new RegisterToken();
        entity.setEmail(email);
        entity.setToken(UUID.randomUUID().toString());
        return registerTokenMapper.insertSelective(entity) == 1 ? entity.getToken() : "";
    }

    @Override
    public String getRegisterToken(String email) {
        assert StringUtils.isNotBlank(email);
        RegisterToken registerToken = registerTokenMapper.findByEmail(email);
        return Objects.nonNull(registerToken) ? registerToken.getToken() : "";
    }

}

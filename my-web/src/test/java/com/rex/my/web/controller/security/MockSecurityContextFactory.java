package com.rex.my.web.controller.security;

import com.rex.my.model.dao.primary.User;
import com.rex.my.security.model.SecuredUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class MockSecurityContextFactory implements WithSecurityContextFactory<MockSecuredUser> {

    @Override
    public SecurityContext createSecurityContext(MockSecuredUser mockSecuredUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        SecuredUser principal = new SecuredUser(createUser(mockSecuredUser), getDefaultAuthorityList());
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

    private User createUser(MockSecuredUser mockSecuredUser) {
        User result = new User();
        result.setId(mockSecuredUser.id());
        result.setEmail(mockSecuredUser.email());
        result.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(mockSecuredUser.password()));
        return result;
    }

    private List<GrantedAuthority> getDefaultAuthorityList() {
        return AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
    }

}

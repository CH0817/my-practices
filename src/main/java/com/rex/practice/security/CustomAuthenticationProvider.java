package com.rex.practice.security;

import com.rex.practice.dao.model.User;
import com.rex.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 自訂驗證 provider
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(authentication.getName());
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("此Email尚未註冊");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches((CharSequence) authentication.getCredentials(), user.getPassword())) {
            throw new BadCredentialsException("錯誤的Email或密碼");
        }

        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
        return new UsernamePasswordAuthenticationToken(new CustomUserDetails(user, authorityList),
                authentication.getCredentials(), authorityList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}

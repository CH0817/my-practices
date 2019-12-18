package com.rex.my.security.custom;

import com.rex.my.dao.mapper.primary.UserMapper;
import com.rex.my.model.dao.primary.User;
import com.rex.my.security.model.SecuredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private UserMapper userMapper;

    @Autowired
    public CustomUserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findByEmail(email);
        if (Objects.isNull(user)) {
            // 這個 exception 會被 AbstractUserDetailsAuthenticationProvider 替換，無法被攔截
            throw new UsernameNotFoundException("cannot found " + email);
        }
        // TODO authority list 進資料庫？
        // 必須用 ROLE_ 起頭，否則 hasRole() 無法正常使用
        return new SecuredUser(user, AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER"));
    }

}

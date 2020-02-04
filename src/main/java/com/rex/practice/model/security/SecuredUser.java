package com.rex.practice.model.security;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class SecuredUser extends User {

    private String id;
    private String email;

    public SecuredUser(com.rex.practice.dao.model.primary.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.id = user.getId();
        this.email = user.getEmail();
    }

    @Override
    public String toString() {
        ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
        return ReflectionToStringBuilder.toStringExclude(this, "password");
    }

}

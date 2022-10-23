package com.go.conowithme.api.user.service;

import com.go.conowithme.api.user.domain.entity.UserEntity;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserQueryService userQueryService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return createUserDetails(findByEmailAndDeletedAtIsNull(username));
    }

    private UserEntity findByEmailAndDeletedAtIsNull(String username) {
        return userQueryService.findByEmailAndDeletedAtIsNull(username);
    }

    private UserDetails createUserDetails(UserEntity userEntity) {
        return new User(String.valueOf(userEntity.getId()), userEntity.getPassword(),
            Collections.singleton(getGrantedAuthority(userEntity)));
    }

    private SimpleGrantedAuthority getGrantedAuthority(UserEntity userEntity) {
        return new SimpleGrantedAuthority(userEntity.getAuthority().toString());
    }
}

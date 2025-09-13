package com.example.demo.service;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.SecurityJwtTokenProvider;
import com.example.demo.security.SecurityUser;

import org.springframework.security.core.Authentication;

@Service
public class AuthService {
    
    @Autowired
    private SecurityJwtTokenProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    private static final PolicyFactory POLICY = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    public String signUser(String login, String password) {
        if (userExists(login))
            return "";

        String newLogin = POLICY.sanitize(login);
        if(!newLogin.equals(login))
            return "";

        String newPassword = passwordEncoder.encode(password);
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(login);
        userEntity.setPassword(newPassword);
        userEntity.setRole("USER");

        userRepository.save(userEntity);

        String token = setUserToSecurity(userEntity);
        return token;
    }

    public String logUser(String login, String password) {
        String token = "";
        UserEntity userEntity = userRepository.findByLogin(login);
        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            token = setUserToSecurity(userEntity);
        }
        return token;
    }

    public String setUserToSecurity(UserEntity user) {
        SecurityUser securityUser = new SecurityUser(user.getLogin(), user.getPassword(),
                user.getRole());
        Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null,
                securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    private boolean userExists(String login) {
        return userRepository.findByLogin(login) != null;
    }
}
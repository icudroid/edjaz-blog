package com.edjaz.blog.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edjaz.blog.domain.User;
import com.edjaz.blog.repository.UserRepository;
import com.edjaz.blog.security.AuthoritiesConstants;
import com.edjaz.blog.security.jwt.JWTConfigurer;
import com.edjaz.blog.security.jwt.TokenProvider;
import com.edjaz.blog.service.UserService;
import com.edjaz.blog.service.dto.UserDTO;
import com.edjaz.blog.web.rest.errors.EmailAlreadyUsedException;
import com.edjaz.blog.web.rest.errors.LoginAlreadyUsedException;
import com.edjaz.blog.web.rest.util.HeaderUtil;
import com.edjaz.blog.web.rest.vm.ManagedUserVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SetupResource {

    private final Logger log = LoggerFactory.getLogger(SetupResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    public SetupResource(UserRepository userRepository, UserService userService, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }


    @PreAuthorize("isFirstSetup()")
    @GetMapping("/setup")
    @Timed
    public ResponseEntity<Boolean> getSetup() {
        log.debug("REST request to get is the first setup");
        return ResponseEntity.ok(true);
    }


    @PreAuthorize("isFirstSetup()")
    @GetMapping("/setup/user")
    @Timed
    public ResponseEntity<Long> getIdAdmin() {
        log.debug("REST request to get id admin");
        return ResponseEntity.ok(
            userRepository.findOneByLogin("admin")
                .orElseThrow(() -> new UsernameNotFoundException("User admin not found"))
                .getId()
        );
    }


    @PutMapping("/setup/user")
    @Timed
    @PreAuthorize("isFirstSetup()")
    public ResponseEntity<JWTToken> updateUser(@Valid @RequestBody ManagedUserVM userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }

        //Met les droits admin par defaut
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);
        userService.changePassword(userDTO.getPassword());

        // Log l'utilisateur
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDTO.getLogin(), userDTO.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication, false);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }


    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

}

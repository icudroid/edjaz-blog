package com.edjaz.blog.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.edjaz.blog.domain.User;
import com.edjaz.blog.repository.UserRepository;
import com.edjaz.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SetupResource {

    private final Logger log = LoggerFactory.getLogger(SetupResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    public SetupResource(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
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
}

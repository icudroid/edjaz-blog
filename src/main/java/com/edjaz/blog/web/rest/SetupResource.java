package com.edjaz.blog.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SetupResource {

    private final Logger log = LoggerFactory.getLogger(SetupResource.class);

    @PreAuthorize("isFirstSetup() or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/setup")
    @Timed
    public ResponseEntity<Boolean> getSetup() {
        log.debug("REST request to get is the first setup");
        return ResponseEntity.ok(true);
    }
}

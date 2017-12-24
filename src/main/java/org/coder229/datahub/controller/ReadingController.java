package org.coder229.datahub.controller;

import org.coder229.datahub.model.Reading;
import org.coder229.datahub.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reading")
public class ReadingController {

    @Autowired
    private ReadingService readingService;

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public Page<Reading> get(Authentication authentication, Pageable pageable) {
        return readingService.getReadings(pageable);
    }
}

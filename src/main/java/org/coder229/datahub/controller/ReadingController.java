package org.coder229.datahub.controller;

import org.coder229.datahub.model.Reading;
import org.coder229.datahub.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;

@RestController
@RequestMapping("/reading")
public class ReadingController {

    @Autowired
    private ReadingService readingService;

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public Page<Reading> get(
            @RequestParam(value = "start", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(value = "end", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(value = "tz", required = false) String timezone,
            Pageable pageable) {

        if (StringUtils.isEmpty(start)) {
            return readingService.getReadings(pageable);
        }

        return readingService.getReadings(start,
                end == null ? LocalDate.now() : end,
                StringUtils.isEmpty(timezone) ? ZoneId.systemDefault().toString() : timezone,
                pageable);
    }
}

package org.arias.reports.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arias.reports.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
public class ReportsController {

    private final ReportService reportService;

    @GetMapping("/reports/{name}")
    public ResponseEntity<Map<String,String>> getReport(@PathVariable String name) {
        log.info("GET: report {}", name);
        return ResponseEntity.ok(Map.of("Company",reportService.makeReport(name)));
    }

}

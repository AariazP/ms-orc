package org.arias.reports.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arias.reports.helper.ReportHelper;
import org.arias.reports.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
public class ReportsController {

    private final ReportService reportService;

    @GetMapping("/reports/{name}")
    public ResponseEntity<Map<String,String>> getReport(@PathVariable String name) {
        return ResponseEntity.ok(Map.of("Company",reportService.makeReport(name)));
    }

    @PostMapping("/reports")
    public ResponseEntity<String> postReport(@RequestBody String report) {
        var response = this.reportService.saveReport(report);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reports/{name}")
    public ResponseEntity<String> deleteReport(@PathVariable String name) {
        this.reportService.deleteReport(name);
        return ResponseEntity.ok("Report deleted");
    }

}

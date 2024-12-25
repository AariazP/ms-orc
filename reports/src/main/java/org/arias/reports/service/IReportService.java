package org.arias.reports.service;

import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arias.reports.repositories.CompaniesRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class IReportService implements ReportService{

    private final CompaniesRepository companiesRepository;
    private final EurekaClient eurekaClient;

    @Override
    public String makeReport(String name) {
        return companiesRepository.findByName(name).orElseThrow().getName();
    }

    @Override
    public String saveReport(String nameReport) {
        return null;
    }

    @Override
    public void deleteReport(String nameReport) {

    }
}

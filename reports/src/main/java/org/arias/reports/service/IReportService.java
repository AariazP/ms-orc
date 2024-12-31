package org.arias.reports.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arias.reports.helper.ReportHelper;
import org.arias.reports.models.Category;
import org.arias.reports.models.Company;
import org.arias.reports.models.WebSite;
import org.arias.reports.repositories.CompaniesRepositoriesFallback;
import org.arias.reports.repositories.CompaniesRepository;
import org.arias.reports.streams.ReportPublisher;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class IReportService implements ReportService {

    private final CompaniesRepository companiesRepository;
    private final ReportHelper reportHelper;
    private final CompaniesRepositoriesFallback companiesRepoFallback;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    private final ReportPublisher reportPublisher;
    private final ObjectMapper objectMapper;

    @Override
    public String makeReport(String name) {
        return circuitBreakerFactory.create("circuitebreaker")
                .run(
                        () -> makeReportMain(name),
                        throwable -> makeReportFallback(name, throwable));
    }

    @Override
    public String saveReport(String nameReport) {

        List<String> data = this.reportHelper.getPlacedholders(nameReport);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var company = this.buildCompany(data, formatter);
        this.reportPublisher.publishReport(nameReport);
        circuitBreakerFactory.create("circuitebreaker-event-company")
                .run(
                        () -> this.companiesRepository.postByName(company),
                        throwable -> this.reportPublisher.saveReport(this.getJSONFromCompany(company)));

        return "Report saved";
    }

    private String getJSONFromCompany(Company company){
        try {
            return objectMapper.writeValueAsString(company);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Company buildCompany(List<String> data, DateTimeFormatter formatter) {
        return Company.builder()
                .name(data.get(0).replaceAll("[{}]", ""))
                .logo(data.get(0).replaceAll("[{}]", "") + ".png")
                .foundationDate(LocalDate.parse(data.get(1).replaceAll("[{}]", ""), formatter))
                .founder(data.get(2).replaceAll("[{}]", ""))
                .webSites(
                        List.of(
                                WebSite.builder()
                                        .name(data.get(0).replaceAll("[{}]", ""))
                                        .description("Description of " + data.get(0).replaceAll("[{}]", ""))
                                        .category(Category.NONE)
                                        .build()
                        )

                )
                .build();
    }

    @Override
    public void deleteReport(String nameReport) {
        this.companiesRepository.deleteByName(nameReport);
    }


    public String makeReportMain(String name) {
        return this.reportHelper.buildReport(companiesRepository.findByName(name).orElseThrow());

    }

    public String makeReportFallback(String name, Throwable throwable) {
        log.warn("Error in circuit breaker: {}", throwable.getMessage());
        return this.reportHelper.buildReport(this.companiesRepoFallback.findByName(name));

    }

}
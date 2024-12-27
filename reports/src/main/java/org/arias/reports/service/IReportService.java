package org.arias.reports.service;

import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arias.reports.helper.ReportHelper;
import org.arias.reports.models.Category;
import org.arias.reports.models.Company;
import org.arias.reports.models.WebSite;
import org.arias.reports.repositories.CompaniesRepositoriesFallback;
import org.arias.reports.repositories.CompaniesRepository;
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

        Company company = Company.builder()
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

        this.companiesRepository.postByName(company);
        return "Report saved";
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
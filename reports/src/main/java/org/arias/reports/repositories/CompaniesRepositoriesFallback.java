package org.arias.reports.repositories;

import org.arias.reports.models.Company;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
@Slf4j
public class CompaniesRepositoriesFallback {

    private final WebClient webClient;
    private final String uri;

    public CompaniesRepositoriesFallback(WebClient webClient, @Value("${fallback.url}") String uri) {
        this.webClient = webClient;
        this.uri = uri;
    }

    public Company findByName(String name) {
        log.info("GET in fallback: company {}", name);
        System.out.println("uri = " + uri);
        return this.webClient.get()
                .uri(uri, name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Company.class)
                .log()
                .block();
    }

}

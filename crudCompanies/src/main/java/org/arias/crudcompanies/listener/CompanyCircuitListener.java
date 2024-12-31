package org.arias.crudcompanies.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arias.crudcompanies.entities.Company;
import org.arias.crudcompanies.services.CompanyService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class CompanyCircuitListener {

    private final CompanyService companyService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "consumerCompany", groupId = "circuitbreaker")
    private void insertCompany(String company) throws JsonProcessingException {
        log.info("Inserting company: {}", company);
        var companyObj = objectMapper.readValue(company, Company.class);
        companyService.create(companyObj);
    }
}

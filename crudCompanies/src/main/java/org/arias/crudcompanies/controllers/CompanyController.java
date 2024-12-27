package org.arias.crudcompanies.controllers;

import lombok.AllArgsConstructor;
import org.arias.crudcompanies.entities.Company;
import org.arias.crudcompanies.services.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping(path = "company")
@Slf4j
@Tag(name = "Companies resource")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "get a company given a company name")
    @GetMapping(path = "{name}")
    public ResponseEntity<Company> get(@PathVariable String name) {
        log.info("GET: company {}", name);
        return ResponseEntity.ok(this.companyService.readByName(name));
    }

    @Operation(summary = "save in DB a company given a company from body")
    @PostMapping
    public ResponseEntity<Company> post(@RequestBody Company company) {
        log.info("Post: company {}", company.getName());
        return ResponseEntity.created(
                        URI.create(this.companyService.create(company).getName()))
                .build();
    }

    @Operation(summary = "update in DB a company given a company from body")
    @PutMapping(path = "{name}")
    public ResponseEntity<Company> put(@RequestBody Company company,
                                       @PathVariable String name) {
        log.info("PUT: company {}", name);
        return ResponseEntity.ok(this.companyService.update(company, name));
    }

    @Operation(summary = "delete in DB a company given a company name")
    @DeleteMapping(path = "{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        log.info("DELETE: company {}", name);
        this.companyService.delete(name);
        return ResponseEntity.noContent().build();
    }
}
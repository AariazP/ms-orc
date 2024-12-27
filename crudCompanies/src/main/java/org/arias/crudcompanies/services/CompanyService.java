package org.arias.crudcompanies.services;

import org.arias.crudcompanies.entities.Company;

public interface CompanyService {

    Company create(Company company);
    Company readByName(String name);
    Company update(Company company, String name);
    void  delete(String name);
}

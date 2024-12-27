package org.arias.crudcompanies.repositories;

import org.arias.crudcompanies.entities.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebSiteRepository extends JpaRepository<WebSite, Long> {
    Optional<WebSite> findByName(String name);
}

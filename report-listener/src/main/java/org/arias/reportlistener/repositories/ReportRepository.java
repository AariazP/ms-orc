package org.arias.reportlistener.repositories;


import org.arias.reportlistener.documents.ReportDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<ReportDocument, String> {
}

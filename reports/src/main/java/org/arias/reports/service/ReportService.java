package org.arias.reports.service;

public interface ReportService {

    String makeReport(String nameReport);
    String saveReport(String nameReport);
    void deleteReport(String nameReport);
}

package org.arias.reports.streams;

import lombok.AllArgsConstructor;
import org.arias.reports.models.Company;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReportPublisher {

    private final StreamBridge streamBridge;

    /**
     * Topic name-> consumerReport
     * @param report
     */
    public void publishReport(String report) {
        streamBridge.send("consumerReport", report);
        streamBridge.send("consumerReport-in-0", report);
        streamBridge.send("consumerReport-out-0", report);
    }

    /**
     * Topic name-> consumerCompany
     * @param company
     */
    public Company saveReport(String company) {
        streamBridge.send("consumerCompany", company);
        streamBridge.send("consumerCompany-in-0", company);
        streamBridge.send("consumerCompany-out-0", company);
        return new Company();
    }
}

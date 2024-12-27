package org.arias.reportlistener.streams;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arias.reportlistener.documents.ReportDocument;
import org.arias.reportlistener.repositories.ReportRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Configuration
@Slf4j
@AllArgsConstructor
public class ReportListener {

    private final ReportRepository reportRepository;

    /**
     * El nombre de este método debe ser igual al nombre
     * del topic en Kafka. Si el nombre del topic es "consumerReport",
     * el nombre del método debe ser consumerReport. Sin esto
     * no se podrá consumir el mensaje.
     * @return Consumer<String>
     */
    @Bean
    public Consumer<String> consumerReport() {
        return report ->{

            this.reportRepository.save(
                    ReportDocument.builder()
                    .report(report)
                    .build());
            log.info("Saving report: {}", report);
        };
    }
}

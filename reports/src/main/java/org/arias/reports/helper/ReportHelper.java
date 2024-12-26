package org.arias.reports.helper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.arias.reports.models.Company;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Getter
public class ReportHelper {

    @Value("${report.template}")
    private String template;

    public String buildReport(Company company){
        return this.template
                .replace("{company}",company.getName())
                .replace("{foundation_date}",company.getFoundationDate().toString())
                .replace("{founder}",company.getFounder())
                .replace("{web_sites}", company.getWebSites().toString());
    }

    public List<String> getPlacedholders(String name){

        List<String> data = new ArrayList<>();


        while (name.contains("{") && name.contains("}")) {
            int start = name.indexOf("{");
            int end = name.indexOf("}");
            data.add(name.substring(start, end + 1));
            name = name.substring(end + 1);
        }

        return data;


    }

}

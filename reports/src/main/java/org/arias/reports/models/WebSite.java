package org.arias.reports.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class WebSite {

    private Long id;
    private String name;
    private Category category;
    private String description;


}

package org.arias.companiescrudfallback.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Builder
public class WebSite {

    private Long id;
    private String name;
    private Category category;
    private String description;

}

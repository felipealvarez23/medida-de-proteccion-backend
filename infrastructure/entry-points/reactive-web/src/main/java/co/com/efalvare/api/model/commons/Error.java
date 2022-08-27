package co.com.efalvare.api.model.commons;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Error {
    private String title;
    private String code;
    private String description;
}

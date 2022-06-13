package co.com.efalvare.api.model.commons;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApiRequest<T> {
    private T data;
}

package co.com.efalvare.api.model.startrequest;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApiStartRequestData {

    @NotEmpty(message = "El campo type no fue suministrado")
    private String type;

    @NotEmpty(message = "El campo documentType no fue suministrado")
    private String documentType;

    @NotEmpty(message = "El campo documentNumber no fue suministrado")
    private String documentNumber;

    @NotEmpty(message = "El campo contactInfo no fue suministrado")
    private String contactInfo;

}

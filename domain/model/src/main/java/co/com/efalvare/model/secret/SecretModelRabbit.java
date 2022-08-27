package co.com.efalvare.model.secret;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SecretModelRabbit {
    private String username;
    private String password;
}

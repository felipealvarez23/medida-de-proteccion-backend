package co.com.efalvare.model.secret;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DBSecretModel {
    private String password;
    private String dbname;
    private Integer port;
    private String host;
    private String username;
}


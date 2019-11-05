package app.klix.encoder;


import lombok.Data;

@Data
public class JwsValidationResult {
    private boolean isValid;
    private String payload;
}
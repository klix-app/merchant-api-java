package app.klix.callback;

import lombok.Data;

@Data
public class OrderVerificationCallback {
    private String orderId;
    private String externalOrderId;
}

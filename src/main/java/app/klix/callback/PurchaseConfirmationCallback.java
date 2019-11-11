package app.klix.callback;

import app.klix.order.KlixPaymentStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseConfirmationCallback {

    private KlixPaymentStatus status;
    private String orderId;
    private String externalOrderId;

}

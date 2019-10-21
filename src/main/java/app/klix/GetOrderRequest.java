package app.klix;

import app.klix.rest.KlixConnector;
import com.google.common.base.Preconditions;

public class GetOrderRequest {

    private final KlixConnector connector;

    private String merchantId;
    private String orderId;

    public GetOrderRequest(KlixConnector connector) {
        this.connector = connector;
    }

    public GetOrderRequest withMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public GetOrderRequest withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getOrderId() {
        return orderId;
    }

    public GetOrderResponse call() {
        Preconditions.checkNotNull(merchantId);
        Preconditions.checkNotNull(orderId);

        return connector.getOrderDetails(this);
    }

}

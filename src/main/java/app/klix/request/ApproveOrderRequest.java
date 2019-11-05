package app.klix.request;

import app.klix.rest.KlixConnector;
import com.google.common.base.Preconditions;
import lombok.ToString;
import org.apache.http.HttpResponse;

@ToString
public class ApproveOrderRequest {
    private final KlixConnector connector;

    private String merchantId;
    private String orderId;

    public ApproveOrderRequest(KlixConnector connector) {
        this.connector = connector;
    }

    public ApproveOrderRequest withMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public ApproveOrderRequest withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getOrderId() {
        return orderId;
    }

    public HttpResponse call() {
        Preconditions.checkNotNull(merchantId);
        Preconditions.checkNotNull(orderId);

        return connector.approveOrder(this);
    }

}

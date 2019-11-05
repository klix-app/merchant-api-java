package app.klix.request;

import app.klix.order.KlixRejectReason;
import app.klix.rest.KlixConnector;
import com.google.common.base.Preconditions;
import lombok.ToString;
import org.apache.http.HttpResponse;

@ToString
public class RejectOrderRequest {

    private final KlixConnector connector;

    private String merchantId;
    private String orderId;
    private KlixRejectReason reasonCode;

    public RejectOrderRequest(KlixConnector connector) {
        this.connector = connector;
    }

    public RejectOrderRequest withMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public RejectOrderRequest withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public RejectOrderRequest withReasonCode(KlixRejectReason reasonCode) {
        this.reasonCode = reasonCode;
        return this;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getOrderId() {
        return orderId;
    }

    public KlixRejectReason getReasonCode() {
        return reasonCode;
    }

    public HttpResponse call() {
        Preconditions.checkNotNull(merchantId);
        Preconditions.checkNotNull(orderId);
        Preconditions.checkNotNull(reasonCode);

        return connector.rejectOrder(this);
    }

}

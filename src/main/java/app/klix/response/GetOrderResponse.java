package app.klix.response;

import app.klix.order.KlixCompany;
import app.klix.order.KlixCustomer;
import app.klix.order.KlixMerchantUrls;
import app.klix.order.KlixOrderItem;
import app.klix.order.KlixOrderStatus;
import app.klix.order.KlixShipping;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class GetOrderResponse {
    private String id;
    private KlixOrderStatus status;
    private KlixCustomer customer;
    private KlixCompany company;
    private String order_id;
    private BigDecimal amount;
    private BigDecimal tax_amount;
    private BigDecimal total_amount;
    private KlixOrderItem[] items;
    private String currency;
    private KlixMerchantUrls merchant_urls;
    private KlixShipping shipping;

}

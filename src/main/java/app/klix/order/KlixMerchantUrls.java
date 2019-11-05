package app.klix.order;

import lombok.Data;

@Data
public class KlixMerchantUrls {
    private String confirmation;
    private String place_order;
    private String terms;
    private String verification;
}

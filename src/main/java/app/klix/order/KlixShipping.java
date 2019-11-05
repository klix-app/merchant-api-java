package app.klix.order;

import lombok.Data;

@Data
public class KlixShipping {
    private KlixAddress address;
    private String contact_phone;
    private String date;
    private KlixMerchantShippingMethod method;
    private KlixPickupPoint pickup_point;
    private String type;
}

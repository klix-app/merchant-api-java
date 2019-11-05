package app.klix.callback;

import app.klix.order.KlixAddress;
import app.klix.order.KlixOrderItem;
import app.klix.order.KlixPickupPoint;
import lombok.Data;

@Data
public class ShippingCostCalculationCallback {

    private String order_id;
    private KlixOrderItem[] items;
    private String shipping_method_id;
    private KlixAddress address;
    private KlixPickupPoint pickup_point;

    //TODO check if actual structure is different
}

package app.klix.order;

import lombok.Data;

@Data
public class KlixPickupPoint {
    private String comments;
    private String external_id;
    private String name;
    private String service_hours;
}

package app.klix.order;

import lombok.Data;

@Data
public class KlixAddress {
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
    private String postal_code;
    private String street;
}

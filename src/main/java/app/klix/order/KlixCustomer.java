package app.klix.order;

import lombok.Data;

@Data
public class KlixCustomer {
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
}

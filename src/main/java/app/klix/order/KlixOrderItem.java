package app.klix.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KlixOrderItem {
    private BigDecimal amount;
    private String label;
    private BigDecimal tax_amount;
    private BigDecimal total_amount;
    private BigDecimal tax_rate;
    private String order_item_id;
    private BigDecimal quantity;
    private String unit;
    private String type;
    private String reference;
}

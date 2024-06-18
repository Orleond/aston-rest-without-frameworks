package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    private Integer productId;
    private int type;
    private int brandId;
    private int modelId;
    private BigDecimal price;
}

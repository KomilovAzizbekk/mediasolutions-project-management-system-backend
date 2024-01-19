package uz.prod.backcrm.payload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentDTO {

    private UUID id;

    private String type;

    private BigDecimal sum;

    private String repetitionPeriod;

}

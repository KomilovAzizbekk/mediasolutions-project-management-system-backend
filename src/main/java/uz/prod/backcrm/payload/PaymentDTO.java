package uz.prod.backcrm.payload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentDTO {

    private UUID id;

    private String type;

    private Double sum;

    private LocalDateTime repetitionPeriod;

    private List<PaymentHistoryDTO> paymentHistoryDTOS;

}

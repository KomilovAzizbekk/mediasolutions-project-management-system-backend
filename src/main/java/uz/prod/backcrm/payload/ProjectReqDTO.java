package uz.prod.backcrm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.prod.backcrm.payload.ProjectTypeDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectReqDTO {

    private UUID id;

    private String name;

    private String clientName;

    private String clientPhone1;

    private String clientPhone2;

    private String description;

    private BigDecimal price;

    private String dealNumber;

    private LocalDateTime deadline;

    private ProjectTypeDTO type;

}

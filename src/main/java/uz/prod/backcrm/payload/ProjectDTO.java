package uz.prod.backcrm.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDTO {

    private UUID id;

    private String name;

    private StatusDTO statusDTO;

    private ClientDTO clientDTO;

    private String description;

    private Double price;

    private String dealNumber;

    private List<UserDTO> userDTOList;

    private List<TaskDTO> taskDTOList;

    private Timestamp deadline;

    private String type;

    private List<PaymentDTO> paymentDTOList;

}

package uz.prod.backcrm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.prod.backcrm.payload.AttachmentDTO;
import uz.prod.backcrm.payload.StatusDTO;
import uz.prod.backcrm.payload.UserDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResDTO {

    private UUID id;

    private String name;

    private StatusDTO status;

    private List<UserDTO> users;

    private LocalDateTime deadline;

    private List<AttachmentDTO> attachments;

}

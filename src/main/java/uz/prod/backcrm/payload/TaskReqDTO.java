package uz.prod.backcrm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.prod.backcrm.payload.AttachmentDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskReqDTO {

    private UUID id;

    private String name;

    private List<UUID> userIds;

    private StatusDTO status;

    private LocalDateTime deadline;

    private List<AttachmentDTO> attachments;

}

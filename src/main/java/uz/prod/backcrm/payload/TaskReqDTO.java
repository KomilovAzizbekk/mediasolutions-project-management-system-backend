package uz.prod.backcrm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    private String status;

    private LocalDate deadline;

    private List<AttachmentDTO> attachments;

}

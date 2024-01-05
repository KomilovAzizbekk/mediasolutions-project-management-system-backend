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
public class TaskDTO {

    private UUID id;

    private String name;

    private StatusDTO statusDTO;

    private List<UserDTO> responsibleUsers;

    private Timestamp deadline;

    private List<AttachmentDTO> attachmentDTOList;

}

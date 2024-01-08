package uz.prod.backcrm.payload;

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

    private List<UserDTO> userDTOS;

    private Timestamp deadline;

    private List<FileDTO> fileDTOS;

    private Timestamp createdAt;

}

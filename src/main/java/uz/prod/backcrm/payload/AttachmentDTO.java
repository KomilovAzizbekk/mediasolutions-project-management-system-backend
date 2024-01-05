package uz.prod.backcrm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.prod.backcrm.utills.constants.Message;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AttachmentDTO {

    @NotNull(message = Message.ID_NOT_FOUND)

    private UUID id;

    private String name;

    private String url;

    private Long size;
}

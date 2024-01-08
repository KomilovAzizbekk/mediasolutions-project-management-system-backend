package uz.prod.backcrm.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.prod.backcrm.entity.template.AbsUUID;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@Entity(name = "files")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE files SET deleted=true WHERE id=?")
public class Filee extends AbsUUID {

    @Column(nullable = false, name = "file_name")
    private String fileName;

    @Column(nullable = false, name = "size")
    private Integer size;

    @Column(nullable = false, name = "original_name")
    private String originalName;

    //PAPKALAR ICHIDAN FILENI TOPISH UCHUN KERAK BOLADI
    @Column(nullable = false)
    private String path;

}

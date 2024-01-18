package uz.prod.backcrm.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.prod.backcrm.entity.template.AbsLong;
import uz.prod.backcrm.enums.PaymentTypeName;
import uz.prod.backcrm.enums.ProjectTypeName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@Entity(name = "project_type")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE project_type SET deleted=true WHERE id=?")
public class ProjectType extends AbsLong {

    @Column(nullable = false, name = "name")
    @Enumerated(EnumType.STRING)
    private ProjectTypeName name;

}

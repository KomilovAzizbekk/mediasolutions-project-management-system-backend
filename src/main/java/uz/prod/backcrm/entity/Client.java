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
@Entity(name = "client")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE client SET deleted=true WHERE id=?")
public class Client extends AbsUUID {

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber1;

    @Column(name = "extra_phone_number")
    private String phoneNumber2;


}

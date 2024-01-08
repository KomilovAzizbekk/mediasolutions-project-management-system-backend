package uz.prod.backcrm.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.prod.backcrm.entity.template.AbsUUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@Entity(name = "payment_history")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE payment_history SET deleted=true WHERE id=?")
public class PaymentHistory extends AbsUUID {

    @Column(nullable = false, name = "sum")
    private Double sum;

    @Column(nullable = false, name = "date_time")
    private LocalDateTime dateTime;

}

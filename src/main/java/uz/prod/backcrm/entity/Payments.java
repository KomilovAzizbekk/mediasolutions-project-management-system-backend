package uz.prod.backcrm.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.prod.backcrm.entity.template.AbsUUID;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@Entity(name = "payments")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE payments SET deleted=true WHERE id=?")
public class Payments extends AbsUUID {

    @Column(nullable = false, name = "type")
    private String type;

    @Column(nullable = false, name = "sum")
    private Double sum;

    @Column(nullable = false, name = "repetition_period")
    private Timestamp repetitionPeriod;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PaymentHistory> paymentHistories;

}

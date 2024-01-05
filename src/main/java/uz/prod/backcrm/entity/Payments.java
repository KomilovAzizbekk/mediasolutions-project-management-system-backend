package uz.prod.backcrm.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.prod.backcrm.entity.template.AbsUUID;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentType paymentType;

    @Column(nullable = false, name = "sum")
    private Double sum;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PaymentHistory> paymentHistory;

}

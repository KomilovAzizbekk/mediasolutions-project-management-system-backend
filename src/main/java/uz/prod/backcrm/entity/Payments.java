package uz.prod.backcrm.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.prod.backcrm.entity.template.AbsUUID;
import uz.prod.backcrm.enums.PaymentTypeName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private PaymentType type;

    @Column(nullable = false, name = "sum", precision = 20, scale = 2)
    private BigDecimal sum;

    @Column(nullable = false, name = "repetition_period")
    private String repetitionPeriod;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PaymentHistory> paymentHistories;

}

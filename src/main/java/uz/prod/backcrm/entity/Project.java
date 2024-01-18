package uz.prod.backcrm.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.prod.backcrm.entity.template.AbsUUID;

import javax.persistence.*;
import java.math.BigDecimal;
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
@Entity(name = "project")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE project SET deleted=true WHERE id=?")
public class Project extends AbsUUID {

    @Column(nullable = false, name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;

    @Column(nullable = false, name = "client_name")
    private String clientName;

    @Column(nullable = false, name = "client_phone_1")
    private String clientPhone1;

    @Column(name = "client_phone_2")
    private String clientPhone2;

    @Column(name = "description")
    private String description;

    @Column(nullable = false, name = "price", precision = 20, scale = 2)
    private BigDecimal price;

    @Column(name = "deal_number")
    private String dealNumber;

    //PROJECTGA TEGISHLI INSONLAR
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Task> tasks;

    @Column(nullable = false, name = "deadline")
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private ProjectType type;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Payments> payments;

}

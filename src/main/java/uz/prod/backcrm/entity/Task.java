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
@Entity(name = "tasks")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE tasks SET deleted=true WHERE id=?")
public class Task extends AbsUUID {

    @Column(nullable = false, name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;

    //RESPONSIBLE USERS
    @OneToMany(fetch = FetchType.LAZY)
    private List<User> users;

    @Column(name = "deadline")
    private Timestamp deadline;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Filee> files;

}

package fr.asylvan.lifen.planningdegarde.workers.model;

import fr.asylvan.lifen.planningdegarde.shifts.model.ShiftEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class WorkerEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private WorkerStatus status;
    @OneToMany(mappedBy = "worker", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ShiftEntity> shifts;
}

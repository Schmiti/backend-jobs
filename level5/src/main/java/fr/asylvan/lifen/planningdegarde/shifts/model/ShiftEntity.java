package fr.asylvan.lifen.planningdegarde.shifts.model;

import fr.asylvan.lifen.planningdegarde.workers.model.WorkerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShiftEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private WorkerEntity worker;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

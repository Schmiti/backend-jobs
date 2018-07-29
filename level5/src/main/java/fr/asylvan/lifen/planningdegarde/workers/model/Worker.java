package fr.asylvan.lifen.planningdegarde.workers.model;

import fr.asylvan.lifen.planningdegarde.shifts.model.Shift;
import lombok.Data;

import java.util.List;

@Data
public class Worker {
    private Long id;
    private String firstName;
    private String lastName;
    private WorkerStatus status;
    private List<Shift> shifts;
}

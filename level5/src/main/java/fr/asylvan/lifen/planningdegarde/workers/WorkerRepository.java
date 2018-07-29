package fr.asylvan.lifen.planningdegarde.workers;

import fr.asylvan.lifen.planningdegarde.workers.model.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {
}

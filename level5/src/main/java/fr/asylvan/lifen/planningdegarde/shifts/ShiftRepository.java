package fr.asylvan.lifen.planningdegarde.shifts;

import fr.asylvan.lifen.planningdegarde.shifts.model.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Long> {
}

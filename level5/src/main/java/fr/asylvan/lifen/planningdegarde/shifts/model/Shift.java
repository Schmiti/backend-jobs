package fr.asylvan.lifen.planningdegarde.shifts.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Shift {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

package fr.asylvan.lifen.planningdegarde;

import fr.asylvan.lifen.planningdegarde.shifts.ShiftRepository;
import fr.asylvan.lifen.planningdegarde.shifts.model.ShiftEntity;
import fr.asylvan.lifen.planningdegarde.workers.WorkerRepository;
import fr.asylvan.lifen.planningdegarde.workers.model.WorkerEntity;
import fr.asylvan.lifen.planningdegarde.workers.model.WorkerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class PlanningdegardeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanningdegardeApplication.class, args);
    }

    @Component
    class DbInit implements CommandLineRunner {

        private final WorkerRepository workerRepository;
        private final ShiftRepository shiftRepository;

        @Autowired
        DbInit(WorkerRepository workerRepository, ShiftRepository shiftRepository) {
            this.workerRepository = workerRepository;
            this.shiftRepository = shiftRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            WorkerEntity aurelien = new WorkerEntity();
            aurelien.setFirstName("Aurélien");
            aurelien.setLastName("Sylvan");
            aurelien.setStatus(WorkerStatus.intern);
            aurelien.setShifts(
                    Arrays.asList(
                            ShiftEntity.builder()
                                    .startTime(LocalDateTime.parse("2018-07-31T02:00:00"))
                                    .endTime(LocalDateTime.parse("2018-07-31T12:00:00"))
                                    .worker(aurelien)
                                    .build(),
                            ShiftEntity.builder()
                                    .startTime(LocalDateTime.parse("2018-07-31T22:00:00"))
                                    .endTime(LocalDateTime.parse("2018-08-01T10:00:00"))
                                    .worker(aurelien)
                                    .build()
                    )
            );

            WorkerEntity etienne = new WorkerEntity();
            etienne.setFirstName("Etienne");
            etienne.setLastName("Depeaulis");
            etienne.setStatus(WorkerStatus.medic);
            etienne.setShifts(
                    Arrays.asList(
                            ShiftEntity.builder()
                                    .startTime(LocalDateTime.parse("2018-07-31T08:00:00"))
                                    .endTime(LocalDateTime.parse("2018-07-31T18:00:00"))
                                    .worker(etienne)
                                    .build(),
                            ShiftEntity.builder()
                                    .startTime(LocalDateTime.parse("2018-08-01T01:00:00"))
                                    .endTime(LocalDateTime.parse("2018-08-01T10:00:00"))
                                    .worker(etienne)
                                    .build()
                    )
            );

            WorkerEntity drHouse = new WorkerEntity();
            drHouse.setFirstName("Gregory");
            drHouse.setLastName("House");
            drHouse.setStatus(WorkerStatus.medic);
            drHouse.setShifts(
                    Arrays.asList(
                            ShiftEntity.builder()
                                    .startTime(LocalDateTime.parse("2018-07-31T01:30:00"))
                                    .endTime(LocalDateTime.parse("2018-07-31T21:30:00"))
                                    .worker(drHouse)
                                    .build(),
                            ShiftEntity.builder()
                                    .startTime(LocalDateTime.parse("2018-08-01T12:00:00"))
                                    .endTime(LocalDateTime.parse("2018-08-01T19:45:00"))
                                    .worker(drHouse)
                                    .build(),
                            ShiftEntity.builder()
                                    .startTime(LocalDateTime.parse("2018-08-03T12:00:00"))
                                    .endTime(LocalDateTime.parse("2018-08-03T23:00:00"))
                                    .worker(drHouse)
                                    .build()
                    )
            );

            WorkerEntity drQuinn = new WorkerEntity();
            drQuinn.setFirstName("Michaela");
            drQuinn.setLastName("Quinn");
            drQuinn.setStatus(WorkerStatus.medic);
            drQuinn.setShifts(
                    Arrays.asList(
                            ShiftEntity.builder()
                                    .startTime(LocalDateTime.parse("2018-08-02T01:30:00"))
                                    .endTime(LocalDateTime.parse("2018-08-03T00:30:00"))
                                    .worker(drQuinn)
                                    .build()
                    )
            );

            WorkerEntity jd = new WorkerEntity();
            jd.setFirstName("John");
            jd.setLastName("Dorian");
            jd.setStatus(WorkerStatus.intern);
            jd.setShifts(
                    Arrays.asList(
                            ShiftEntity.builder()
                                    .startTime(LocalDateTime.parse("2018-08-02T08:30:00"))
                                    .endTime(LocalDateTime.parse("2018-08-02T19:30:00"))
                                    .worker(jd)
                                    .build(),
                            ShiftEntity.builder()
                                    .startTime(LocalDateTime.parse("2018-08-03T07:15:00"))
                                    .endTime(LocalDateTime.parse("2018-08-03T20:00:00"))
                                    .worker(jd)
                                    .build()
                    )
            );

            workerRepository.saveAll(Arrays.asList(aurelien, etienne, drHouse, drQuinn, jd));
        }
    }
}

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InputData {

    private List<Worker> workers;
    private List<Shift> shifts;

    @Data
    public static class Worker {
        @JsonProperty("id")
        private String id;
        @JsonProperty("status")
        private WorkerStatus status;
    }

    public enum WorkerStatus {
        medic,
        interne,
        interim
    }

    @Data
    public static class Shift {
        @JsonProperty("id")
        private String id;
        @JsonProperty("user_id")
        private String userId;
        @JsonProperty("start_date")
        private LocalDate startDate;
    }
}

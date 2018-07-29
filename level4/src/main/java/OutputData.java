import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputData {

    private List<Worker> workers;
    private Commission commission;

    @Data
    public static class Worker {
        private String id;
        private long price;
    }

    @Data
    public static class Commission {
        @JsonProperty("pdg_fee")
        private double pdgFee;
        @JsonProperty("interim_shifts")
        private int interimShifts;
    }
}

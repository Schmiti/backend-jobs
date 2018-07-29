import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputData {

    private List<Worker> workers;

    @Data
    public static class Worker {
        private String id;
        private long price;
    }
}

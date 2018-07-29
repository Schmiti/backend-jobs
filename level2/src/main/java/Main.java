import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static final Map<InputData.WorkerStatus, Integer> pricePerStatus = new HashMap<>();
    static {
        pricePerStatus.put(InputData.WorkerStatus.medic, 270);
        pricePerStatus.put(InputData.WorkerStatus.interne, 126);
    }

    public static void main(String[] args) throws IOException {
        InputStream stream = Main.class.getResourceAsStream("data.json");
        // So we don't care about the other fields not required for this exercice
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputData data = objectMapper.readValue(stream, InputData.class);

        List<OutputData.Worker> outputWorkers = data.getWorkers().stream()
                .map(worker -> toResultWorker(worker, data))
                .collect(Collectors.toList());

        objectMapper.writeValue(new FileOutputStream("level2/generatedOutput.json"), new OutputData(outputWorkers));
    }

    private static OutputData.Worker toResultWorker(InputData.Worker inputWorker, InputData data) {
        long shiftsNb = data.getShifts().stream()
                .filter(shift -> shift.getUserId().equals(inputWorker.getId()))
                .count();
        long price = shiftsNb * pricePerStatus.get(inputWorker.getStatus());

        OutputData.Worker outputWorker = new OutputData.Worker();
        outputWorker.setId(inputWorker.getId());
        outputWorker.setPrice(price);
        return outputWorker;
    }

}

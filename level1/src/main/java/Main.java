import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        InputStream stream = Main.class.getResourceAsStream("data.json");
        // So we don't care about the other fields not required for this exercice
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputData data = objectMapper.readValue(stream, InputData.class);

        List<OutputData.Worker> outputWorkers = data.getWorkers().stream()
                .map(worker -> toResultWorker(worker, data))
                .collect(Collectors.toList());

        objectMapper.writeValue(new FileOutputStream("level1/generatedOutput.json"), new OutputData(outputWorkers));
    }

    private static OutputData.Worker toResultWorker(InputData.Worker inputWorker, InputData data) {
        long shiftsNb = data.getShifts().stream()
                .filter(shift -> shift.getUserId().equals(inputWorker.getId()))
                .count();
        long price = shiftsNb * inputWorker.getPricePerShift();

        OutputData.Worker outputWorker = new OutputData.Worker();
        outputWorker.setId(inputWorker.getId());
        outputWorker.setPrice(price);
        return outputWorker;
    }

}

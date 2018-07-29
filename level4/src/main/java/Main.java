import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static final Map<InputData.WorkerStatus, Integer> pricePerStatus = new HashMap<>();
    static {
        pricePerStatus.put(InputData.WorkerStatus.medic, 270);
        pricePerStatus.put(InputData.WorkerStatus.interne, 126);
        pricePerStatus.put(InputData.WorkerStatus.interim, 480);
    }

    public static void main(String[] args) throws IOException {
        InputStream stream = Main.class.getResourceAsStream("data.json");
        ObjectMapper objectMapper = getObjectMapper();
        InputData data = objectMapper.readValue(stream, InputData.class);

        List<OutputData.Worker> outputWorkers = data.getWorkers().stream()
                .map(worker -> toResultWorker(worker, data))
                .collect(Collectors.toList());
        OutputData.Commission commission = calculateCommission(data);

        objectMapper.writeValue(new FileOutputStream("level4/generatedOutput.json"), new OutputData(outputWorkers, commission));
    }

    private static OutputData.Worker toResultWorker(InputData.Worker inputWorker, InputData data) {
        long regularShiftNb = data.getShifts().stream()
                .filter(shift -> shift.getUserId() != null)
                .filter(shift -> shift.getUserId().equals(inputWorker.getId()))
                .filter(shift -> shift.getStartDate().getDayOfWeek().getValue() <= 5)
                .count();
        long weekendShiftNb = data.getShifts().stream()
                .filter(shift -> shift.getUserId() != null)
                .filter(shift -> shift.getUserId().equals(inputWorker.getId()))
                .filter(shift -> shift.getStartDate().getDayOfWeek().getValue() > 5)
                .count();
        long price = regularShiftNb * pricePerStatus.get(inputWorker.getStatus())
                + (weekendShiftNb * pricePerStatus.get(inputWorker.getStatus()) * 2);

        OutputData.Worker outputWorker = new OutputData.Worker();
        outputWorker.setId(inputWorker.getId());
        outputWorker.setPrice(price);
        return outputWorker;
    }

    private static OutputData.Commission calculateCommission(InputData data) {
        double shiftFees = data.getShifts().stream()
                .mapToLong(shift -> {
                    InputData.Worker worker = data.getWorkers().stream()
                            .filter(w -> w.getId().equals(shift.getUserId()))
                            .findAny().get();
                    return shiftPrice(shift, worker);
                })
                .mapToDouble(shiftPrice -> shiftPrice * 0.05)
                .sum();

        long interimShiftNb = data.getShifts().stream()
                .filter(shift -> isInterimShift(data, shift))
                .count();
        double pdgFee = shiftFees + (interimShiftNb * 80);


        OutputData.Commission commission = new OutputData.Commission();
        commission.setPdgFee(pdgFee);
        commission.setInterimShifts((int) interimShiftNb);
        return commission;
    }

    private static long shiftPrice(InputData.Shift shift, InputData.Worker worker) {
        boolean isWeekend = shift.getStartDate().getDayOfWeek().getValue() > 5;
        long shiftPrice = pricePerStatus.get(worker.getStatus());
        if (isWeekend) {
            shiftPrice *= 2;
        }
        return shiftPrice;
    }

    private static boolean isInterimShift(InputData data, InputData.Shift shift) {
        return data.getWorkers().stream()
                .filter(worker -> worker.getId().equals(shift.getUserId()))
                .anyMatch(worker -> worker.getStatus().equals(InputData.WorkerStatus.interim));
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // So we don't care about the other fields not required for this exercice
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new CustomLocalDateTimeDeserializer()); // Pattern in file is not really ISO-8601 so I have to implement a custom parser
        objectMapper.registerModule(module);
        return objectMapper;
    }

    private static class CustomLocalDateTimeDeserializer extends StdDeserializer<LocalDate> {

        private CustomLocalDateTimeDeserializer() {
            super(LocalDate.class);
        }
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return LocalDate.parse(p.getValueAsString(), DateTimeFormatter.ofPattern("yyyy-M-d"));
        }

    }
}

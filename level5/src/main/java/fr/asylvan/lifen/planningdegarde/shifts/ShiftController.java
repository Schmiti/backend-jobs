package fr.asylvan.lifen.planningdegarde.shifts;

import fr.asylvan.lifen.planningdegarde.shifts.model.ShiftEntity;
import fr.asylvan.lifen.planningdegarde.workers.WorkerRepository;
import fr.asylvan.lifen.planningdegarde.workers.model.Worker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ShiftController {

    private final WorkerRepository workerRepository;
    private final ShiftRepository shiftRepository;

    @Autowired
    public ShiftController(WorkerRepository workerRepository, ShiftRepository shiftRepository) {
        this.workerRepository = workerRepository;
        this.shiftRepository = shiftRepository;
    }

    @GetMapping("shifts")
    public ModelAndView listShiftsPerWorker() {
        ModelAndView modelAndView = new ModelAndView("shifts");
        ModelMapper modelMapper = new ModelMapper();
        List<Worker> shiftsPerWorker = workerRepository.findAll().stream()
                .map(it -> modelMapper.map(it, Worker.class))
                .collect(Collectors.toList());
        modelAndView.addObject("shiftsPerWorker", shiftsPerWorker);
        return modelAndView;
    }

    @GetMapping("add-shift")
    public ModelAndView addShift(@RequestParam("workerId") Long workerId) {
        ModelAndView modelAndView = new ModelAndView("add-shift");
        ShiftEntity shift = new ShiftEntity();
        workerRepository.findById(workerId).ifPresent(shift::setWorker);
        modelAndView.addObject("shiftForm", shift);
        return modelAndView;
    }

    @PostMapping(value = "workers/{id}/shifts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addShift(@RequestBody ShiftEntity shift, @PathVariable("id") Long workerId) {
        workerRepository.findById(workerId).ifPresent(worker -> {
            shift.setWorker(worker);
            shiftRepository.save(shift);
        });
        return "redirect:/shifts";
    }

    @PutMapping(value = "shifts/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateShift(@PathVariable("id") Long shiftId, @RequestBody ShiftEntity shift) {
        shiftRepository.findById(shiftId).ifPresent(shiftEntity -> {
            shiftEntity.setStartTime(shift.getStartTime());
            shiftEntity.setEndTime(shift.getEndTime());
            shiftRepository.saveAndFlush(shiftEntity);
        });
        return "redirect:/shifts";
    }

    @DeleteMapping("shifts/{shiftId}")
    public String deleteShift(@PathVariable Long shiftId) {
        shiftRepository.deleteById(shiftId);
        return "redirect:/shifts";
    }
}

package fr.asylvan.lifen.planningdegarde.workers;

import fr.asylvan.lifen.planningdegarde.workers.model.WorkerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WorkerController {

    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerController(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @GetMapping("/workers")
    public ModelAndView listWorkers() {
        List<WorkerEntity> all = workerRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("workers");
        modelAndView.addObject("workers", all);
        return modelAndView;
    }

    @GetMapping("/add-worker")
    public ModelAndView addWorker() {
        ModelAndView modelAndView = new ModelAndView("add-worker");
        modelAndView.addObject("workerForm", new WorkerEntity());
        return modelAndView;
    }

    @PostMapping("/workers")
    public String addWorker(@ModelAttribute("workerForm") WorkerEntity worker) {
        workerRepository.save(worker);
        return "redirect:/workers";
    }

    @DeleteMapping("/workers/{id}")
    public String deleteWorker(@PathVariable("id") Long id) {
        workerRepository.deleteById(id);
        return "redirect:/workers";
    }
}

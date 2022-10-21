package np.com.roshanadhikary.testdemo.controller;

import np.com.roshanadhikary.testdemo.entity.Battery;
import np.com.roshanadhikary.testdemo.repository.BatteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class BatteryController {

    private final BatteryRepository repository;

    @Autowired
    public BatteryController(BatteryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("batteries/{id}")
    public Battery getBatteryFromId(@PathVariable int id) {
        Optional<Battery> battery = repository.findById(id);
        battery.orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Battery ID %s not found", id)
        ));

        return battery.get();
    }

    @GetMapping("batteries")
    public List<Battery> getAllBatteries() {
        return repository.findAll();
    }

    @PostMapping("batteries")
    @ResponseStatus(HttpStatus.CREATED)
    public Battery saveBattery(@RequestBody Battery battery) {
        return repository.save(battery);
    }
}

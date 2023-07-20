package lt.gerasimovas.fleetassets.controlers;

import lt.gerasimovas.fleetassets.dto.SimDTO;
import lt.gerasimovas.fleetassets.services.SimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/assets/sims")
public class SimController {

    @Autowired
    private SimService simService;

    @GetMapping
    public ResponseEntity<List<SimDTO>> getAllSims(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(this.simService.getAllDto(pageable));
    }

    @GetMapping()
    @RequestMapping("/{id}")
    public ResponseEntity<SimDTO> getSimById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.simService.getById(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Sim by ID: %s not found", id));
        }
    }

    @PostMapping
    public ResponseEntity<SimDTO> addSim(@RequestBody SimDTO simDTO) {
        return ResponseEntity.ok(this.simService.create(simDTO));
    }

    @PutMapping
    public ResponseEntity<SimDTO> updateSim(@RequestBody SimDTO simDTO) {
        try {
            return ResponseEntity.ok(this.simService.update(simDTO));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Sim!! by ID: %s not found", simDTO.getId()));
        } catch (NoSuchFieldException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                    String.format("This truck License Plate %s, dont exist", simDTO.getTruckLicensePlate()));
                    String.format("This truck License Plate or truckID, dont exist"));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSimById(@PathVariable Long id) {
        try {
            this.simService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Sim by ID: %s not found", id));
        }
    }


}

package lt.gerasimovas.fleetassets.controlers;

import lt.gerasimovas.fleetassets.dto.SimDTO;
import lt.gerasimovas.fleetassets.enumes.Operator;
import lt.gerasimovas.fleetassets.services.SimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<List<SimDTO>> getAllSims(@RequestParam(name = "operator", required = false)
                                                   Operator operator,
                                                   @PageableDefault Pageable pageable) {

        return ResponseEntity.ok(this.simService.getAllDto(pageable, operator));

    }

    @GetMapping()
    @RequestMapping("/{id}")
    public ResponseEntity<SimDTO> getSimById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.simService.getById(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<SimDTO> addSim(@RequestBody SimDTO simDTO) {
        try{
            return ResponseEntity.ok(this.simService.create(simDTO));
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<SimDTO> updateSim(@RequestBody SimDTO simDTO) {
        try {
            return ResponseEntity.ok(this.simService.update(simDTO));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (NoSuchFieldException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSimById(@PathVariable Long id) {
        try {
            this.simService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}

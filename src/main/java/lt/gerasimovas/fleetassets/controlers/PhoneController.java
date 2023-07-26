package lt.gerasimovas.fleetassets.controlers;

import lt.gerasimovas.fleetassets.dto.PhoneDTO;
import lt.gerasimovas.fleetassets.services.PhoneService;
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
@RequestMapping("/assets/phones")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping
    public ResponseEntity<List<PhoneDTO>> getAllPhones(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(this.phoneService.getAllDto(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneDTO> getPhoneById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.phoneService.getById(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Phone by ID: %s not found", id));
        }
    }

    @PostMapping
    public ResponseEntity<PhoneDTO> addPhone(@RequestBody PhoneDTO phoneDTO) {
        return ResponseEntity.ok(this.phoneService.create(phoneDTO));
    }

    @PutMapping
    public ResponseEntity<PhoneDTO> updatePhone(@RequestBody PhoneDTO phoneDTO){
        try {
            return ResponseEntity.ok(this.phoneService.update(phoneDTO));
        } catch (NoSuchFieldException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }



}

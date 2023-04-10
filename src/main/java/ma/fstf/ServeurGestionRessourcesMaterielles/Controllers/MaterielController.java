package ma.fstf.ServeurGestionRessourcesMaterielles.Controllers;

import lombok.AllArgsConstructor;
import ma.fstf.ServeurGestionRessourcesMaterielles.DTO.MaterielDto;
import ma.fstf.ServeurGestionRessourcesMaterielles.Models.Imprimente;
import ma.fstf.ServeurGestionRessourcesMaterielles.Models.Ordinateur;
import ma.fstf.ServeurGestionRessourcesMaterielles.Services.MaterielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin("*")
public class MaterielController {
    @Autowired
    private MaterielService materielService;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/saveOrdinateur")
    public void saveOrdinateur(@RequestBody Ordinateur ordinateur){
        materielService.saveOrdinateur(ordinateur);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/saveImprimante")
    public void saveImprimente(@RequestBody Imprimente imprimente){
       materielService.saveImprimante(imprimente);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getMateriels/{id}")
    public ResponseEntity<List<MaterielDto>> getMateriels(@PathVariable int id) throws Exception {
        return new ResponseEntity<>(materielService.getMateriels(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        materielService.supprimerMaterielOrdinateur(id);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/isAuthenticated")
    public ResponseEntity<String> isAuthenticated(){
        return new ResponseEntity<>("is Authenticated",HttpStatus.ACCEPTED);
    }
}

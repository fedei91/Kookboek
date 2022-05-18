package be.fedei91.kookboek.restcontrollers;

import be.fedei91.kookboek.domain.Recept;
import be.fedei91.kookboek.services.ReceptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recepten")
class ReceptController {
    private final ReceptService receptService;

    ReceptController(ReceptService receptService) {
        this.receptService = receptService;
    }

    @GetMapping("{id}")
    Recept get(@PathVariable long id) {
        return receptService.findById(id).get();
    }
}

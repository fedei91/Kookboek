package be.fedei91.kookboek.restcontrollers;

import be.fedei91.kookboek.services.ReceptService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recepten")
class ReceptController {
    private final ReceptService receptService;

    ReceptController(ReceptService receptService) {
        this.receptService = receptService;
    }


}

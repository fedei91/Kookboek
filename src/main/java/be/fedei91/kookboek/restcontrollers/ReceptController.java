package be.fedei91.kookboek.restcontrollers;

import be.fedei91.kookboek.domain.Recept;
import be.fedei91.kookboek.exceptions.ReceptNietGevondenException;
import be.fedei91.kookboek.services.ReceptService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recepten")
class ReceptController {
    private final ReceptService receptService;

    ReceptController(ReceptService receptService) {
        this.receptService = receptService;
    }

    @GetMapping("{id}")
    Recept get(@PathVariable long id) {
        return receptService.findById(id)
                .orElseThrow(ReceptNietGevondenException::new);
    }

    @ExceptionHandler(ReceptNietGevondenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void receptNietGevonden() {}
}

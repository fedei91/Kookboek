package be.fedei91.kookboek.restcontrollers;

import be.fedei91.kookboek.domain.Recept;
import be.fedei91.kookboek.exceptions.ReceptNietGevondenException;
import be.fedei91.kookboek.services.ReceptService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        receptService.delete(id);
    }

    @PostMapping
    void post(@RequestBody Recept recept) {
        receptService.create(recept);
    }

    @PutMapping("{id}")
    void put(@PathVariable long id, @RequestBody @Valid Recept recept) {
        receptService.update(recept.withId(id));
    }
}

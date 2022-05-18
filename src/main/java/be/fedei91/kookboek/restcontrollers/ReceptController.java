package be.fedei91.kookboek.restcontrollers;

import be.fedei91.kookboek.domain.Recept;
import be.fedei91.kookboek.exceptions.ReceptNietGevondenException;
import be.fedei91.kookboek.services.ReceptService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.TypedEntityLinks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/recepten")
@ExposesResourceFor(Recept.class)
class ReceptController {
    private final ReceptService receptService;
    private final TypedEntityLinks.ExtendedTypedEntityLinks<Recept> links;

    ReceptController(ReceptService receptService, EntityLinks links) {
        this.receptService = receptService;
        this.links = links.forType(Recept.class, Recept::getId);
    }

    @GetMapping("{id}")
    EntityModel<Recept> get(@PathVariable long id) {
        return receptService.findById(id)
                .map(
                        recept -> EntityModel.of(recept,
                                links.linkToItemResource(recept),
                                links.linkForItemResource(recept)
                                        .slash("gebruikers").withRel("gebruikers"))
                )
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
    @ResponseStatus(HttpStatus.CREATED)
    HttpHeaders create(@RequestBody @Valid Recept recept) {
        receptService.create(recept);
        var headers = new HttpHeaders();
        headers.setLocation(links.linkToItemResource(recept).toUri());
        return headers;
    }

    @PutMapping("{id}")
    void put(@PathVariable long id, @RequestBody @Valid Recept recept) {
        receptService.update(recept.withId(id));
    }
}

package be.fedei91.kookboek.restcontrollers;

import be.fedei91.kookboek.domain.Recept;
import be.fedei91.kookboek.exceptions.ReceptNietGevondenException;
import be.fedei91.kookboek.services.ReceptService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.TypedEntityLinks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping
    @Operation(summary = "Alle recepten zoeken")
    CollectionModel<EntityModel<ReceptIdNaam>> findAll() {
        return CollectionModel.of(
                receptService.findAll().stream()
                        .map(recept ->
                                EntityModel.of(new ReceptIdNaam(recept),
                                        links.linkToItemResource(recept)))
                        ::iterator,
                links.linkToCollectionResource());
    }

    @GetMapping("{id}")
    @Operation(summary = "Een recept zoeken op id")
    EntityModel<Recept> get(@PathVariable long id) {
        return receptService.findById(id)
                .map(
                        recept -> EntityModel.of(recept,
                                links.linkToItemResource(recept),
                                links.linkForItemResource(recept)
                                        .slash("ingredienten").withRel("ingredienten"))
                )
                .orElseThrow(ReceptNietGevondenException::new);
    }

    @ExceptionHandler(ReceptNietGevondenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void receptNietGevonden() {}

    @DeleteMapping("{id}")
    @Operation(summary = "Een recept verwijderen")
    void delete(@PathVariable long id) {
        receptService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Een recept toevoegen")
    HttpHeaders create(@RequestBody @Valid Recept recept) {
        receptService.create(recept);
        var headers = new HttpHeaders();
        headers.setLocation(links.linkToItemResource(recept).toUri());
        return headers;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> verkeerdeData(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream().collect(
                        Collectors.toMap(FieldError::getField,
                                FieldError::getDefaultMessage)
                );
    }

    @PutMapping("{id}")
    @Operation(summary = "Een recept wijzigen")
    void put(@PathVariable long id, @RequestBody @Valid Recept recept) {
        receptService.update(recept.withId(id));
    }

    private static class ReceptIdNaam {
        private final long id;
        private final String naam;

        ReceptIdNaam(Recept recept) {
            id = recept.getId();
            naam = recept.getNaam();
        }

        public long getId() {
            return id;
        }

        public String getNaam() {
            return naam;
        }
    }
}

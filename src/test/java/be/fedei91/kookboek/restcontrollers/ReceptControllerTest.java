package be.fedei91.kookboek.restcontrollers;

import be.fedei91.kookboek.services.ReceptService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/insertRecept.sql")
class ReceptControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String RECEPTEN = "recepten";
    private final MockMvc mvc;
    private final ReceptService receptService;

    public ReceptControllerTest(MockMvc mvc, ReceptService receptService) {
        this.mvc = mvc;
        this.receptService = receptService;
    }

    private long idVanTestRecept() {
        return jdbcTemplate.queryForObject(
                "select id from recepten where naam = 'test'", Long.class
        );
    }

    @DisplayName("GET een onbestaand recept")
    @Test
    void onbestaandRecept() throws Exception {
        mvc.perform(get("/recepten/{id}", -1))
                .andExpect(status().isNotFound());
    }

    @DisplayName("GET een bestaand recept")
    @Test
    void receptLezen() throws Exception {
        mvc.perform(get("/recepten/{id}", idVanTestRecept()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(idVanTestRecept()));
    }

    @DisplayName("GET alle recepten")
    @Test
    void alleReceptenLezen() throws Exception {
        mvc.perform(get("/recepten", idVanTestRecept()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.receptIdNaamList", hasSize(countRowsInTable(RECEPTEN))));
    }

    @DisplayName("DELETE een recept")
    @Test
    void eenReceptVerwijderen() throws Exception {
        mvc.perform(delete("/recepten/{id}", idVanTestRecept()))
                .andExpect(status().isOk());
    }

    @DisplayName("DELETE een onbestaand recept")
    @Test
    void eenOnbestaandReceptVerwijderen() throws Exception {
        mvc.perform(delete("/recepten/{id}", -1))
                .andExpect(status().isNotFound());

    }
}

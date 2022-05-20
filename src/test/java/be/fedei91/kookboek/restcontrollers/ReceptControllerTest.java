package be.fedei91.kookboek.restcontrollers;

import be.fedei91.kookboek.domain.Recept;
import be.fedei91.kookboek.services.ReceptService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/insertRecept.sql")
class ReceptControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final MockMvc mvc;
    private final ReceptService receptService;
    private final ReceptController receptController;

    public ReceptControllerTest(MockMvc mvc, ReceptController receptController, ReceptService receptService) {
        this.mvc = mvc;
        this.receptController = receptController;
        this.receptService = receptService;
    }

    private long idVanTestRecept() {
        return jdbcTemplate.queryForObject(
                "select id from recepten where naam = 'test_recept'", Long.class
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
        var id = idVanTestRecept();
        mvc.perform(get("/recepten/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id));
    }
}

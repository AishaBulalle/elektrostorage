package dk.kea.elektrostorage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ComponentControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @Test
    void discontinue_twice_gives_conflict() throws Exception {
        // create component
        var create = Map.of("supplierId", 1, "externalSku", "TEST-DISCONT-1", "orderable", true);
        var res = mvc.perform(post("/components")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(create)))
                .andExpect(status().isOk()).andReturn();

        JsonNode json = om.readTree(res.getResponse().getContentAsString());
        long id = json.get("id").asLong();

        // first discontinue -> OK
        mvc.perform(patch("/components/{id}/discontinue", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discontinued").value(true));

        // second discontinue -> 409 Conflict
        mvc.perform(patch("/components/{id}/discontinue", id))
                .andExpect(status().isConflict());
    }
}

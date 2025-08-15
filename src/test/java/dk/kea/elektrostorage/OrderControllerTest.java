package dk.kea.elektrostorage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @Test
    void finalize_empty_order_is_conflict() throws Exception {
        // create order
        var createOrder = Map.of("supplierId", 1);
        var orderRes = mvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(createOrder)))
                .andExpect(status().isOk()).andReturn();
        long orderId = om.readTree(orderRes.getResponse().getContentAsString()).get("id").asLong();

        // finalize with no lines -> 409
        var fin = Map.of("trackingCode", "TRK-T1", "expectedDate", "2025-08-20");
        mvc.perform(patch("/orders/{id}/finalize", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(fin)))
                .andExpect(status().isConflict());
    }

    @Test
    void add_line_then_finalize_then_add_line_conflict() throws Exception {
        // create order
        var orderRes = mvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(Map.of("supplierId", 1))))
                .andExpect(status().isOk()).andReturn();
        long orderId = om.readTree(orderRes.getResponse().getContentAsString()).get("id").asLong();

        // create component to use
        var compRes = mvc.perform(post("/components")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(Map.of("supplierId", 1, "externalSku", "TEST-LINE-1", "orderable", true))))
                .andExpect(status().isOk()).andReturn();
        long compId = om.readTree(compRes.getResponse().getContentAsString()).get("id").asLong();

        // add line
        mvc.perform(post("/orders/{id}/lines", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(Map.of("componentId", compId, "quantity", 5))))
                .andExpect(status().isOk());

        // finalize
        mvc.perform(patch("/orders/{id}/finalize", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(Map.of("trackingCode", "TRK-T2", "expectedDate", "2025-08-21"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SENT"));

        // try adding line after finalize -> 409
        mvc.perform(post("/orders/{id}/lines", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(Map.of("componentId", compId, "quantity", 1))))
                .andExpect(status().isConflict());
    }
}

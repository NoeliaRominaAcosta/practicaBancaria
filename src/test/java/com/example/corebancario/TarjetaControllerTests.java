package com.example.corebancario;

import com.example.corebancario.controller.TarjetaController;
import com.example.corebancario.dto.SolicitudCompraDTO;
import com.example.corebancario.service.TarjetaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TarjetaController.class)
public class TarjetaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TarjetaService tarjetaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRealizarCompra() throws Exception {
        SolicitudCompraDTO solicitudCompraDTO = new SolicitudCompraDTO(new BigDecimal("100.0"));

        mockMvc.perform(post("/tarjetas/1/compra")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitudCompraDTO)))
                .andExpect(status().isOk());
    }
}

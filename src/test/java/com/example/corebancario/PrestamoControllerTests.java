package com.example.corebancario;

import com.example.corebancario.controller.PrestamoController;
import com.example.corebancario.dto.PagoCuotaDTO;
import com.example.corebancario.dto.SolicitudPrestamoDTO;
import com.example.corebancario.facade.BankingFacadeService;
import com.example.corebancario.model.Prestamo;
import com.example.corebancario.service.PrestamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrestamoController.class)
public class PrestamoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankingFacadeService bankingFacadeService;

    @MockBean
    private PrestamoService prestamoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSolicitarPrestamo() throws Exception {
        SolicitudPrestamoDTO solicitudPrestamoDTO = new SolicitudPrestamoDTO(1L, new BigDecimal("10000"), "PERSONAL");
        Prestamo prestamo = new Prestamo();

        when(bankingFacadeService.solicitarPrestamo(any(SolicitudPrestamoDTO.class))).thenReturn(prestamo);

        mockMvc.perform(post("/prestamos/solicitar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitudPrestamoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPagarCuota() throws Exception {
        PagoCuotaDTO pagoCuotaDTO = new PagoCuotaDTO(new BigDecimal("500"));

        mockMvc.perform(post("/prestamos/1/pagar_cuota")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagoCuotaDTO)))
                .andExpect(status().isOk());
    }
}

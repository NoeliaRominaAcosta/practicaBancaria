package com.example.corebancario;

import com.example.corebancario.controller.CuentaController;
import com.example.corebancario.dto.RespuestaBalanceDTO;
import com.example.corebancario.service.CuentaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CuentaController.class)
public class CuentaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaService cuentaService;

    @Test
    public void testGetBalance() throws Exception {
        RespuestaBalanceDTO respuestaBalanceDTO = new RespuestaBalanceDTO(1000.0);

        when(cuentaService.getBalance(1L)).thenReturn(respuestaBalanceDTO);

        mockMvc.perform(get("/cuentas/1/balance"))
                .andExpect(status().isOk());
    }
}

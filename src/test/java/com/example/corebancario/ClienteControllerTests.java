package com.example.corebancario;

import com.example.corebancario.controller.ClienteController;
import com.example.corebancario.dto.ClienteDTO;
import com.example.corebancario.model.Cliente;
import com.example.corebancario.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateCliente() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO("John Doe", "john.doe@example.com", 600);
        Cliente cliente = new Cliente.Builder()
                .nombre(clienteDTO.nombre())
                .email(clienteDTO.email())
                .scoreCrediticio(clienteDTO.scoreCrediticio())
                .build();

        when(clienteService.createCliente(any(ClienteDTO.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isOk());
    }
}

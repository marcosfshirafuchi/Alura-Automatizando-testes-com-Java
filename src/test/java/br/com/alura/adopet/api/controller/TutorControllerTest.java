package br.com.alura.adopet.api.controller;


import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TutorControllerTest {

    @MockBean
    private TutorService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeCadastrarTutor() throws Exception{
        //ARRANGE
        String json = """
                {
                    "nome": "Rodrigo",
                    "telefone": "(21)0000-9090",
                    "email": "email@example.com.br" 
                }
                """ ;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoCadastrarTutorDadosInvalidos() throws Exception{
        //ARRANGE
        String json = """
                {
                    "nome":"Rodrigo",
                    "telefone":"(21)0000-90900",
                    "email":"email@example.com.br" 
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/tutores")
                        .contentType(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeAtualizarTutor() throws Exception{
        //ARRANGE
        String json = """
                {
                    "id": "1",
                    "nome": "Rodrigo",
                    "telefone":"(21)0000-9090",
                    "email":"email@example.com"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeAtualizarTutor() throws Exception{
        //ARRANGE
        String json = """
                {
                    "id":"2",
                    "nome":"Rodrigo",
                    "telefone":"(21)0000-909000",
                    "email":"email@example.com.br"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                put("/tutores")
                        .contentType(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400QuandoExceptionForLancadaNoCadastrarTutor() throws Exception {
        // ARRANGE
        String json = """
                {
                    "nome": "Rodrigo",
                    "telefone": "(21)0000-9090",
                    "email": "email@example.com.br"
                }
                """;

        // Simulando o lançamento da exceção pelo serviço
        doThrow(new ValidacaoException("Erro na validação do cadastro"))
                .when(service).cadastrar(any(CadastroTutorDto.class));

        // ACT & ASSERT
        mockMvc.perform(
                        post("/tutores")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro na validação do cadastro"));
    }

    @Test
    void deveriaDevolverCodigo400QuandoExceptionForLancadaNoAtualizarTutor() throws Exception{
        //ARRANGE
        String json = """
                {
                    "id":"1",
                    "nome":"Rodrigo",
                    "telefone":"(21)0000-9090",
                    "email":"email@example.com"
                }
                """;

        //Simulando o lançamento da exceção pelo serviço
        doThrow(new ValidacaoException("Erro na validação da atualização"))
                .when(service).atualizar(any(AtualizacaoTutorDto.class));

        //ACT & ASSERT
        mockMvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro na validação da atualização"));
    }
}
package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
import jakarta.validation.ValidationException;
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

//Essa anotação vai ajudar a fazer a injeção de indepencia na classe de test
@SpringBootTest
//Essa anotação AutoConfigureMockMvc vai o Autowired a fazer a injeção de indepencia
@AutoConfigureMockMvc
class AdocaoControllerTest {
    //Vai fazer injeção de independencias
    @Autowired
    //Mock que vai simular as requisições
    private MockMvc mvc;

    //Mock do Spring
    //Usar o service mockado
    //Essa anotação vai fazer configurar o objeto e injeta-lo automáticamente no controller
    @MockBean
    private AdocaoService service;

    @Test
    void deveriaDevolverCodigo400ParaSolicitacaoDeAdocaoComErro() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        //Recebe informações como paramêtro
        var response = mvc.perform(
                //Dispara a requisição post
                post("/adocoes")
                        //Conteudo da requisição
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                //Pega a resposta
                //Retornar a response
        ).andReturn().getResponse();

        //ASSERT
        //Esperado 400 no primeiro campo e retornar o estado do response no segundo campo
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaSolicitacaoDeAdocaoSemErros() throws Exception {
        //ARRANGE
        String json = """
                {
                                "idPet": 1,
                                "idTutor": 1,
                                "motivo": "Motivo qualquer"
                }
                                
                """;

        //ACT
        //Recebe informações como paramêtro
        var response = mvc.perform(
                //Dispara a requisição post
                post("/adocoes")
                        //Conteudo da requisição
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                //Pega a resposta
                //Retornar a response
        ).andReturn().getResponse();

        //ASSERT
        //Esperado 200 no primeiro campo e retornar o estado do response no segundo campo
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeAprovarAdocao() throws Exception{
        //ARRANGE
        String json = """
                {
                    "idAdocao":1
                }
                """;

        //ACT
        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/aprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeAprovarAdocaoInvalida() throws Exception{
        //AARANGE
        String json = """
                {
                
                }
                """;

        //ACT
        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/aprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeReprovarAdocao() throws Exception{
        //ARRANGE
        String json = """
                {
                    "idAdocao":1,
                    "justificativa": "qualquer"
                }
                """;

        //ACT
        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/reprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeReprovarAdocaoInvalida() throws Exception{
        //ARRANGE
        String json = """
                {
                
                }
                """;

        //ACT
        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/reprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400QuandoExceptionForLancadaNoSolicitarAdocao() throws Exception {
        // ARRANGE
        String json = """
                {
                    "idPet": 1,
                    "idTutor": 1,
                    "motivo": "Motivo qualquer"
                }
                """;

        // Simulando o comportamento do service lançando uma exceção de validação
        doThrow(new ValidationException("Erro na validação da adoção"))
                .when(service).solicitar(any(SolicitacaoAdocaoDto.class));

        // ACT & ASSERT
        mvc.perform(
                        post("/adocoes")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro na validação da adoção"));
    }
}
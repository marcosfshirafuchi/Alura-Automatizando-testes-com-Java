package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
        //Esperado 400 no primeiro campo e retornar o estado b=do response no segundo campo
        Assertions.assertEquals(400, response.getStatus());
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
        Assertions.assertEquals(200, response.getStatus());
    }
}
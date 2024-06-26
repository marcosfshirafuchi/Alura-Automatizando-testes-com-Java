package br.com.alura.adopet;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.CalculadoraProbabilidadeAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadoraProbabilidadeAdocaoTest {


    @Test
    @DisplayName("Probabilidade alta para gatos jovens com peso baixo")
    void probabilidadeAltaCenario1() {
        //PADRÃO TRIPLO A (ARRANG, ACT E ASSERTS)
        //idade 4 anos e 4 kg - ALTA
        //ARRANGE - Arrumando a casapara realizar algo
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
                "Abrigo feliz",
                "94999999999",
                "abrigofeliz@email.com.br"
        ));
        Pet pet = new Pet(new CadastroPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                4,
                "Cinza",
                4.0f
        ), abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        //ACT - Realiza uma ação
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        ///Verifica o que a gente espera: Probabilidade Alta  e do método calculadora de probabilidade

        //ASSERT - Verificar se dois valores são iguais
        Assertions.assertEquals(ProbabilidadeAdocao.ALTA,probabilidade);
    }

    @Test
    @DisplayName("Probabilidade média para gatos idosos com peso baixo")
    void probabilidadeMediaCenario1() {
            //idade 15 anos e 4 kg - MEDIA
        //PADRÃO TRIPLO A (ARRANG, ACT E ASSERTS)
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
                "Abrigo feliz",
                "94999999999",
                "abrigofeliz@email.com.br"
        ));
        Pet pet = new Pet(new CadastroPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                15,
                "Cinza",
                4.0f
        ), abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        //ACT - Realiza uma ação
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        ///Verifica o que a gente espera: Probabilidade Media  e do método calculadora de probabilidade
        //ASSERT - Verificar se dois valores são iguais
        Assertions.assertEquals(ProbabilidadeAdocao.MEDIA,probabilidade);
    }
}

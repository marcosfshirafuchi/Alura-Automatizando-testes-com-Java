package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculadoraProbabilidadeAdocaoTest {

    @Test
    void deveriaRetornarProbabilidadeAltaParaPetComIdadeBaixaEPesoBaixo(){
        //PADRÃO TRIPLO A (ARRANG, ACT E ASSERTS)
        //ARRANGE - Arrumando a casa para realizar algo
        //Idade 4 anos e 4 kg - ALTA
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
           "Abrigo feliz",
                "94999999999",
                "abrigofeliz@email.com.br"
        ));

        Pet pet =  new Pet(new CadastroPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                4,
                "Cinza",
                4.0f
        ),abrigo);
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();

        //ACT - Realiza uma ação
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        //ASSERT - Verificar se dois valores são iguais
        //Verifica o que a gente espera: Probabilidade Alta e do método calculadora de probabilidade
        Assertions.assertEquals(ProbabilidadeAdocao.ALTA,probabilidade);
    }

    @Test
    void deveriaRetornarProbabilidadeMediaParaPetComIdadeAltaEPesoBaixo(){
        //PADRÃO TRIPLO A (ARRANG, ACT E ASSERTS)
        //ARRANGE - Arrumando a casa para realizar algo
        //Idade 15 anos e 4 kg - MEDIA
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
                "Abrigo feliz",
                "94999999999",
                "abrigofeliz@email.com"
        ));

        Pet pet = new Pet(new CadastroPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                15,
                "Cinza",
                4.0f
        ),abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();

        //ACT - Realiza uma ação
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        //ASSERT - Verificar se dois valores são iguais
        Assertions.assertEquals(ProbabilidadeAdocao.MEDIA,probabilidade);
    }

    @Test
    void deveriaRetornarProbabilidadeBaixaParaPetComIdadeAltaEPesoAlto(){
        //PADRÃO TRIPLO A (ARRANG, ACT E ASSERTS)
        //ARRANGE - Arrumando a casa para realizar algo
        //Idade 11 anos e 10 kg - BAIXA
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
           "Abrigo feliz",
           "94999999999",
           "abrigofeliz@email.com"
        ));

        Pet pet = new Pet(new CadastroPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                11,
                "Cinza",
                12.0f
        ),abrigo);
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();

        //ACT - Realiza uma ação
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        //ASSERT - Verificar se dois valores são iguais
        //Verificar o que nós esperemos
        Assertions.assertEquals(ProbabilidadeAdocao.BAIXA,probabilidade);
    }

    @Test
    void deveriaRetornarProbabilidadeBaixaParaCachorroComIdadeAltaEPesoAlto(){
        //PADRÃO TRIPLO A (ARRANG, ACT E ASSERTS)
        //ARRANGE - Arrumando a casa para realizar algo
        //Pet cachorro, idade 15 anos e 16 kg - BAIXA
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
                "Abrigo feliz",
                "94999999999",
                "abrigofeliz@email.com.br"
        ));
        Pet pet = new Pet(new CadastroPetDto(
                TipoPet.CACHORRO,
                "Snoop",
                "Spitz alemão",
                15,
                "Branco",
                16.0f
        ),abrigo);
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();

        //ACT - Realiza uma ação
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        //ASSERT - Verificar se dois valores são iguais
        //Verificar o que nós esperemos
        Assertions.assertEquals(ProbabilidadeAdocao.BAIXA,probabilidade);

    }
}
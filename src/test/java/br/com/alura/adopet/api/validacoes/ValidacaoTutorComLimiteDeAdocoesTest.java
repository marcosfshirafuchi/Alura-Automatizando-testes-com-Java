package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//A anotação ExtendWith vai o Junit da classe ValidacaoPetDisponivelTest
// e o Mockito vai interceptar a classe e vai ler os atributos e vai tudo que vai ser mock pelas
// anotações e vai que tem o que esta com InjectMocks, vai new na classe de validação e injetar os Mocks automaticamente.
@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private  ValidacaoTutorComLimiteDeAdocoes validacaoTutorComLimiteDeAdocoes;

    //Declarou o Atributo. Vai transformar em um duble
    @Mock
    private AdocaoRepository adocaoRepository;

    //Declarou o Atributo. Vai transformar em um duble
    @Mock
    private TutorRepository tutorRepository;

    //Declarou o Atributo. Vai transformar em um duble
    @Mock
    private SolicitacaoAdocaoDto dto;

    //Declarou o Atributo. Vai transformar em um duble
    @Mock
    private Tutor tutor;

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoTutorComAdocaoEmAndamento(){
        //Arrange:
        //
        //Definimos um tutorId e configuramos o mock do dto para retornar esse ID quando idTutor() for chamado.
        //Configuramos o mock do tutorRepository para retornar um objeto Tutor mockado quando getReferenceById(tutorId) for chamado.
        //Criamos cinco instâncias de Adocao com o status APROVADO e associadas ao mesmo tutor mockado.
        //Criamos uma lista com essas cinco adoções.
        //Configuramos o mock do adocaoRepository para retornar essa lista quando findAll() for chamado.
        var tutorId = 1L;
        when(dto.idTutor()).thenReturn(tutorId);
        when(tutorRepository.getReferenceById(tutorId)).thenReturn(tutor);

        var adocao1 = new Adocao();
        adocao1.setTutor(tutor);
        adocao1.setStatus(StatusAdocao.APROVADO);

        var adocao2 = new Adocao();
        adocao2.setTutor(tutor);
        adocao2.setStatus(StatusAdocao.APROVADO);

        var adocao3 = new Adocao();
        adocao3.setTutor(tutor);
        adocao3.setStatus(StatusAdocao.APROVADO);

        var adocao4 = new Adocao();
        adocao4.setTutor(tutor);
        adocao4.setStatus(StatusAdocao.APROVADO);

        var adocao5 = new Adocao();
        adocao5.setTutor(tutor);
        adocao5.setStatus(StatusAdocao.APROVADO);

        var adocoes = List.of(adocao1, adocao2, adocao3, adocao4, adocao5);
        when(adocaoRepository.findAll()).thenReturn(adocoes);

        //Act + Assert:
        //
        //Chamamos o método validar() do validador, passando o dto como argumento.
        //Utilizamos assertThrows para verificar se uma exceção do tipo ValidacaoException é lançada, indicando que a validação falhou.
        assertThrows(ValidacaoException.class, ()-> validacaoTutorComLimiteDeAdocoes.validar(dto));
    }

    @Test
    void deveriaPermitirSolicitarDeAdocaoTutorSemadocaoEmAndamento(){
        //Arrange:
        //
        //Definimos um tutorId e configuramos o mock do dto para retornar esse ID quando idTutor() for chamado.
        //Configuramos o mock do tutorRepository para retornar um objeto Tutor mockado quando getReferenceById(tutorId) for chamado.
        //Configuramos o mock do adocaoRepository para retornar uma lista vazia quando findAll() for chamado, simulando um cenário onde o tutor não possui nenhuma adoção em andamento.
        Long tutorId = 1L;
        when(dto.idTutor()).thenReturn(tutorId);
        when(tutorRepository.getReferenceById(tutorId)).thenReturn(tutor);
        when(adocaoRepository.findAll()).thenReturn(Collections.emptyList());

        //Act + Assert:
        //
        //Chamamos o método validar() do validador, passando o dto como argumento.
        //Utilizamos assertDoesNotThrow para verificar se nenhuma exceção é lançada, indicando que a validação passou com sucesso.
        assertDoesNotThrow(() -> validacaoTutorComLimiteDeAdocoes.validar(dto));
    }

    //Observações:
    //
    //É importante garantir que o número de adoções com status APROVADO associadas ao tutor seja igual ou superior a 5 no primeiro teste para que a validação falhe.
    //No segundo teste, podemos utilizar uma lista vazia para simular a ausência de adoções em andamento, mas também poderíamos utilizar uma lista com adoções que não violem a regra de validação (por exemplo, adoções com status diferente de APROVADO ou associadas a outros tutores).
    //Com essas configurações, os testes devem cobrir os dois cenários possíveis para a validação do limite de adoções.
}
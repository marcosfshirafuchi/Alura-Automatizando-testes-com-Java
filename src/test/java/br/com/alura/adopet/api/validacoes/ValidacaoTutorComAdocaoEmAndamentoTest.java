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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//Mockito intercept essa classe
@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    //Declarou o validador como sendo um atributo. O AdocaoRepository vai ser injetado automaticamente no ValidacaoTutorComAdocaoEmAndamento
    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacaoTutorComAdocaoEmAndamento;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private Tutor tutor;

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoTutorComAdocaoEmAndamento(){
        // Arrange:
        //Criamos um objeto Adocao com status AGUARDANDO_AVALIACAO e o tutor mockado.
        //Configuramos o adocaoRepository para retornar uma lista contendo essa Adocao.
        //Configuramos o dto para retornar um ID de tutor e o tutorRepository para retornar o tutor mockado quando chamado com esse ID.
        var tutorId = 1L;
        var adocao = new Adocao();
        adocao.setTutor(tutor);
        adocao.setStatus(StatusAdocao.AGUARDANDO_AVALIACAO);
        var adocoes = Collections.singletonList(adocao);

        when(dto.idTutor()).thenReturn(tutorId);
        when(tutorRepository.getReferenceById(tutorId)).thenReturn(tutor);
        when(adocaoRepository.findAll()).thenReturn(adocoes);

        //Act + Assert:
        //Usamos assertThrows para verificar se ValidacaoException é lançada ao chamar validador.validar(dto).

        assertThrows(ValidacaoException.class, () -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoTutorSemAdocaoEmAndamento(){
        //Arrange:
        //Configuramos o adocaoRepository para retornar uma lista vazia, simulando que não há adoções em andamento.
        //Configuramos o dto para retornar um ID de tutor e o tutorRepository para retornar o tutor mockado.
        var tutorId = 1L;


        when(dto.idTutor()).thenReturn(tutorId);
        when(tutorRepository.getReferenceById(tutorId)).thenReturn(tutor);
        when(adocaoRepository.findAll()).thenReturn(Collections.emptyList());

        //Act + Assert:
        //Usamos assertDoesNotThrow para verificar se nenhuma exceção é lançada ao chamar validador.validar(dto).
        assertDoesNotThrow(() -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }
}
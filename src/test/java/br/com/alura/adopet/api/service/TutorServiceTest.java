package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


//Mockito intercept essa classe
@ExtendWith(MockitoExtension.class)
class TutorServiceTest {
    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository repository;

    @Mock
    private CadastroTutorDto dto;

    @Mock
    private Tutor tutor;

    @Mock
    private AtualizacaoTutorDto atualizacaoTutorDto;

    @Test
    void NaoDeveriaCasdastrarTutorTelefoneOuEmailCadastrado(){
        //PADRÃO TRIPLO A (ARRANG, ACT E ASSERTS)
        //Arrange (Arrumando a casa para realizar algo) + Act (Realiza uma ação)
        given(repository.existsByTelefoneOrEmail(dto.telefone(),dto.email())).willReturn(true);

        //Assert
        //Usamos assertThrows para verificar se ValidacaoException é lançadado ao chamar service.cadastrar(dto)
        assertThrows(ValidacaoException.class,() -> service.cadastrar(dto));
    }

    @Test
    void deveriaCadastrarTutor(){
        //PADRÃO TRIPLO A (ARRANG, ACT E ASSERTS)
        //Arrange (Arrumando a casa para realizar algo)
        given(repository.existsByTelefoneOrEmail(dto.telefone(),dto.email())).willReturn(false);

        //Act (Realiza uma ação) + Assert (Usamos assertDoesNotThrow para verificar se nenhuma exceção é lançada ao chamar service.cadastrar(dto)
        assertDoesNotThrow(() -> service.cadastrar(dto));
        then(repository).should().save(new Tutor(dto));
    }

    @Test
    void deveriaAtualizarDadosTutor(){
        //PADRÃO TRIPLO A (ARRANG, ACT E ASSERTS)
        //Arrange (Arrumando a casa para realizar algo)
        given(repository.getReferenceById(atualizacaoTutorDto.id())).willReturn(tutor);

        //Act (Realiza uma ação)
        service.atualizar(atualizacaoTutorDto);

        //Assert
        then(tutor).should().atualizarDados(atualizacaoTutorDto);
    }
}
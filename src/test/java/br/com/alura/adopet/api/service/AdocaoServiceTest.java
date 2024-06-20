package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

//O mockito vai fazer a injeção automatica
@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {
    //Classe principal que vai ser injeta os mocks e que vai testada
    @InjectMocks
    private AdocaoService service;

    @Mock
    private AdocaoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

    @Mock
    private ValidacaoSolicitacaoAdocao validador1;

    @Mock
    private ValidacaoSolicitacaoAdocao validador2;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;


    private SolicitacaoAdocaoDto dto;

    //O Captor vai capturar a adocao
    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    //Vai ter checar os métodos validações.forEach, repository.save e emailService.enviarEmail
    @Test
    void deveriaSalvarAdocaoAoSolicitar(){
        //ARRANGE
        //This.dto vai receber os parametros para ser instanciados para depois passar o dto para os given
        this.dto = new SolicitacaoAdocaoDto(10L, 20l,"motivo qualquer");
        //Devolve o pet
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        //Devolve o tutor
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);


        //ACT
        service.solicitar(dto);

        //ASSERT
        //Verifica o repository foi chamado e foi salvo com qualquer paramêtro
        //then(repository).should().save(any());

        //ASSERT

        //Capturar o objeto adocaoCaptor que foi chamado
        then(repository).should().save(adocaoCaptor.capture());
        //Ele vai passar o objeto que foi passado no repository
        Adocao adocaoSalva = adocaoCaptor.getValue();
        //Verifica se o meu pet(mock) é o mesmo da adoção que foi salvo
        Assertions.assertEquals(pet,adocaoSalva.getPet());
        Assertions.assertEquals(tutor,adocaoSalva.getTutor());
        Assertions.assertEquals(dto.motivo(),adocaoSalva.getMotivo());

    }

    @Test
    void deveriaChamarValidadoresDeAdocaoAoSolicitar(){
        //ARRANGE
        //This.dto vai receber os parametros para ser instanciados para depois passar o dto para os given
        this.dto = new SolicitacaoAdocaoDto(10L, 20l,"motivo qualquer");
        //Devolve o pet
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        //Devolve o tutor
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        validacoes.add(validador1);
        validacoes.add(validador2);

        //ACT
        service.solicitar(dto);

        //ASSERT
        //Verifica o repository foi chamado e foi salvo com qualquer paramêtro
        //then(repository).should().save(any());

        //ASSERT
        //Ve se os dois validadores teve o método validado passando como parametro dto
        BDDMockito.then(validador1).should().validar(dto);
        BDDMockito.then(validador2).should().validar(dto);
    }
}
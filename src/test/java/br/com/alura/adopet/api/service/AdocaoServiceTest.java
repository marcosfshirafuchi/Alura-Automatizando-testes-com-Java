package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Mock
    private AprovacaoAdocaoDto aprovacaoAdocaoDto;

    @Mock
    private ReprovacaoAdocaoDto reprovacaoAdocaoDto;

    @Spy
    private Adocao adocao;

    //Vai ter que checar os métodos validações.forEach, repository.save e emailService.enviarEmail
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
        assertEquals(pet,adocaoSalva.getPet());
        assertEquals(tutor,adocaoSalva.getTutor());
        assertEquals(dto.motivo(),adocaoSalva.getMotivo());

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
    @Test
    void deveriaEnviarEmailAoSolicitarAdocao() {

        //ARRANGE
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(10l, 30l, "motivo teste");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        //ACT
        service.solicitar(dto);

        //ASSERT
        then(repository).should().save(adocaoCaptor.capture());
        Adocao adocao = adocaoCaptor.getValue();
        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " +adocao.getPet().getAbrigo().getNome() +"!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação."
        );
    }

    @Test
    void deveriaAprovarUmaAdocao(){
        //Arrange
        given(repository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(abrigo.getEmail()).willReturn("email@example.com");
        given(adocao.getTutor()).willReturn(tutor);
        given(tutor.getNome()).willReturn("Rodrigo");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        //Act
        service.aprovar(aprovacaoAdocaoDto);

        //Assert
        then(adocao).should().marcarComoAprovada();
        assertEquals(StatusAdocao.APROVADO,adocao.getStatus()
        );
    }

    @Test
    void deveriaEnviarEmailAoAprovarUmaAdocao(){
        //Arrange
        given(repository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(abrigo.getEmail()).willReturn("email@example.com");
        given(adocao.getTutor()).willReturn(tutor);
        given(tutor.getNome()).willReturn("Rodrigo");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        //Act
        service.aprovar(aprovacaoAdocaoDto);

        //Assert
        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Adoção aprovada","Parabéns " +adocao.getTutor().getNome() + "!\n\nSua adoção do pet "
                        + adocao.getPet().getNome() + ", solicitada em "
                        + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                        + ", foi aprovada.\nFavor entrar em contato com o abrigo "
                        + adocao.getPet().getAbrigo().getNome()
                        + " para agendar a busca do seu pet."
        );
    }

    @Test
    void deveriaReprovarUmaAdocao(){
        //Arrange
        given(repository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(abrigo.getEmail()).willReturn("email@example.com");
        given(adocao.getTutor()).willReturn(tutor);
        given(tutor.getNome()).willReturn("Rodrigo");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        //Act
        service.reprovar(reprovacaoAdocaoDto);

        //Assert
        then(adocao).should().marcarComoReprovada(reprovacaoAdocaoDto.justificativa());
        assertEquals(StatusAdocao.REPROVADO, adocao.getStatus());
    }

    @Test
    void deveriaEnviarEmailAoReprovarUmaAdocao(){
        //Arrange
        given(repository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(adocao.getTutor()).willReturn(tutor);
        given(tutor.getNome()).willReturn("Rodrigo");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        //Act
        service.reprovar(reprovacaoAdocaoDto);

        //Assert
        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " + adocao.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet "
                + adocao.getPet().getNome() +", solicitada em "
                + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                + ", foi reprovada pelo abrigo "
                + adocao.getPet().getAbrigo().getNome()
                + " com a seguinte justificativa: "
                + adocao.getJustificativaStatus()
        );
    }
}
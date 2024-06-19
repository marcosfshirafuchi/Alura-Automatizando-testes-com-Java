package br.com.alura.adopet;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoPetDisponivel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
//Mockito intercept essa classe
@ExtendWith(MockitoExtension.class)
public class ValidacaoPetDisponivelTest {
    //Declarou o validador como sendo um atributo. O PetRepository vai ser injetado automaticamente no ValidacaoPetDisponivel
    @InjectMocks
    private ValidacaoPetDisponivel validacao;

    //Declarou o Atributo. Vai transformar em um duble
    @Mock
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void deveriaPermitirSolicitacaoAdocaoPet(){


        //ARRANGE
//        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(
//                7l,
//                2l,
//                "Motivo qualquer"
//        );
        //ARRANGE
        //Chama o método do petRepository, com a variavel dto.idPet()  e da a resposta pet do método
        BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        //Vai false porque o pet não foi adotado
        BDDMockito.given(pet.getAdotado()).willReturn(false);

        //ACT
        //validacao.validar(dto);

        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void naoDeveriaPermitirSolicitacaoAdocaoPet(){


        //ARRANGE
//        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(
//                7l,
//                2l,
//                "Motivo qualquer"
//        );
        //ARRANGE
        //Chama o método do petRepository, com a variavel dto.idPet()  e da a resposta pet do método
        BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        //Vai true porque o pet foi adotado
        BDDMockito.given(pet.getAdotado()).willReturn(true);

        //ACT
        //validacao.validar(dto);

        //ASSERT + ACT
        //O primeiro parametrô(ValidacaoException.class) é o que espero receber
        //O segundo parametrô é de onde você espera receber
        Assertions.assertThrows(ValidacaoException.class,() -> validacao.validar(dto));
    }
}

package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


//Mockito intercept essa classe
@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {

    //Declarou o validador como sendo um atributo. O AdocaoRepository vai ser injetado automaticamente no ValidacaoPetComAdocaoEmAndamento
    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validacaoPetComAdocaoEmAndamento;

    //Declarou os Atributos. Vai transformar em um duble
    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoDePetComPedidoEmAndamento(){
        //Arrange
        //Given(Dado o cenário o adocaoRepository vai chamar o método existsByPetIdAndStatus()) e retornar true para não permitir a adocação de um pet em pedido de andamento
        BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        //Act + Assert
        Assertions.assertThrows(ValidacaoException.class, () ->validacaoPetComAdocaoEmAndamento.validar(dto));
    }

    @Test
    void DeveriaPermitirSolicitacaoDeAdocaoDePetComPedidoInexistente(){
        //Arrange
        //Given(Dado o cenário o adocaoRepository vai chamar o método existsByPetIdAndStatus()) e retornar false para  permitir a adocação de um pet em pedido inexistente
        BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(
                dto.idPet(),
                StatusAdocao.AGUARDANDO_AVALIACAO
        )).willReturn(false);

        //Act + Assert
        Assertions.assertDoesNotThrow(() -> validacaoPetComAdocaoEmAndamento.validar(dto));
    }
}
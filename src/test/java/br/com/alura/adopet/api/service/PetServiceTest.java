package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.then;

//Mockito intercept essa classe
@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    @InjectMocks
    private PetService service;

    @Mock
    private CadastroPetDto cadastroPetDto;

    @Mock
    private PetRepository repository;

    @Mock
    private Abrigo abrigo;

    @Test
    void deveriaCadastrarPet(){
        //PADRÃO TRIPLO A (ARRANG, ACT E ASSERTS)
        //ACT - Realiza uma ação
        service.cadastrarPet(abrigo,cadastroPetDto);

        //ASSERT
        then(repository).should().save(new Pet(cadastroPetDto,abrigo));
    }

    @Test
    void deveriaRetornarTodosOsPetsDisponiveis(){
        //PADRÃO TRIPLO A (ARRANG, ACT E ASSERTS)
        //ACT - Realiza uma ação
        service.buscarPetsDisponiveis();

        //ASSERT
        then(repository).should().findAllByAdotadoFalse();
    }

}
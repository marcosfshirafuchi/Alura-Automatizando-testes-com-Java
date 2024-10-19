package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {
    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository repository;

    @Mock
    private Abrigo abrigo;

    @Mock
    private PetRepository petRepository;

    @Test
    void deveriaChamarListaDeTodosOsAbrigos(){
        //Act
        service.listar();

        //Assert
        then(repository).should().findAll();
    }

    @Test
    void deveriaChamarListaDePetsDoAbrigoAtravesDoNome(){
        //Arrange
        String nome = "Miau";
        given(repository.findByNome(nome)).willReturn(Optional.of(abrigo));

        //Act
        service.listarPetsDoAbrigo(nome);

        //Assert
        then(petRepository).should().findByAbrigo(abrigo);
    }

    @Test
    void deveriaCadastrarAbrigoComSucesso(){
        //Arrange
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo Teste","123456789","abrigo@teste.com");
        //Simula que não existe nenhum abrigo com o nome, telefone ou e-mail já cadastrados
        given(repository.existsByNomeOrTelefoneOrEmail(dto.nome(),dto.telefone(),dto.email())).willReturn(false);

        //Act
        service.cadastrar(dto);

        //Assert
        //Verifica se o método save foi chamado
        then(repository).should().save(any(Abrigo.class));
    }

    @Test
    void deveriaLancarExcecaoQuandoAbrigoJaCadastrado(){
        //Arrange
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo Teste","123456789","abrigo@teste.com");

        //Simula que o abrigo já existe
        given(repository.existsByNomeOrTelefoneOrEmail(dto.nome(),dto.telefone(),dto.email())).willReturn(true);

        //Act & Assert
        //Verifica se a exceção ValidacaoException é lançada
        ValidacaoException exception = assertThrows(ValidacaoException.class, ()->service.cadastrar(dto));

        //Verifica a mensagem da exceção
        assertEquals("Dados já cadastrados para outro abrigo!", exception.getMessage());

        //Verifica que o método save não foi chamado, pois o abrigo já esta cadastrado
        then(repository).should(never()).save(any(Abrigo.class));
    }

    @Test
    void deveriaCarregarAbrigoPorId(){
        //Arrange
        Long id = 1L;
        given(repository.findById(id)).willReturn(Optional.of(abrigo));

        //Act
        Abrigo resultado = service.carregarAbrigo(String.valueOf(id));

        //Assert
        assertNotNull(resultado);
        then(repository).should().findById(id);
    }

    @Test
    void deveriaCarregarAbrigoPorNome(){
        //Arrange
        String nome = "Abrigo Teste";
        given(repository.findByNome(nome)).willReturn(Optional.of(abrigo));

        //Act
        Abrigo resultado = service.carregarAbrigo(nome);

        //Assert
        assertNotNull(resultado);
        then(repository).should().findByNome(nome);
    }

    @Test
    void deveriaLancarExcecaoQuandoAbrigoNaoEncontradoPorIdOuNome(){
        //Arrange
        String idOuNome = "Abrigo Inexistente";
        given(repository.findByNome(idOuNome)).willReturn(Optional.empty());

        //Act & Assert
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> service.carregarAbrigo(idOuNome));

        //Verifica a mensagem da exceçãp
        assertEquals("Abrigo não encontrado", exception.getMessage());
    }
}
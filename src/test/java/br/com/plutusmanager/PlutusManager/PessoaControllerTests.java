import br.com.plutusmanager.PlutusManager.controller.PessoaController;
import br.com.plutusmanager.PlutusManager.entities.Pessoa;
import br.com.plutusmanager.PlutusManager.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {PessoaControllerTests.TestConfig.class})
public class PessoaControllerTests {

    @Configuration
    @ComponentScan(basePackages = "br.com.plutusmanager.PlutusManager")
    static class TestConfig {
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private PessoaController pessoaController;

    @MockBean
    private PessoaService pessoaService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testFindAll_deveRetornarListaDeUsuarios_quandoExistemUsuarios() {
        List<Pessoa> pessoasMockadas = new ArrayList<>();
        pessoasMockadas.add(new Pessoa());
        pessoasMockadas.add(new Pessoa());

        Mockito.when(pessoaService.findAll()).thenReturn(pessoasMockadas);

        ResponseEntity<List<Pessoa>> respostaEntity = pessoaController.findAll();

        assertEquals(HttpStatus.OK, respostaEntity.getStatusCode());
        assertEquals(pessoasMockadas, respostaEntity.getBody());
    }

    @Test
    public void testFindAll_deveRetornarListaVazia_quandoNaoExistemUsuarios() {
        List<Pessoa> listaVazia = Collections.emptyList();

        Mockito.when(pessoaService.findAll()).thenReturn(listaVazia);

        ResponseEntity<List<Pessoa>> respostaEntity = pessoaController.findAll();

        assertEquals(HttpStatus.OK, respostaEntity.getStatusCode());
        assertTrue(respostaEntity.getBody().isEmpty());
    }

    @Test
    public void testFindById_deveRetornarUsuario_quandoExiste() {
        UUID id = UUID.randomUUID();
        Pessoa pessoaMockada = new Pessoa();
        pessoaMockada.setPessoaId(id);

        Mockito.when(pessoaService.findById(id)).thenReturn(Optional.of(pessoaMockada));

        ResponseEntity<Pessoa> respostaEntity = pessoaController.findById(id);

        assertEquals(HttpStatus.OK, respostaEntity.getStatusCode());
        assertEquals(pessoaMockada, respostaEntity.getBody());
    }

    @Test
    public void testFindById_deveRetornarNotFound_quandoUsuarioNaoEncontrado() {
        UUID id = UUID.randomUUID();

        Mockito.when(pessoaService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Pessoa> respostaEntity = pessoaController.findById(id);

        assertEquals(HttpStatus.NOT_FOUND, respostaEntity.getStatusCode());
        assertNull(respostaEntity.getBody());
    }

    @Test
    public void testCreate_deveCriarUsuario_eRetornarStatusCreated() {
        Pessoa novaPessoa = new Pessoa();

        Mockito.when(pessoaService.save(novaPessoa)).thenReturn(novaPessoa);

        ResponseEntity<Pessoa> respostaEntity = pessoaController.create(novaPessoa);

        assertEquals(HttpStatus.CREATED, respostaEntity.getStatusCode());
        assertEquals(novaPessoa, respostaEntity.getBody());
    }

    @Test
    public void testUpdate_deveAtualizarUsuario_eRetornarUsuarioAtualizado_quandoUsuarioExiste() {
        UUID id = UUID.randomUUID();
        Pessoa pessoaMockada = new Pessoa();
        pessoaMockada.setPessoaId(id);

        Mockito.when(pessoaService.findById(id)).thenReturn(Optional.of(pessoaMockada));
        Mockito.when(pessoaService.save(pessoaMockada)).thenReturn(pessoaMockada);

        Pessoa pessoaParaAtualizar = new Pessoa();
        pessoaParaAtualizar.setNome("Nome Atualizado");

        ResponseEntity<Pessoa> respostaEntity = pessoaController.update(id, pessoaParaAtualizar);

        assertEquals(HttpStatus.OK, respostaEntity.getStatusCode());
        assertEquals("Nome Atualizado", respostaEntity.getBody().getNome());
    }

    @Test
    public void testUpdate_deveRetornarNotFound_quandoUsuarioNaoExiste() throws Exception {
        UUID id = UUID.randomUUID();
        Pessoa pessoaDetails = new Pessoa();
        pessoaDetails.setNome("Nome Atualizado");
        pessoaDetails.setEmail("email@exemplo.com");

        Mockito.when(pessoaService.update(Mockito.eq(id), Mockito.any(Pessoa.class)))
                .thenThrow(new RuntimeException("Id da pessoa não encontrado: " + id));

        mockMvc.perform(put("/api/pessoas/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Nome Atualizado\", \"email\": \"email@exemplo.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete_deveRemoverUsuario_eRetornarNoContent_quandoUsuarioExiste() {
        UUID id = UUID.randomUUID();

        Mockito.when(pessoaService.findById(id)).thenReturn(Optional.of(new Pessoa()));

        ResponseEntity<Void> respostaEntity = pessoaController.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, respostaEntity.getStatusCode());

        Mockito.verify(pessoaService).deleteById(id);
    }
}

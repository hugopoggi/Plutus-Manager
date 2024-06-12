import br.com.plutusmanager.PlutusManager.controller.UsuarioController;
import br.com.plutusmanager.PlutusManager.entities.Usuario;
import br.com.plutusmanager.PlutusManager.service.UsuarioService;
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
@ContextConfiguration(classes = {UsuarioControllerTests.TestConfig.class})
public class UsuarioControllerTests {

    @Configuration
    @ComponentScan(basePackages = "br.com.plutusmanager.PlutusManager")
    static class TestConfig {
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private UsuarioController usuarioController;

    @MockBean
    private UsuarioService usuarioService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testFindAll_deveRetornarListaDeUsuarios_quandoExistemUsuarios() {
        List<Usuario> usuariosMockados = new ArrayList<>();
        usuariosMockados.add(new Usuario());
        usuariosMockados.add(new Usuario());

        Mockito.when(usuarioService.findAll()).thenReturn(usuariosMockados);

        ResponseEntity<List<Usuario>> respostaEntity = usuarioController.findAll();

        assertEquals(HttpStatus.OK, respostaEntity.getStatusCode());
        assertEquals(usuariosMockados, respostaEntity.getBody());
    }

    @Test
    public void testFindAll_deveRetornarListaVazia_quandoNaoExistemUsuarios() {
        List<Usuario> listaVazia = Collections.emptyList();

        Mockito.when(usuarioService.findAll()).thenReturn(listaVazia);

        ResponseEntity<List<Usuario>> respostaEntity = usuarioController.findAll();

        assertEquals(HttpStatus.OK, respostaEntity.getStatusCode());
        assertTrue(respostaEntity.getBody().isEmpty());
    }

    @Test
    public void testFindById_deveRetornarUsuario_quandoExiste() {
        Long id = Long.valueOf(1L);
        Usuario usuarioMockado = new Usuario();
        usuarioMockado.setUsuarioId(id);

        Mockito.when(usuarioService.findById(id)).thenReturn(Optional.of(usuarioMockado));

        ResponseEntity<Usuario> respostaEntity = usuarioController.findById(id);

        assertEquals(HttpStatus.OK, respostaEntity.getStatusCode());
        assertEquals(usuarioMockado, respostaEntity.getBody());
    }

    @Test
    public void testFindById_deveRetornarNotFound_quandoUsuarioNaoEncontrado() {
        Long id = Long.valueOf(1L);

        Mockito.when(usuarioService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> respostaEntity = usuarioController.findById(id);

        assertEquals(HttpStatus.NOT_FOUND, respostaEntity.getStatusCode());
        assertNull(respostaEntity.getBody());
    }

    @Test
    public void testCreate_deveCriarUsuario_eRetornarStatusCreated() {
        Usuario novoUsuario = new Usuario();

        Mockito.when(usuarioService.save(novoUsuario)).thenReturn(novoUsuario);

        ResponseEntity<Usuario> respostaEntity = usuarioController.create(novoUsuario);

        assertEquals(HttpStatus.CREATED, respostaEntity.getStatusCode());
        assertEquals(novoUsuario, respostaEntity.getBody());
    }

    @Test
    public void testUpdate_deveAtualizarUsuario_eRetornarUsuarioAtualizado_quandoUsuarioExiste() {
        Long id = Long.valueOf(1L);
        Usuario usuarioMockado = new Usuario();
        usuarioMockado.setUsuarioId(id);
        usuarioMockado.setNomeUsuario("Nome Antigo");
        usuarioMockado.setTelefone("123456789");
        usuarioMockado.setEmail("antigo@example.com");

        // Mock para o usuário que será atualizado
        Usuario usuarioParaAtualizar = new Usuario();
        usuarioParaAtualizar.setNomeUsuario("Nome Atualizado");
        usuarioParaAtualizar.setTelefone("987654321");
        usuarioParaAtualizar.setEmail("atualizado@example.com");

        // Mock do serviço para retornar o usuário atualizado
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setUsuarioId(id);
        usuarioAtualizado.setNomeUsuario("Nome Atualizado");
        usuarioAtualizado.setTelefone("987654321");
        usuarioAtualizado.setEmail("atualizado@example.com");

        Mockito.when(usuarioService.update(id, usuarioParaAtualizar)).thenReturn(usuarioAtualizado);

        ResponseEntity<Usuario> respostaEntity = usuarioController.update(id, usuarioParaAtualizar);

        assertEquals(HttpStatus.OK, respostaEntity.getStatusCode());
        assertNotNull(respostaEntity.getBody());
        assertEquals("Nome Atualizado", respostaEntity.getBody().getNomeUsuario());
        assertEquals("987654321", respostaEntity.getBody().getTelefone());
        assertEquals("atualizado@example.com", respostaEntity.getBody().getEmail());
    }

    @Test
    public void testUpdate_deveRetornarNotFound_quandoUsuarioNaoExiste() throws Exception {
        Long id = Long.valueOf(1L);
        Usuario usuarioDetails = new Usuario();
        usuarioDetails.setNomeUsuario("Nome Atualizado");
        usuarioDetails.setEmail("email@exemplo.com");

        Mockito.when(usuarioService.update(Mockito.eq(id), Mockito.any(Usuario.class)))
                .thenThrow(new RuntimeException("Id do usuario não encontrado: " + id));

        mockMvc.perform(put("/api/usuarios/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomeUsuario\": \"Nome Atualizado\", \"email\": \"email@exemplo.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete_deveRemoverUsuario_eRetornarNoContent_quandoUsuarioExiste() {
        Long id = Long.valueOf(1L);

        Mockito.when(usuarioService.findById(id)).thenReturn(Optional.of(new Usuario()));

        ResponseEntity<Void> respostaEntity = usuarioController.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, respostaEntity.getStatusCode());

        Mockito.verify(usuarioService).deleteById(id);
    }
}

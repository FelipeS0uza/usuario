//Camada CONTROLLER recebe as requisições HTTP, mapeia os endpoints, chama a Service para usar os metodos
// e retorna respostas HTTP conforme o necessário.
// Não deve conter lógica de negócio (cálculos, validações complexas, regras de banco)

package com.felipesouza.usuario.controller;

import com.felipesouza.usuario.business.UsuarioService;
import com.felipesouza.usuario.business.dto.EnderecoDTO;
import com.felipesouza.usuario.business.dto.TelefoneDTO;
import com.felipesouza.usuario.business.dto.UsuarioDTO;
import com.felipesouza.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController     //Indica para o spring que essa classe é o controlador e que vai lidar com as requisições (Padrão REST)
@RequestMapping("/usuario")     //Responsável por apontar qual é a URI da controller
@RequiredArgsConstructor        //Gera um construtor que inicializa apenas os campos PRIVATE FINAL
public class UsuarioController {

    //Injeção de dependências
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    //ResponseEntity<> é uma classe que indica que o metodo vai retornar uma resposta HTTP do tipo que estiver dentro de <>
    //RequestBody indica que estou passando um objeto no corpo da requisição
    //RequestParam indica que estou passando um parametro no corpo da requisição
    //RequestHeader indica que receberá no Header, na chave Authorization o valor do token


    @PostMapping    //Indica que o metodo é um POST
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        //Caso estaja tudo ok, então o usuario é salvo no banco de dados
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")      //Indica que é um metodo POST na url /login
    //No corpo da requisição foi enviado uma classe DTO para filtrar os dados enviados do Usuario, visando segurança
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        //Authentication é uma classe do security que faz a autenticação do usuário e cria um objeto com os dados autenticados
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        //Caso esteja tudo ok, retorna um token
        return "Bearer " +  jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping   //Indica que o metodo é um GET
    //Busca os dados somente do email informado
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email ) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")      //Indica que é um metodo DELETE e que receberá o email na url /{email para deletar}
    //PathVariable indica que será extraido valores da url, nesse caso o email
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping     //Indica que é um metodo PUT
    //Vai receber os dados da dto(dados atualizados) no corpo da requisição
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO dto,
                                                           @RequestHeader("Authorization") String token) {
        //Caso esteja tudo ok, então salva os dados atualizados do usuario no bando de dados
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }

    @PutMapping("/endereco")     //Indica que é um metodo PUT, na url /endereco
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestParam ("id") Long id) {
        //Caso esteja tudo ok, então salva os dados atualizados do endereco no bando de dados
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
    }

    @PutMapping("/telefone")     //Indica que é um metodo PUT, na url /telefone
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestParam ("id") Long id) {
        //Caso esteja tudo ok, então salva os dados atualizados do telefone no bando de dados
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }

    @PostMapping("/endereco")     //Indica que é um metodo POST, na url /endereco
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestHeader ("Authorization") String token) {
        //Caso esteja tudo ok, salva o novo endereço no usuario cadastrado
        return ResponseEntity.ok(usuarioService.cadastroEndereco(token, dto));
    }

    @PostMapping("/telefone")     //Indica que é um metodo POST, na url /telefone
    public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestHeader ("Authorization") String token) {
        //Caso esteja tudo ok, salva o novo telefone no usuario cadastrado
        return ResponseEntity.ok(usuarioService.cadastroTelefone(token, dto));
    }
}
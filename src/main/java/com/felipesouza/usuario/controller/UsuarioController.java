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

@RestController     //Indica que essa classe é o controlador e que vai lidar com as requisições (Padrão REST)
@RequestMapping("/usuario")     //Responsável por apontar qual é a URI da controller
@RequiredArgsConstructor        //Gera um construtor que inicializa apenas os campos PRIVATE FINAL
public class UsuarioController {

    //Injeção de dependências
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping    //Indica que o metodo é um POST
    //ResponseEntity é uma classe que indica que o metodo vai retornar uma resposta HTTP
    //RequestBody indica que estou passando o objeto Usuario no corpo da requisição
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        //Caso estaja tudo ok, então o usuario é salvo no banco de dados
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")      //Indica que é um metodo POST na url /login
    //No corpo da requisição foi enviado uma classe DTO para filtrar os dados enviados do Usuario, visando segurança
    //RequestBody indica que estou passando o objeto UsuarioDTO no corpo da requisição
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        //Authentication é uma classe do security que faz a autenticação do usuário e cria um objeto com os dados autenticados
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        //Caso esteja tudo ok, retorna um token
        return "Bearer " +  jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping   //Indica que o metodo é um GET
    //RequestParam indica que estou passando um parametro no corpo da url, nesse caso o email
    //Busca os dados somente do email informado
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email ) {
        //Caso esteja tudo ok, retorna os dados do usuario solicitado
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")     //Indica que é um metodo DELETE e que receberá o email na url /{email para deletar}
    //PathVariable indica que será extraido valores da url, nesse caso o email
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email) {
        //É feito o delete do usuario no bando de dados através do email
        usuarioService.deletarUsuarioPorEmail(email);

        //Retorna um ok
        return ResponseEntity.ok().build();
    }

    @PutMapping     //Indica que é um metodo PUT
    //Vai receber os dados da dto(dados atualizados) no corpo da requisição
    //e receberá no Header o token, declarado explicitamente pois precisaremos utilizar na Controler para extrair o email.
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO dto,
                                                           @RequestHeader("Authorization") String token) {
        //Caso esteja tudo ok, então salva os dados atualizados do usuario no bando de dados
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }

    @PutMapping("/endereco")     //Indica que é um metodo PUT na url /endereco
    //ResponseEntity é uma classe que indica que o metodo vai retornar uma resposta HTTP
    //RequestBody indica que estou passando o objeto EnderecoDTO no corpo da requisição
    //RequestParam indica que estou passando um parametro no corpo da url, nesse caso o id do endereco
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestParam ("id") Long id) {
        //Faz a atualização do endereço e retorna um ok
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
    }

    @PutMapping("/telefone")     //Indica que é um metodo PUT /telefone
    //ResponseEntity é uma classe que indica que o metodo vai retornar uma resposta HTTP
    //RequestBody indica que estou passando o objeto TelefoneDTO no corpo da requisição
    //RequestParam indica que estou passando um parametro no corpo da url, nesse caso o id do telefone
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestParam ("id") Long id) {
        //Faz a atualização do telefone e retorna um ok
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }

    @PostMapping("/endereco")     //Indica que é um metodo POST na url /endereco
    //ResponseEntity é uma classe que indica que o metodo vai retornar uma resposta HTTP
    //RequestBody indica que estou passando o objeto EnderecoDTO no corpo da requisição
    //RequestHeader indica que receberá no Header na chave Authorization o valor do token
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestHeader ("Authorization") String token) {
        //Cadastra o novo endereço e retorna um ok
        return ResponseEntity.ok(usuarioService.cadastroEndereco(token, dto));
    }

    @PostMapping("/telefone")     //Indica que é um metodo POST na url /telefone
    //ResponseEntity é uma classe que indica que o metodo vai retornar uma resposta HTTP
    //RequestBody indica que estou passando o objeto TelefoneDTO no corpo da requisição
    //RequestHeader indica que receberá no Header na chave Authorization o valor do token
    public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestHeader ("Authorization") String token) {
        //Cadastra o novo telefone e recebe um ok
        return ResponseEntity.ok(usuarioService.cadastroTelefone(token, dto));
    }
}
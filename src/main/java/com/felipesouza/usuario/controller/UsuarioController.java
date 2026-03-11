package com.felipesouza.usuario.controller;

import com.felipesouza.usuario.business.UsuarioService;
import com.felipesouza.usuario.business.dto.UsuarioDTO;
import com.felipesouza.usuario.infrastructure.entity.Usuario;
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

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping    //Indica que o metodo é um POST
    //ResponseEntity é uma classe que indica que o metodo vai retornar uma resposta HTTP
    //RequestBody indica que estou passando o objeto Usuario no corpo da requisição
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
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
    //RequestParam indica que estou passando um parametro no corpo da requisição, nesse caso o email
    public ResponseEntity<Usuario> buscaUsuarioPorEmail(@RequestParam("email") String email ) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }
}
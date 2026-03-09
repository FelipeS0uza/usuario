package com.felipesouza.usuario.controller;

import com.felipesouza.usuario.business.UsuarioService;
import com.felipesouza.usuario.business.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController     //Indica para o spring que essa classe é o controlador e que vai lidar com as requisições (Padrão REST)
@RequestMapping("/usuario")     //Responsável por apontar qual é a URI da controller
@RequiredArgsConstructor        //Gera um construtor que inicializa apenas os campos PRIVATE FINAL
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping    //Indica que o metodo é um POST
    //ResponseEntity é uma classe que indica que o metodo vai retornar uma resposta HTTP
    //RequestBody indica que estou passando o objeto Usuario no corpo da requisição
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }
}
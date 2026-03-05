package com.felipesouza.usuario.business;

import com.felipesouza.usuario.business.converter.UsuarioConverter;
import com.felipesouza.usuario.business.dto.UsuarioDTO;
import com.felipesouza.usuario.infrastructure.entity.Usuario;
import com.felipesouza.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }


}

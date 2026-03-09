package com.felipesouza.usuario.business;

import com.felipesouza.usuario.business.converter.UsuarioConverter;
import com.felipesouza.usuario.business.dto.UsuarioDTO;
import com.felipesouza.usuario.infrastructure.entity.Usuario;
import com.felipesouza.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service    //Indica ao spring que é uma Service(onde se desenvolve as regras de negócio, camada que recebe as requisições do controller)
@RequiredArgsConstructor    //Gera um construtor que inicializa apenas os campos PRIVATE FINAL
public class UsuarioService {

    //Injeção de dependências
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    //Criação do metodo para salvar usuário, usando DTO para não expor os dados do usuário
    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {

        //Converte os dados de DTO para entidade usuario e armazena na instância usuario.
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        //Salva o usuário no banco de dados, e o retorno é convertido de volta para dto.
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
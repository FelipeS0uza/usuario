package com.felipesouza.usuario.business;

import com.felipesouza.usuario.business.converter.UsuarioConverter;
import com.felipesouza.usuario.business.dto.UsuarioDTO;
import com.felipesouza.usuario.infrastructure.entity.Usuario;
import com.felipesouza.usuario.infrastructure.exceptions.ConflictException;
import com.felipesouza.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.felipesouza.usuario.infrastructure.repository.UsuarioRepository;
import com.felipesouza.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service    //Indica ao spring que é uma Service(onde se desenvolve as regras de negócio, camada que recebe as requisições do controller)
@RequiredArgsConstructor    //Gera um construtor que inicializa apenas os campos PRIVATE FINAL
public class UsuarioService {

    //Injeção de dependências
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //Criação do metodo para salvar usuário, usando DTO para não expor os dados do usuário
    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        //Converte os dados de DTO para entidade usuario e armazena na instância usuario.
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        //Salva o usuário no banco de dados, e o retorno é convertido de volta para dto.
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    //Metodo de verificação de email existente que reaproveita o metodo verificaEmailExistente e
    // gera uma Conflict Exception, que foi criada nas exceptions, e que trata de conflito de dados
    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email); //Verifica se o email existe
            if (existe) {   //Se o email existe(true), é lançado uma exceção com uma mensagem
                throw new ConflictException("Email já cadastrado: " + email);
            }
        } catch (ConflictException e) {     //É feito a captura da exceção e lançado uma mensagem com a causa da exceção
            throw new ConflictException("Email já cadastrado: ", e.getCause());
        }
    }

    //Metodo que apenas chama o metodo do UsuarioRepository e verifica se o email existe
    //Feito separado para que possa ser reutilizado a verificação em outros contextos
    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);  //Existe=true   Não Existe=false
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                "Email não encontrado " + email
        ));
    }

    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String email = jwtUtil.extractUsername(token.substring(7));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado"));

        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
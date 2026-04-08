//Camada SERVICE é onde se desenvolve as regras de negócio e toda a lógica e recebe as requisições do controller
//Intermedia a comunicação entre o Controller e o Repository

package com.felipesouza.usuario.business;

import com.felipesouza.usuario.business.converter.UsuarioConverter;
import com.felipesouza.usuario.business.dto.EnderecoDTO;
import com.felipesouza.usuario.business.dto.TelefoneDTO;
import com.felipesouza.usuario.business.dto.UsuarioDTO;
import com.felipesouza.usuario.infrastructure.entity.Endereco;
import com.felipesouza.usuario.infrastructure.entity.Telefone;
import com.felipesouza.usuario.infrastructure.entity.Usuario;
import com.felipesouza.usuario.infrastructure.exceptions.ConflictException;
import com.felipesouza.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.felipesouza.usuario.infrastructure.repository.EnderecoRepository;
import com.felipesouza.usuario.infrastructure.repository.TelefoneRepository;
import com.felipesouza.usuario.infrastructure.repository.UsuarioRepository;
import com.felipesouza.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service    //Indica ao spring que é uma Service
@RequiredArgsConstructor    //Gera um construtor que inicializa apenas os campos PRIVATE FINAL
public class UsuarioService {

    //Injeção de dependências
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    //Metodo que busca o usuario pelo email
    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            //Busca o usuario no BD e já converte para DTO
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email)
                            //Caso não encontre lança um erro
                            .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email)));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    //Metodo que deleta o usuario pelo email
    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    //Metodo que atualiza os dados do usuario autenticado somente
    //Recebe o token de autenticação e extrai o email dele
    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        //Exclui a palavra Bearer , extrai o token JWT e a partir dele extrai o email
        String email = jwtUtil.extractUsername(token.substring(7));

        //Caso a senha seja informada para atualizar, então será encriptografada novamente, caso contrário nada será feito
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        //Busca os dados do usuario no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                //Erro caso o email não estaja cadastrado, difícil de ocorrer pois o email é extraido do token autenticado
                //Mas como na repository usamos um Optional, é obrigatório a criação de exceção de erro
                new ResourceNotFoundException("Email não localizado"));

        //Salva os dados já atualizados em uma variavel, utilizando o update implementado no converter
        //recebendo como parametro o dto para os novos dados e usuarioEntity que são os dados atuais no banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        //Salva os dados do usuario convertido e depois converte o retorno para UsuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    //Metodo que atualiza os dados do endereço
    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {

        //Busca o endereço no banco de dados
        Endereco enderecoEntity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                //Caso não encontre lança um erro
                new ResourceNotFoundException("Id não encontrado " + idEndereco));

        //Salva os dados já atualizados em uma variavel, utilizando o update implementado no converter
        //recebendo como parametro o dto para os novos dados e enderecoEntity que são os dados atuais no banco de dados
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, enderecoEntity);

        //Salva os dados do endereço convertido e depois converte o retorno para EnderecoDTO
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    //Metodo que atualiza os dados do telefone
    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {

        //Busca o telefone no banco de dados
        Telefone telefoneEntity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                //Caso não encontre lança um erro
                new ResourceNotFoundException("Id não localizado " + idTelefone));

        //Salva os dados já atualizados em uma variavel, utilizando o update implementado no converter
        //recebendo como parametro o dto para os novos dados e telefoneEntity que são os dados atuais no banco de dados
        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, telefoneEntity);

        //Salvou os dados do telefone convertido e depois converte o retorno para TelefoneDTO
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    //Metodo que cadastra um novo endereço
    public EnderecoDTO cadastroEndereco(String token, EnderecoDTO dto) {
        //Exclui a palavra Bearer , extrai o token JWT e a partir dele extrai o email
        String email = jwtUtil.extractUsername(token.substring(7));

        //Busca o usuario no banco de dados
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado " + email));

        //Converte o novo endereço para uma entidade para que possa inserir no banco de dados
        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());

        //Salva o novo endereço no cadastro do usuario autenticado e converte o retorno para dto
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    //Metodo que cadastra um novo telefone
    public TelefoneDTO cadastroTelefone(String token, TelefoneDTO dto) {
        //Exclui a palavra Bearer , extrai o token JWT e a partir dele extrai o email
        String email = jwtUtil.extractUsername(token.substring(7));

        //Busca o usuario no banco de dados
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado " + email));

        //Converte o novo telefone para uma entidade para que possa inserir no banco de dados
        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());

        //Salva o novo telefone no cadastro do usuario autenticado e converte o retorno para dto
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
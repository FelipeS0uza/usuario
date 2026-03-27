package com.felipesouza.usuario.business.converter;

import com.felipesouza.usuario.business.dto.EnderecoDTO;
import com.felipesouza.usuario.business.dto.TelefoneDTO;
import com.felipesouza.usuario.business.dto.UsuarioDTO;
import com.felipesouza.usuario.infrastructure.entity.Endereco;
import com.felipesouza.usuario.infrastructure.entity.Telefone;
import com.felipesouza.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component  //Indica que é um componente a mais porém não possui regras de negócio
public class UsuarioConverter {

    //Converte os dados para entidade Usuario recebendo o usuarioDto como parâmetro
    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()    //Essa forma de instanciar é devido a anotação @Builder
                //É passado os dados do usuarioDTO para setar nos dados do usuario entidade
                .nome(usuarioDTO.getNome())
                .senha(usuarioDTO.getSenha())
                //Passa a lista de endereçosDtos para converter com os metodos paraEndereço e paraListaEndereços
                // e setar na lista de endereços da entidade usuario
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                //Passa a lista de telefonesDtos para converter com os metodos paraTelefone e paraListaTelefones
                // e setar na lista de telefones da entidade usuario
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();
    }

    //Cria a lista de entidades endereço, recebendo cada endereçoDto e convertendo cada um com
    //o metodo criado paraEndereço e adicionando a lista de endereços convertidos.
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecosDTOS){
        return enderecosDTOS.stream().map(this::paraEndereco).toList();
    }

    //Converte os dados de um ÚNICO endereço para entidade Endereço recebendo o endereçoDto como parâmetro
    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                //É passado todos os dados do enderecoDTO para setar nos dados do endereco entidade
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    //Cria a lista de entidades telefone, recebendo cada telefoneDto e convertendo cada um com
    //o metodo criado paraTelefone e adicionando a lista de telefones convertidos.
    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOS){
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    //Converte os dados de um ÚNICO telefone para entidade Telefone recebendo o telefoneDto como parâmetro
    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                //É passado todos os dados do telefoneDTO para setar nos dados do telefone entidade
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }


    //ABAIXO É AO CONTRÁRIO, SERÁ FEITO A CONVERSÃO DOS DADOS ENTIDADE PARA DTO RECEBENDO ENTIDADE COMO PARAMETRO
    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuario.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecosDTOS){
        return enderecosDTOS.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefoneDTOS){
        return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }


    //Aqui será passado os dados para atualizar, porém foi usado o operador ternário em nome, email e senha para que
    //não seja necessário informar todos os dados novamente, somente o que será alterado
    public Usuario updateUsuario(UsuarioDTO usuario, Usuario entity) {
        return Usuario.builder()
                //Se for informado nome para ser alterado, então enviará o novo dado,
                //mas se não for informado nada(null) então busca o nome que já possui cadastrado na entity.
                .nome(usuario.getNome() != null ? usuario.getNome() : entity.getNome())
                .email(usuario.getEmail() != null ? usuario.getEmail() : entity.getEmail())
                .senha(usuario.getSenha() != null ? usuario.getSenha() : entity.getSenha())
                .id(entity.getId())  //No id não foi usado operador ternário pois o id não pode ser alterado
                .enderecos(entity.getEnderecos())   //Por se tratar de um relacionamento, será implementado de outra forma
                .telefones(entity.getTelefones())   //Por se tratar de um relacionamento, será implementado de outra forma
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity) {
        return Endereco.builder()
                //Se for informado nome para ser alterado, então enviará o novo dado,
                //mas se não for informado nada(null) então busca o nome que já possui cadastrado na entity.
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .id(entity.getId())  //No id não foi usado operador ternário pois o id não pode ser alterado
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity) {
        return Telefone.builder()
                //Se for informado nome para ser alterado, então enviará o novo dado,
                //mas se não for informado nada(null) então busca o nome que já possui cadastrado na entity.
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .id(entity.getId())  //No id não foi usado operador ternário pois o id não pode ser alterado
                .build();
    }


    //Metodo para o cadastro de novos endereços
    //Recebe o novo endereco como enderecoDTO no parametro e o idUsuario para atualizar no usuario autenticado
    //Como o novo endereco vem na forma de dto, é feito a conversão para entidade para inserir no banco de dados
    public Endereco paraEnderecoEntity(EnderecoDTO dto, Long idUsuario){
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .usuario_id(idUsuario)
                .build();
    }

    //Metodo para o cadastro de novos telefones
    //Recebe o novo telefone como telefoneDTO no parametro e o idUsuario para atualizar no usuario autenticado
    //Como o novo telfone vem na forma de dto, é feito a conversão para entidade para inserir no banco de dados
    public Telefone paraTelefoneEntity(TelefoneDTO dto, Long idUsuario){
        return Telefone.builder()
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .usuario_id(idUsuario)
                .build();
    }
}
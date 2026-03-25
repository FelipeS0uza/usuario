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
                .nome(usuarioDTO.getNome())     //Passar o nome do usuarioDto para setar no nome da entidade usuário
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                //Passa a lista de endereçosDtos para converter com os metodos paraEndereço e paraListaEndereços
                // e setar na lista de endereços da entidade usuario
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
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
                .rua(enderecoDTO.getRua())      //Passa a rua do endereçoDto para setar na rua da entidade endereço
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
                .numero(telefoneDTO.getNumero())    //Passa o numero do telefoneDto para setar no numero da entidade telefone
                .ddd(telefoneDTO.getDdd())
                .build();
    }


    //ABAIXO É EXATAMENTE O CONTRÁRIO, SERÁ FEITO A CONVERSÃO DOS DADOS ENTIDADE PARA DTO
    public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO){
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuarioDTO.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecosDTOS){
        return enderecosDTOS.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco enderecoDTO){
        return EnderecoDTO.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefoneDTOS){
        return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO){
        return TelefoneDTO.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    //Aqui será passado os dados para atualizar, porém foi usado o operador ternário em nome, email e senha para que
    //não seja necessário informar todos os dados novamente, somente o que será alterado
    public Usuario updateUsuario(UsuarioDTO usuario, Usuario entity) {
        return Usuario.builder()
                //Se for informado nome para ser alterado, então enviará o novo dado,
                //mas se não for informado nada(null) então busca o nome que já possui cadastrado na entity.
                .nome(usuario.getNome() != null ? usuario.getNome() : entity.getNome())
                .id(entity.getId())  //No id não foi usado operador ternário pois o id não pode ser alterado
                .email(usuario.getEmail() != null ? usuario.getEmail() : entity.getEmail())
                .senha(usuario.getSenha() != null ? usuario.getSenha() : entity.getSenha())
                .enderecos(entity.getEnderecos())   //Por se tratar de um relacionamento, será implementado de outra forma
                .telefones(entity.getTelefones())   //Por se tratar de um relacionamento, será implementado de outra forma
                .build();
    }
}
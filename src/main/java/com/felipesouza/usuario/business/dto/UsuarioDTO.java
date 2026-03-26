/* DTO - Data Transfer Object
É um modelo de projeto usado para transportar dados, evitando passar dados sensíveis para outro serviço. */

package com.felipesouza.usuario.business.dto;

import lombok.*;
import java.util.List;

@Getter //Cria todos os getters
@Setter //Cria todos os setters
@AllArgsConstructor //  Cria o construtor recebendo todos os argumentos
@NoArgsConstructor  //Cria o construtor não recebendo nenhum argumento
@Builder    //Simplifica a instância de classes fornecendo os parâmetros na ordem desejada e de uma só vez.
public class UsuarioDTO {

    //Não possui o id pois é uma informação privada, da qual não faz sentido expor.
    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDTO> enderecos;
    private List <TelefoneDTO> telefones;
}
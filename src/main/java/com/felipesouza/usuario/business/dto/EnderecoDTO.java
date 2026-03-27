/* DTO - Data Transfer Object
É um modelo de projeto usado para transportar dados, evitando passar dados sensíveis para outro serviço. */

package com.felipesouza.usuario.business.dto;

import lombok.*;

@Getter     //Cria todos os getters
@Setter     //Cria todos os setters
@AllArgsConstructor //Cria o construtor recebendo todos os argumentos
@NoArgsConstructor  //Cria o construtor não recebendo nenhum argumento
@Builder    //Simplifica a instância de classes fornecendo os parâmetros na ordem desejada e de uma só vez.
public class EnderecoDTO {

    //Diferente do UsuarioDTO, aqui passamos o id para o cadastro de novos enderecos após o cadastro do usuario
    private Long id;
    private String rua;
    private Long numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;
}
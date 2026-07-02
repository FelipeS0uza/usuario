package com.felipesouza.usuario.infrastructure.client;

import lombok.*;

@Getter //Cria todos os getters
@Setter //Cria todos os setters
@AllArgsConstructor //  Cria o construtor recebendo todos os argumentos
@NoArgsConstructor  //Cria o construtor não recebendo nenhum argumento
@Builder    //Simplifica a instância de classes fornecendo os parâmetros na ordem desejada e de uma só vez.
public class ViaCepDTO {

    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
}

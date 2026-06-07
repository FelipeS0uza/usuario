package com.felipesouza.usuario.infrastructure.client;

import lombok.*;

@Getter //Cria todos os getters
@Setter //Cria todos os setters
@AllArgsConstructor //  Cria o construtor recebendo todos os argumentos
@NoArgsConstructor  //Cria o construtor não recebendo nenhum argumento
@Builder    //Simplifica a instância de classes fornecendo os parâmetros na ordem desejada e de uma só vez.
public class ViaCepDTO {

    public String cep;
    public String logradouro;
    public String complemento;
    public String unidade;
    public String bairro;
    public String localidade;
    public String uf;
    public String estado;
    public String regiao;
    public String ibge;
    public String gia;
    public String ddd;
    public String siafi;
}

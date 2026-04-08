//Camada ENTITY é onde representa a estrutura de dados da aplicação
// Define as tabelas do banco de dados mapeadas para classes Java

package com.felipesouza.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter     //Cria todos dos getters
@Setter     //Cria todos os setter
@AllArgsConstructor //Cria o construtor recebendo todos os argumentos
@NoArgsConstructor  //Cria o construtor não recebendo nenhum argumento
@Entity     //Aponta para o spring que é uma tabela do banco de dados
@Table(name = "telefone")    //Indica o nome da tabela, caso não indique usa como default o nome da classe
@Builder     //Simplifica a instância de classes fornecendo os parâmetros na ordem desejada e de uma só vez.
public class Telefone {

    @Id     //Identificador único
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera automaticamente os id's
    private Long id;
    //Identifica o nome da coluna e quantidade de caracteres, entre outras informações
    @Column(name = "numero", length = 10)
    private String numero;
    @Column(name = "ddd", length = 3)
    private String ddd;
    @Column(name = "usuario_id")
    private Long usuario_id;
}
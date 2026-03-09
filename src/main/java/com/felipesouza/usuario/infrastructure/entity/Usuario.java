package com.felipesouza.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Getter     //Cria todos dos getters
@Setter     //Cria todos os setter
@AllArgsConstructor //Cria o construtor recebendo todos os argumentos
@NoArgsConstructor  //Cria o construtor não recebendo nenhum argumento
@Entity     //Aponta para o spring que é uma tabela do banco de dados
@Table(name = "usuario")    //Indica o nome da tabela, caso não indique usa como default o nome da classe
@Builder     //Simplifica a instância de classes fornecendo os parâmetros na ordem desejada e de uma só vez.
//UserDetails é uma classe do spring security, para que o Usuario seja validado como usuario de login
public class Usuario implements UserDetails {

    @Id     //Identificador único
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera automaticamente os id's
    private Long id;
    //Identifica o nome da coluna e quantidade de caracteres, entre outras informações
    @Column(name = "nome", length = 100)
    private String nome;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "senha")
    private String senha;

    //Um usuario para muitos endereços, e faz o relacionamento em cascata (quando o usuario é excluido, tanto o telefone quanto os endereços são excluidos automaticamente)
    @OneToMany(cascade = CascadeType.ALL)
    //Indica o nome da coluna(usuarios_id) na tabela endereços que faz referencia a coluna ID na tabela usuarios
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Endereco> enderecos;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Telefone> telefones;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
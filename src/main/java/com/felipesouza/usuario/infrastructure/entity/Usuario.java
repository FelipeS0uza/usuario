package com.felipesouza.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter     //Cria automaticamente todos dos getters
@Setter     //Cria automaticamente todos os setter
@AllArgsConstructor //Cria o construtor recebendo todos os argumentos
@NoArgsConstructor  //Cria o construtor não recebendo nenhum argumento
@Entity     //Aponta para o spring que é uma tabela do banco de dados
@Table(name = "usuario")    //Indica o nome da tabela, caso não indique usa como default o nome da classe
//UserDetails é uma classe do spring security, para que o Usuario seja validado como usuario de login
@Builder
public class Usuario implements UserDetails {

    @Id     //Identificador único
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera automaticamente os id's
    private Long id;

    @Column(name = "nome", length = 100)
    //Identifica o nome da coluna e quantidade de caracteres, entre outras informações
    private String nome;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "senha")
    private String senha;

    @OneToMany(cascade = CascadeType.ALL)  //Um usuario para muitos endereços, e faz o relacionamento em cascata
    // (quando o usuario é excluido, tanto o telefone quanto os endereços são excluidos automaticamente)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    //Indica o nome da coluna(usuarios_id) na tabela endereços
    // que faz referencia a coluna ID na tabela usuarios
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

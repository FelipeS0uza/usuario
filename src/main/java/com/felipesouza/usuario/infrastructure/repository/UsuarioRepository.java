package com.felipesouza.usuario.infrastructure.repository;

import com.felipesouza.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository     //Indica que essa interface é responsável por acessar o banco

//JpaRepository indica que estou herdando funcionalidades prontas, como save(), findById(), findByEmail(), etc.
//<Usuario, Long> indica a entidade e o tipo do ID
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Script do JPA que leva para o banco de dados e verifica se o email já existe
    boolean existsByEmail(String email);

    //Optional é uma classe que serve para evitar o retorno de informações nulas, obrigando a criar um tratamento de erro
    Optional<Usuario> findByEmail(String email);

    @Transactional      //Indica que a operação de escrita no banco precisa de transação
    void deleteByEmail(String email);
}
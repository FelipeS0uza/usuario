//Camda REPOSITORY é onde temos persistência e acesso ao bando de dados (CRUD)
// Converte objetos Java (Entities) em comandos SQL e vice-versa

package com.felipesouza.usuario.infrastructure.repository;

import com.felipesouza.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository     //Indica ao spring que é uma repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Script do JPA que leva para o banco de dados e verifica se o email já existe
    boolean existsByEmail(String email);

    //Optional é uma classe que serve para evitar o retorno de informações nulas
    Optional<Usuario> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
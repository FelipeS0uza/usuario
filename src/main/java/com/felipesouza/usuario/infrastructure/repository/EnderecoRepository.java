package com.felipesouza.usuario.infrastructure.repository;

import com.felipesouza.usuario.infrastructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository     //Indica que essa interface é responsável por acessar o banco

//JpaRepository indica que estou herdando funcionalidades prontas, como save(), findById(), findByEmail(), etc.
//<Endereco, Long> indica a entidade e o tipo do ID
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
        /*
    Aqui não foi implementado nada pois o JPA já disponibiliza metodos pronto, entre eles
    .save()
    .findById()
    .saveAll()
    .deleteById()
    .deleteAll()
    .existById()
     */
}
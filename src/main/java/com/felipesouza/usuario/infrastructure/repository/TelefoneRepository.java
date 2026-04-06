//Camda REPOSITORY é onde temos persistência e acesso ao bando de dados (CRUD)
// Converte objetos Java (Entities) em comandos SQL e vice-versa.

package com.felipesouza.usuario.infrastructure.repository;

import com.felipesouza.usuario.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository     //Indica ao spring que é uma repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
package com.felipesouza.usuario.infrastructure.repository;

import com.felipesouza.usuario.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Telefone, Long> {
}

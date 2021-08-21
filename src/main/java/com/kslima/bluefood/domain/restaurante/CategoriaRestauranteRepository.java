package com.kslima.bluefood.domain.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRestauranteRepository extends JpaRepository<CategoriaRestaurante, Integer> {

}

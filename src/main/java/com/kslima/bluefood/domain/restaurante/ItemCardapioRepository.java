package com.kslima.bluefood.domain.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {

    @Query("SELECT DISTINCT ic.categoria FROM ItemCardapio ic WHERE ic.restaurante.id = ?1 ORDER BY ic.categoria")
    List<String> findCategorias(Integer restauranteId);

}

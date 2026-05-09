package com.umg.carrito.repository;

import com.umg.carrito.model.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {

    CarritoItem findByProductoId(Long productoId);
}
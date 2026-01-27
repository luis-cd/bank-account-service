package com.example.banca.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.banca.infrastructure.persistence.jpaentities.CuentaBancariaEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancariaEntity, Long> {

    List<CuentaBancariaEntity> findByClienteDni(String dniCliente);

    @Modifying(
        flushAutomatically = true,
        clearAutomatically = true
    )
    @Transactional
    @Query("UPDATE CuentaBancariaEntity c SET c.total = :total WHERE c.id = :id")
    int actualizarTotal(@Param("id") Long id, @Param("total") Double total);
}

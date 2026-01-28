package com.example.banca.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

import com.example.banca.domain.ports.out.ClienteRepositoryPort;
import com.example.banca.infrastructure.persistence.jpaentities.ClienteEntity;
import com.example.banca.infrastructure.persistence.jpaentities.CuentaBancariaEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {

    @EntityGraph(attributePaths = "cuentas")
    Optional<ClienteEntity> findByDni(String dni);

    @EntityGraph(attributePaths = "cuentas")
    List<ClienteEntity> findAll();

    @EntityGraph(attributePaths = "cuentas")
    List<ClienteEntity> findAllByOrderByApellido1Asc();

    @EntityGraph(attributePaths = "cuentas")
    List<ClienteEntity> findByFechaNacimientoBefore(LocalDate fecha);

    @Query("""
        SELECT c
        FROM ClienteEntity c
        JOIN c.cuentas cuenta
        GROUP BY c
        HAVING SUM(cuenta.total) > :cantidad
    """)
    List<ClienteEntity> findClientesConTotalMayorQue(@Param("cantidad") Double cantidad);
}
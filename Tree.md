```
.
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── banca
    │   │               ├── application
    │   │               │   └── services
    │   │               │       ├── ClienteService.java
    │   │               │       └── CuentaService.java
    │   │               ├── BankAccountServiceApplication.java
    │   │               ├── domain
    │   │               │   ├── model
    │   │               │   │   ├── Cliente.java
    │   │               │   │   ├── CuentaBancaria.java
    │   │               │   │   ├── Exceptions
    │   │               │   │   │   ├── ClienteNoEncontradoException.java
    │   │               │   │   │   ├── CuentaNoEncontradaException.java
    │   │               │   │   │   └── GlobalExceptionHandler.java
    │   │               │   │   └── ValueObjects
    │   │               │   │       ├── Dni.java
    │   │               │   │       └── TipoCuenta.java
    │   │               │   └── ports
    │   │               │       ├── in
    │   │               │       │   ├── ActualizarSaldoUseCase.java
    │   │               │       │   ├── CrearCuentaUseCase.java
    │   │               │       │   ├── ObtenerClientePorDniUseCase.java
    │   │               │       │   ├── ObtenerClientesConCuentaSuperiorAUseCase.java
    │   │               │       │   ├── ObtenerClientesMayoresDeEdadUseCase.java
    │   │               │       │   └── ObtenerClientesUseCase.java
    │   │               │       └── out
    │   │               │           ├── ClienteRepositoryPort.java
    │   │               │           └── CuentaRepositoryPort.java
    │   │               └── infrastructure
    │   │                   ├── persistence
    │   │                   │   ├── jpaentities
    │   │                   │   │   ├── ClienteEntity.java
    │   │                   │   │   └── CuentaBancariaEntity.java
    │   │                   │   ├── mappers
    │   │                   │   │   ├── ClienteMapper.java
    │   │                   │   │   └── CuentaBancariaMapper.java
    │   │                   │   └── repositories
    │   │                   │       ├── ClienteRepositoryAdapter.java
    │   │                   │       ├── ClienteRepository.java
    │   │                   │       ├── CuentaBancariaRepository.java
    │   │                   │       └── CuentaRepositoryAdapter.java
    │   │                   └── rest
    │   │                       ├── Controladores
    │   │                       │   ├── ClienteController.java
    │   │                       │   └── CuentaController.java
    │   │                       ├── DTOs
    │   │                       │   ├── ActualizarCuentaRequest.java
    │   │                       │   ├── ClienteDTO.java
    │   │                       │   ├── CrearCuentaRequest.java
    │   │                       │   └── CuentaDTO.java
    │   │                       └── mappers
    │   │                           └── ClienteRestMapper.java
    │   └── resources
    │       ├── application.properties
    │       ├── data.sql
    │       └── openapi
    │           └── bank-api.yaml
    └── test
        └── java
            └── com
                └── example
                    └── banca
                        ├── application
                        │   └── services
                        │       ├── ClienteServiceIntegrationTest.java
                        │       ├── ClienteServiceTest.java
                        │       ├── CuentaServiceIntegrationTest.java
                        │       └── CuentaServiceTest.java
                        ├── BankAccountServiceApplicationTests.java
                        ├── domain
                        │   └── model
                        │       ├── ClienteTest.java
                        │       ├── CuentBancariaTest.java
                        │       └── ValueObjects
                        │           ├── DniTest.java
                        │           └── TipoCuentaTest.java
                        └── infrastructure
                            ├── persistence
                            │   ├── mappers
                            │   │   ├── ClienteMapperTest.java
                            │   │   └── CuentaBancariaMapperTest.java
                            │   └── repositories
                            │       ├── ClienteRepositoryTest.java
                            │       └── CuentaBancariaRepositoryTest.java
                            └── rest
                                └── ClienteControllerIntegrationTest.java
```
42 directories, 53 files

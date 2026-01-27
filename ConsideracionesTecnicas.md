# Consideraciones Técnicas

Este documento recoge y explica las decisiones de diseño y consideraciones técnicas del proyecto.

---

## 1. Dominio (Domain)

### Internacionalización y validaciones de identidad

Actualmente, el dominio implementa un **Value Object `Dni`** y la función `esMayorDeEdad()` en `Cliente`.  

- La validación del DNI se limita a un formato simple (`8 números + letra`).
- La mayoría de edad se calcula usando 18 años como referencia.

**Consideraciones reales**:  

- En un sistema internacional sería necesario soportar **NIE, pasaportes u otros identificadores** según país.  
- La regla de mayoría de edad puede variar entre jurisdicciones (16, 18, 21 años).  
- La implementación actual se deja simple para ajustarse al ejemplo, pero se podría extender añadiendo:  
  - Estrategia de validación por tipo de documento (`Dni`, `NIE`, `Pasaporte`)  
  - Configuración de mayoría de edad por país en `Cliente` o mediante un servicio de reglas de negocio.

---

### Uso de Value Objects

Usar los tipos propios del lenguaje y literales de texto son dos code smells a evitar ("Primitive obsession" y "Magic Strings"), además de no permitir lógica interna como es la validación y normalización.
Para solucionarlo hay que crear Value Objects para los elementos que así lo requieran:

- `Dni`  
- `TipoCuenta`  

**Implicación**:

- Los Value Objects obligan a tener **mappers en la infraestructura**, para transformar entre entidades del dominio y entidades de persistencia, o entre JSON y dominio en los controladores REST.

---

### Representación del saldo de la cuenta (`total`)

Actualmente `CuentaBancaria.total` se implementa como `double`. En sistemas reales **lo recomendable es `BigDecimal`**, para evitar errores de precisión en operaciones financieras. Se mantiene `double` para simplificar la implementación y ajustarse a los requerimientos de la prueba técnica.

---

### Tests del dominio

- Los tests de dominio (`ClienteTest`, `CuentaBancariaTest`, VO tests) se realizan **aislados**, usando **JUnit 5** puro.  
- No requieren infraestructura ni acceso a bases de datos.  
- Esto permite validar la **lógica de negocio y reglas fundamentales** de forma rápida y confiable.

---

### Evitar setters en entidades y Value Objects

En el dominio actual **no se utilizan setters**: todas las propiedades son `final` y solo se pueden asignar vía constructor o mediante **factory methods**.

**Motivación**:

1. **Inmutabilidad y consistencia de estado**  
2. **Seguridad y protección**  
3. **Encapsulamiento de lógica y simplificación de testeo**  

A mayores será necesario proteger los endpoints REST para que no sean accesibles de forma no autorizada, especialmente en un caso real en el que se trabaje con cuentas bancarias

# Pokedex App (API REST v1 & v2)

¡Bienvenido a la **Pokedex App Básico**, un ejemplo didáctico de una API REST construida con Spring Boot, Spring Data JPA y MySQL!

---

## 📋 Contenidos

1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Requisitos Previos](#requisitos-previos)
3. [Configuración](#configuración)
4. [Cómo Ejecutar](#cómo-ejecutar)
5. [Estructura de Paquetes](#estructura-de-paquetes)
6. [Endpoints de la API v1](#endpoints-de-la-api-v1)
   - Entrenadores
   - Habilidades
   - Pokémon
7. [Endpoints de la API v2 (Búsqueda con Query Params)](#endpoints-de-la-api-v2-búsqueda-con-query-params)
8. [Ejemplos de Peticiones (cURL)](#ejemplos-de-peticiones-curl)
9. [Explicación de Relaciones JPA](#explicación-de-relaciones-jpa)
10. [Buenas Prácticas y Siguientes Pasos](#buenas-prácticas-y-siguientes-pasos)

---

## 📘 Descripción del Proyecto

La **Pokedex App Básico** es una API REST sencilla para gestionar:

- **Entrenadores** (Trainer)
- **Habilidades** (Skills)
- **Pokémon**

Se muestran ejemplos de CRUD completo, relaciones Many-to-One, One-to-Many y Many-to-Many con entidades JPA, y cómo exponerlos mediante controladores RESTful.

---

## 📊 Diagrama de la Base de Datos

![Diagrama ER de PokedexDB](https://raw.githubusercontent.com/cifp-villa-aguimes/pokedex/refs/tags/v1/src/main/resources/static/diagram.png)

---

## ⚙️ Requisitos Previos

- Java 17 o superior
- Maven 3.8+
- MySQL (y cliente como MySQL Workbench)
- IDE compatible (IntelliJ IDEA, VS Code, NetBeans…)

---

## 🔧 Configuración

1. Clona este repositorio:
   ```bash
   git clone <REPO_URL>
   cd pokedex
   ```
2. Crea la base de datos en MySQL:
   ```sql
   CREATE DATABASE pokedexdb;
   ```
3. Ajusta `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/pokedexdb?useSSL=false&serverTimezone=UTC
   spring.datasource.username=TU_USUARIO
   spring.datasource.password=TU_CONTRASEÑA
   spring.jpa.hibernate.ddl-auto=update
   ```

---

## ▶️ Cómo Ejecutar

Puedes ejecutar la aplicación de varias formas:

1. **Desde la línea de comandos**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   La aplicación arrancará en `http://localhost:8080/`.

2. **Ejecutando la clase principal**  
   En tu IDE (IntelliJ IDEA, VS Code, NetBeans, Eclipse), busca y ejecuta la clase `com.damw.pokedex.PokedexApplication` (método `main`). Esto iniciará la aplicación en el mismo puerto.

3. **Spring Boot Dashboard (VS Code)**  
   Si usas VS Code, abre la vista “Spring Boot Dashboard”, selecciona el proyecto **pokedex** y haz clic en **Run**.

4. **Run Configurations (IntelliJ IDEA)**  
   En IntelliJ, crea una **Run Configuration** de tipo **Spring Boot** apuntando a `com.damw.pokedex.PokedexApplication` y ejecútala.

> Con Spring Boot DevTools habilitado, la aplicación se recargará automáticamente al detectar cambios en el código. Si no ves los cambios reflejados, puedes reiniciar manualmente usando cualquiera de los métodos anteriores.

---

**Consejo:**  
Puedes usar herramientas como [Postman](https://www.postman.com/) o `curl` para probar los endpoints de la API.

## 📂 Estructura de Paquetes

```
com.damw.pokedex
├── controller   → HTTP REST Controllers
├── model        → Entidades JPA
├── repository   → Interfaces JpaRepository
├── service      → Lógica de negocio / transacciones
└── static       → index.html de bienvenida
```

---

## 🚀 Endpoints de la API v1

### Entrenadores

| Método | Ruta                                 | Descripción                       |
| ------ | ------------------------------------ | --------------------------------- |
| GET    | `/api/v1/entrenadores`               | Lista todos los entrenadores      |
| GET    | `/api/v1/entrenadores/{id}`          | Obtiene un entrenador por ID      |
| GET    | `/api/v1/entrenadores/{id}/pokemons` | Lista sus Pokémon asociados       |
| POST   | `/api/v1/entrenadores`               | Crea un nuevo entrenador          |
| PUT    | `/api/v1/entrenadores/{id}`          | Actualiza un entrenador existente |
| DELETE | `/api/v1/entrenadores/{id}`          | Elimina un entrenador             |

### Habilidades

| Método | Ruta                                | Descripción                            |
| ------ | ----------------------------------- | -------------------------------------- |
| GET    | `/api/v1/habilidades`               | Lista todas las habilidades            |
| GET    | `/api/v1/habilidades/{id}`          | Obtiene una habilidad por ID           |
| GET    | `/api/v1/habilidades/{id}/pokemons` | Lista Pokémon que tienen esa habilidad |
| POST   | `/api/v1/habilidades`               | Crea una nueva habilidad               |
| PUT    | `/api/v1/habilidades/{id}`          | Actualiza una habilidad existente      |
| DELETE | `/api/v1/habilidades/{id}`          | Elimina una habilidad                  |

### Pokémon

| Método | Ruta                    | Descripción                    |
| ------ | ----------------------- | ------------------------------ |
| GET    | `/api/v1/pokemons`      | Lista todos los Pokémon        |
| GET    | `/api/v1/pokemons/{id}` | Obtiene un Pokémon por ID      |
| POST   | `/api/v1/pokemons`      | Crea un nuevo Pokémon          |
| PUT    | `/api/v1/pokemons/{id}` | Actualiza un Pokémon existente |
| DELETE | `/api/v1/pokemons/{id}` | Elimina un Pokémon             |

---

## 🆕 Endpoints de la API v2 (Búsqueda con Query Params)

| Método | Ruta                                                                               | Descripción                                       |
| ------ | ---------------------------------------------------------------------------------- | ------------------------------------------------- |
| GET    | `/api/v2/pokemons/buscar`                                                          | Lista Pokémon con filtros y ordenación opcionales |
| GET    | `/api/v2/pokemons/buscar?tipo=agua`                                                | Filtra por tipo “agua”                            |
| GET    | `/api/v2/pokemons/buscar?nivelMin=10&nivelMax=50`                                  | Filtra por rango de nivel (10-50)                 |
| GET    | `/api/v2/pokemons/buscar?sortBy=salud&order=desc`                                  | Ordena resultados por salud descendente           |
| GET    | `/api/v2/pokemons/buscar?tipo=fuego&nivelMin=5&nivelMax=30&sortBy=nivel&order=asc` | Combinación de filtros y ordenación               |

---

## 💻 Ejemplos de Peticiones (cURL)

```bash
# Crear entrenador
curl -X POST http://localhost:8080/api/v1/entrenadores \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ash Ketchum"}'

# Crear Pokémon
curl -X POST http://localhost:8080/api/v1/pokemons \
  -H "Content-Type: application/json" \
  -d '{
        "nombre":"Pikachu",
        "tipo":"eléctrico",
        "nivel":6,
        "salud":40,
        "entrenador":{"id":1},
        "habilidades":[{"id":1},{"id":2}]
      }'

# Obtener pokemons de un entrenador
curl http://localhost:8080/api/v1/entrenadores/1/pokemons

# Obtener pokemons de una habilidad
curl http://localhost:8080/api/v1/habilidades/5/pokemons

# Búsqueda avanzada v2 (Query Params)
curl "http://localhost:8080/api/v2/pokemons/buscar?tipo=agua&nivelMin=5&nivelMax=30&sortBy=nivel&order=asc"
```

---

## 🔗 Explicación de Relaciones JPA

1. **Many-to-One (Pokémon → Entrenador)**

   ```java
   @ManyToOne(fetch = EAGER)
   @JoinColumn(name="entrenador_id")
   private Entrenador entrenador;
   ```

2. **One-to-Many (Entrenador → Pokémon)**

   ```java
   @OneToMany(mappedBy="entrenador", cascade=ALL, orphanRemoval=true)
   private List<Pokemon> pokemons;
   ```

3. **Many-to-Many (Pokémon ↔ Habilidad)**
   ```java
   @ManyToMany
   @JoinTable(name="pokemon_habilidad",
       joinColumns=@JoinColumn(name="pokemon_id"),
       inverseJoinColumns=@JoinColumn(name="habilidad_id")
   )
   private Set<Habilidad> habilidades;
   ```

---

## ⭐ Buenas Prácticas y Siguientes Pasos

- Usa `@Valid` y validaciones (`@NotBlank`, `@Positive`) para asegurar integridad de datos.
- Añade logs (`log.debug`, `log.info`) en controladores para depurar.
- Considera validaciones avanzadas con DTOs o vistas JSON en proyectos más complejos.
- ¡Prueba en Postman o cURL todas las rutas y observa cómo interactúan las relaciones!

> **Nota:** En la versión 2 de la API se implementaron Query Params para filtrado y ordenación dinámica, usando `@RequestParam` y métodos de consulta derivados en Spring Data JPA.

---

> ¡Felicidades por completar tu API REST de Pokédex en Spring Boot! 🎉

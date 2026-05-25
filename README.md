# Segundo Parcial – Sistema de Biblioteca Digital

## Indicaciones Generales

Lea detenidamente el enunciado y asegúrese de comprender los requisitos funcionales, las reglas de negocio y los entregables antes de comenzar. El ejercicio debe resolverse **individual**, aplicando correctamente la **arquitectura N-Capas** y las buenas prácticas vistas en clase.

> ⚠️ **No se aceptarán commits realizados después de la hora límite establecida para el parcial.**

---

## Sistema de Biblioteca Digital

**Descripción:** Desarrollar una API REST que permita gestionar el inventario de libros de una biblioteca digital, implementando las operaciones básicas de CRUD, validaciones, reglas de negocio y manejo adecuado de respuestas HTTP.

---

## Entidad `Book`

| Campo | Tipo | Reglas |
|-------|------|--------|
| `id` | Long | Autogenerado |
| `title` | String | Requerido, único, no vacío |
| `author` | String | Requerido, no vacío |
| `isbn` | String | Requerido, único, formato válido |
| `genre` | Enum | Uno de: `FICTION`, `NON_FICTION`, `SCIENCE`, `HISTORY`, `TECHNOLOGY`, `CHILDREN` |
| `totalCopies` | Integer | Obligatorio, ≥ 1 |
| `availableCopies` | Integer | Calculado automáticamente, ≥ 0 |
| `available` | Boolean | Se gestiona según `availableCopies` |
| `publishedDate` | Date | Opcional, no puede ser fecha futura |
| `description` | String | Opcional |

---

## Reglas de Negocio

- **Título único:** No se permite registrar dos libros con el mismo título sin importar mayúsculas/minúsculas. No puede existir un libro sin título ni genre.
- **ISBN único y válido:** El ISBN debe ser único en el sistema. No se permiten duplicados.
- **Copias disponibles:** `availableCopies` no puede ser mayor que `totalCopies`. Si `availableCopies = 0`, el campo `available` debe cambiar automáticamente a `false`.
- **Protección de eliminación:** No se puede eliminar un libro si tiene copias prestadas (es decir, si `availableCopies < totalCopies`).
- **Fecha de publicación:** No puede ser una fecha futura; de lo contrario, lanzar excepción.
- **Filtrado:** El listado debe soportar filtros por `genre` y `available`.
    - Ejemplo: `GET /api/books?genre=SCIENCE&available=true`

---

## Operaciones CRUD

| Método HTTP | Endpoint | Descripción |
|-------------|----------|-------------|
| `POST` | `/api/books` | Registrar un nuevo libro |
| `GET` | `/api/books` | Listar todos los libros (con filtros opcionales) |
| `GET` | `/api/books/{id}` | Obtener un libro por ID |
| `PUT` | `/api/books/{id}` | Actualizar la información de un libro |
| `DELETE` | `/api/books/{id}` | Eliminar un libro (respetando reglas de negocio) |

---

## Manejo de Excepciones

Se requieren **al menos 2 excepciones personalizadas**, por ejemplo:

- `ResourceNotFoundException` → HTTP `404`
- `BusinessRuleException` → HTTP `400`

Deben manejarse con un `@RestControllerAdvice` global.

---

## Códigos de Estado HTTP Esperados

| Situación | Código |
|-----------|--------|
| Creación exitosa | `201 Created` |
| Consulta exitosa | `200 OK` |
| Recurso no encontrado | `404 Not Found` |
| Datos inválidos / regla de negocio violada | `400 Bad Request` |
| Eliminación exitosa | `200 OK` o `204 No Content` |

---

## Entregables

Repositorio en **GitHub** con el nombre:

```
pnc-segundo-parcial-<carnet_estudiante>
```

El repositorio debe contener:

1. Código fuente del proyecto Spring Boot.

---

## Rúbrica de Evaluación (90% práctica)

| Criterio | Porcentaje |
|----------|-----------|
| CRUD completo y funcional | 22% |
| Reglas de negocio implementadas correctamente | 23% |
| Uso de anotaciones, entities, DTOs y validaciones | 15% |
| Estructura en capas y claridad del código | 10% |
| Manejo de excepciones personalizado (mínimo 2) | 10% |
| Conexión a base de datos | 5% |
| Buen manejo de códigos de estado HTTP (404, 400, 201, etc.) | 5% |
| **Total** | **90%** |

> El **10% restante** de la nota del parcial corresponde a la parte teórica, evaluada por separado.
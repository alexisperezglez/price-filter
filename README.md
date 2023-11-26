# Buscador de Precios

## Contenido

- [Ejercicio](#ejercicio)
    - [Campos](#campos)
    - [Objetivo](#objetivo)
    - [Requisitos](#requisitos)
- [Solucion](#solucion)
    - [Definicion de Paquetes](#definicion-de-paquetes)
    - [Dependencias](#dependencias)
- [Aplicacion](#aplicacion)
    - [Instalacion](#instalacion)
    - [Ejecucion](#ejecucion)
    - [Tests](#tests)
    - [Endpoints](#endpoints)
        - [Health](#health)
        - [Filtro de Precios](#filtro-de-precios)
- [Docker](#docker)

## Ejercicio

En la base de datos de comercio electrónico de la compañía disponemos de la tabla PRICES que refleja el precio final (
pvp) y la tarifa que aplica a un producto de una cadena entre unas fechas determinadas. A continuación se muestra un
ejemplo de la tabla con los campos relevantes:

| BRAND_ID | START_DATE          | END_DATE            | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----------|---------------------|---------------------|------------|------------|----------|-------|------|
| 1        | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 1          | 35455      | 0        | 35.50 | EUR  |
| 1        | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 2          | 35455      | 1        | 25.45 | EUR  |
| 1        | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 3          | 35455      | 1        | 30.50 | EUR  |
| 1        | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 4          | 35455      | 1        | 38.95 | EUR  |

### Campos

| Campo      | Descripcion                                                                                                            |
|------------|------------------------------------------------------------------------------------------------------------------------|
| BRAND_ID   | foreign key de la cadena del grupo (1 = ZARA)                                                                          |
| START_DATE | fecha de inicio en el que aplica el precio tarifa indicado.                                                            |
| END_DATE   | fecha fin en el que deja de aplicar el precio tarifa indicado.                                                         |
| PRICE_LIST | identificador de la tarifa de precios aplicable                                                                        |
| PRODUCT_ID | identificador codigo de producto                                                                                       |
| PRIORITY   | Desambiguador de aplicacion de precios. Si dos tarifas coinciden en un rango de fechas se aplica la de mayor prioridad |
| PRICE      | precio final de venta                                                                                                  |
| CURR       | iso de la moneda (Ej: EUR)                                                                                             |

### Objetivo

Construir una aplicacion/servicio en SpringBoot que provea un endpoint rest de consulta tal que:

1. Acepte como parametros de entrada: fecha de aplicacion, identificador de producto e identificador de cadena.
2. Devuelva como datos de salida: identificador de producto, identificador de cadena, tarifa a aplicar, fechas de
   aplicacion y precio final a aplicar

### Requisitos

Se debe utilizar una vase de datos en memoria (tipo H2) e inicializar con los datos del ejemplo, (se pueden
cambiar el nombre de los campos y añadir otros nuevos si se quiere, elegir el tipo de dato que se considere
adecuado para los mismos)

Desarrollar unos tests al endpoint rest que validen las siguientes peticiones al servicio con los datos de ejemplo:

| TEST | brandId | productId | applicationDate     |
|------|---------|-----------|---------------------|
| 1    | 1       | 35455     | 2020-06-14 10:00:00 |
| 2    | 1       | 35455     | 2020-06-14 16:00:00 |
| 3    | 1       | 35455     | 2020-06-14 21:00:00 |
| 4    | 1       | 35455     | 2020-06-15 10:00:00 |
| 5    | 1       | 35455     | 2020-06-16 21:00:00 |

## Solucion

La solucion al enunciado anterior se implemento utilizando una arquitectura hexagonal que se describe a continuacion:

```markdown
.
├── main
│ ├── java
│ │ └── es
│ │ └── project
│ │ └── pricefilter
│ │ ├── application
│ │ ├── domain
│ │ │ ├── model
│ │ │ └── service
│ │ │ └── impl
│ │ └── infrastructure
│ │ └── adapter
│ │ ├── api
│ │ │ ├── dto
│ │ │ │ ├── input
│ │ │ │ └── output
│ │ │ └── v1
│ │ ├── mapper
│ │ └── persistence
│ │ ├── entity
│ │ └── repository
│ │ └── impl
│ └── resources
│ └── db
│ └── migration
└── test
├── java
│ └── es
│ └── project
│ └── pricefilter
│ └── cucumberglue
└── resources
└── features
```

### Definicion de Paquetes

| Paquete                 | Descripcion                                                                                                                                                                           |
|-------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| application             | Contiene las configuraciones de la aplicacion.                                                                                                                                        |
| domain                  | contiene la lógica de negocios central de la aplicación. Es independiente de cualquier dependencia externa, incluyendo Spring Boot o cualquier tecnología de base de datos específica |
| model                   | contiene las estructuras de dominio requeridas por la logica de negocio                                                                                                               |
| service                 | contiene la definicion de la logica de negocio.                                                                                                                                       |
| service.impl            | Contiene las implementaciones especificas de la logica de negocio y la interaccion con los adaptadores.                                                                               |
| infrastructure          | contiene las implementaciones concretas de los adaptadores. Es responsable de conectar la aplicación a sistemas externos, como bases de datos y APIs REST                             |
| adapter                 | traducen entre la capa de dominio y el mundo exterior. Se pueden considerar como componentes "enchufa-bles" que se pueden reemplazar fácilmente sin afectar a la capa de dominio      |
| api                     | contiene las piezas de codigo necesarias para los endpoints REST expuestos por la aplicacion                                                                                          |
| dto                     | Contiene las estructuras de entrada y salida de las APIs.                                                                                                                             |
| input                   | Contiene las estructuras de entrada de las APIs.                                                                                                                                      |
| output                  | Contiene las estructuras de salida de las APIs.                                                                                                                                       |
| v1                      | Contiene la implementacion de los endpoints REST expuestos por la aplicacion.                                                                                                         |
| mapper                  | Contiene la logica de transformacion requeridas por las REST APIs.                                                                                                                    |
| persistence             | Contiene las piezas de codigo necesarias para la interaccion con el sistema de almacenamiento externo                                                                                 |
| entity                  | Contiene las estructuras de datos representativas de la base de datos.                                                                                                                |
| repository              | Contiene las definiciones de las interacciones con el sistema de almacenamiento externo.                                                                                              |
| repository.impl         | Contiene las implementaciones especificas de las interacciones con el sistema de almacenamiento externo.                                                                              |
| db.migration            | Contiene los scripts sql de migracion e inicializacion de base de datos.                                                                                                              |
| test.resources.features | Contiene las definiciones en lenguaje declarativo de los tests sobre la aplicacion.                                                                                                   |

### Dependencias

Algunas de las principales dependencias de la solucion incluyen:

- lombok: generador de codigo en tiempo de compilacion que reduce la escritura de codigo repetitivo (ej. getters,
  setters, constructores, metodo toString, etc...)
- flyway: permite realizar migraciones de base de datos relacionales facilitando el desarrollo de nuevas
  funcionalidades.
- h2: base de datos en memoria muy util para pruebas.
- cucumber: permite la definicion de casos de prueba de forma declarativa utilizando Gerkin como lenguaje
- rest-assured: Es una herramienta que permite la prueba de APIs con una implementacion declarativa que facilita la
  lectura de la prueba en cuestion.
- mapstruct: permite realizar transformaciones de un objeto a otro de forma clara y sencilla

## Aplicacion

### Instalacion

Para ejecutar la aplicacion es necesario clonar el projecto. Una vez clonado en la carpeta raiz del proyecto ejecutamos
el siguiente comando:

`mvn clean install`

### Ejecucion

Este comando instalara las dependencias necesarias para ejecutar la aplicacion. Una vez haya finalizado la instalacion
de las dependencias podemos levantar la aplicacion ejecutando el siguiente comando:

`mvn spring-boot:run`

Este comando levantara la aplicacion en el puerto 8080 (por defecto) permitiendo consumir los endpoints

### Tests

Para ejecutar los tests debemos ejecutar el siguiente comando:

`mvn test`

### Endpoints

La aplicacion cuenta con dos endpoints expuestos:

#### Health

Este endpoint sirve para comprobar si el servicio se esta ejecutando de manera correcta.

`http://localhost:8080/api/rest/health`

#### Filtro de Precios

Este endpoint tiene como objetivo el de retornar el precio final aplicable en un rango de fechas.

`http://localhost:8080/api/rest/v1/price-filter`

Un ejemplo de peticion seria:

```shell
curl --location 'http://localhost:8080/api/rest/v1/price-filter' \
--header 'Content-Type: application/json' \
--data '{
    "brandId": 1,
    "productId": 35455,
    "applicationDate": "2020-06-15 10:00:00"
}'
```

## Docker

Dentro de la aplicacion existe un fichero llamado **Dockerfile** para empaquetar la aplicacion en una imagen docker.
Para empaquetar y ejecutar la aplicacion con docker solo es necesario seguir los siguientes pasos

1. Crear la imagen docker desde la raiz del proyecto ejecutando el siguiente comando:

```shell
docker build -t pricefilter:1.0 .
```

2. Ejecutar un contenedor de la imagen con el siguiente comando:

```shell
docker run -ti --rm -p 8080:8080 pricefilter:1.0
```

Este comando eliminara el contenedor una vez terminemos su ejecucion.

## API doc

La aplicacion expone la documentacion de la api en los siguientes endpoints:

1. [http://localhost:8080/api/rest/swagger-ui.html](http://localhost:8080/api/rest/swagger-ui.html)

2. [http://localhost:8080/api/rest/v3/api-docs](http://localhost:8080/api/rest/v3/api-docs)


# EvolutionCode Technical Test

---

* [ENG](#table-of-contents) 
* [ES](#tabla-de-contenido)

## Table Of Contents

---
1. [Introduction](#introduction)
2. [Project setup](#project-setup)
   1. [Consume from deploy.](#remote-deployment)
   2. [Docker.](#docker)
   3. [Local.](#local-setup)
3. [API Documentation.](#api-documentation)
   1. [Home "/"](#home-path-)
      * [GET](#get-)
   2. [Tasks "/tasks"](#tasks-tasks)
      * [GET](#get-tasks)
      * [GET/ID](#get-tasksid)
      * [POST](#post-tasks)
      * [PUT](#put-tasksid)
      * [DELETE](#delete-tasksid)


## Introduction

---

This is a CRUD backend application developed in Spring Boot 3.2.0 with Java 21. This application allows to   
manage tasks as in a TO-DO application. Since this is a CRUD application, Creating, Retrieving, Updating and Deleting tasks are among the application's scope and functional requirements.

Non-functional requirements for this application include the following:

* Backend development in Java and Spring Boot.
* TDD implementation for assurance of code quality.
* Use of GIT and GitHub for code versioning.
* Use of Docker for easement of deployment and setting up a development environment.
* Use of an in memory database for storage. In this case, H2 was selected.

## Project Setup

---

This project is available to consume and review within the following options:
* [Consume of endpoints using current remote deployment.](#remote-deployment)
* [Consume of endpoints through local environment using Docker.](#docker)
* [Consume of endpoints through local environment setting up the application.](#local-setup)

To know how to consume the endpoints available after the setup you can verify
the [api documentation.](#api-documentation)

### Remote Deployment

This backend project is deployed in the following url 
https://evolution-code-test-backend.onrender.com using a cloud free host service.
To use this option you can:

* Use Postman.
* Use CURL CLI or similar.
* Access directly to the URL with your browser.

### Docker

A Dockerfile is available in the root of the application. In order to use it, you 
will need to have Docker previously installed and clone the repository locally 
into your machine.   

Follow the next instructions to get a Docker container of this application running 
in your machine:

1. Create a directory in your computer where you will store the application.
2. Clone the application running the following command:   
   `git clone https://github.com/neortegab/evolutioncode-technicaltest.git`   
3. Move into the cloned project with your File Explorer or using the `cd` 
command in a shell or cmd terminal.
4. Use the Docker CLI to build and run the image with the following commands:     
          
    `docker build -t evolution-code-test .`   
           
    Here we are telling docker to build an image with the tag "evolution-code-test"
and using the root directory to find the dockerfile to run the instructions for 
building.   
             
    `docker run -dp 8080:8080 --name java-backend evolution-code-test`   
                 
    Here we are telling docker to run a container in `localhost:8080` with the 
name java-backend and using the image `evolution-code-test` that we previously 
created.

5. Now, you can use Postman, a CLI like CURL or your browser with the 
url `http://localhost:8080` to use the application's endpoints.

>Note: Since the container is detached as we ran it with the `-d` parameter in 
the run command, the application will keep on running in the background even if 
you aren't actively using it. To stop the application from running you can use
either one of these commands:   
`docker stop java-backend`   
`docker kill java-backend`   
If you set up another tag name during the running of the container, replace 
`java-backend` in these commands with your personalized tag.

### Local setup

In order to use this project locally without Docker it is necessary to have Java 21,
Maven and an IDE compatible with the Open JDK 21 to run the application.

After installing these main requirements you can:
1. Clone the repository with the source code using    
`git clone https://github.com/neortegab/evolutioncode-technicaltest.git`
2. Open the source code in your IDE (IntelliJ IDEA, Eclipse, and NetBeans are 
the most suggested).
3. With your IDE, run the file `EvolutionCodeTestApplication.java` located in the
`src/main/java/com/example/evolutioncodetest/` package.
4. You will be able to use the application's API as long as your IDE is running 
in the background.

## API Documentation

---

### Home Path "/"

This endpoint will provide all the available routes inside the application.

#### GET `"/"`

* PARAMETERS   

  This method doesn't receive any parameters.

* RESPONSES   
  
  | Status Code | Status Message | Body Message |
  |-------------|----------------|--------------|
  | 200         | Ok             | NA           |   
  
* RESPONSE BODY EXAMPLE   

  ```json
    {
        "tasks": "http://localhost:8080/tasks"
    }
  ```

### Tasks "/tasks"

This endpoint will allow to create, retrieve, update or delete tasks.

#### GET `"/tasks"`

* PARAMETERS   

  | Name        | Data Type | Param Type | Description                                                   |
  |-------------|-----------|------------|---------------------------------------------------------------|
  | description | string    | query      | Allows to query for the tasks with the mentioned description. |
  | completed   | boolean   | query      | Allows to query for the tasks with the status mentioned.      |

* RESPONSES
  
  | Status Code | Status Message | Body Message |
  |-------------|----------------|--------------|
  | 200         | Ok             | NA           |   

* RESPONSE BODY EXAMPLE   
  
    ```json
    [
      {
        "id": "3805b4dc-9a6c-4e02-b168-3fa643627c4e",
        "description": "I am a task",
        "isCompleted": false
      },
      {
        "id": "d2fcbc2d-fe2f-4154-81b8-949136e41ecf",
        "description": "I am another task",
        "isCompleted": true
      }
    ]
    ```
  
#### GET `"/tasks/{id}"`

* PARAMETERS

  | Name        | Data Type     | Param Type | Description                                                   |
  |-------------|---------------|------------|---------------------------------------------------------------|
  | id*         | string (uuid) | param      | Allows to query for the task with the given id.               |

  Required*    

* RESPONSES

  | Status Code | Status Message | Body Message       |
  |-------------|----------------|--------------------|
  | 200         | Ok             | NA                 |   
  | 400         | Bad Request    | Bad parameter type |   
  | 404         | Not found      | Task not found     |   

* RESPONSE BODY EXAMPLE   

  ```json
    {
      "id": "3805b4dc-9a6c-4e02-b168-3fa643627c4e",
      "description": "I am a task",
      "isCompleted": false
    }
  ```
  
#### POST `"/tasks/`

* PARAMETERS
  * BODY

  | Name         | Data Type | Description                        |
  |--------------|-----------|------------------------------------|
  | description* | string    | Description of the task to create. |
  | isCompleted  | boolean   | Status of the task to create.      |

  Required*

* REQUEST BODY EXAMPLE   

  `"isCompleted"` is defaulted to `false`

  ```json
    {
      "description": "I am a task"
    }
  ```
  Send the `"isCompleted"` attribute when you want isCompleted different to 
  false.

  ```json
    {
      "description": "I am a task",
      "isCompleted": true
    }
  ```
  
* RESPONSES

  | Status Code | Status Message | Body Message |
  |-------------|----------------|--------------|
  | 200         | Ok             | NA           |   
  | 400         | Bad Request    | Bad body     |   

* RESPONSE BODY EXAMPLE

  ```json
    {
      "id": "3805b4dc-9a6c-4e02-b168-3fa643627c4e",
      "description": "I am a task",
      "isCompleted": false
    }
  ```
  
#### PUT `"/tasks/{id}`
    
* PARAMETERS
  * PATH

      | Name        | Data Type     | Param Type | Description                                                   |
      |-------------|---------------|------------|---------------------------------------------------------------|
      | id*         | string (uuid) | param      | Allows to query for the task with the given id.               |

   Required*

  * BODY

      | Name        | Data Type | Description                        |
      |-------------|-----------|------------------------------------|
      | description | string    | Description of the task to create. |
      | isCompleted | boolean   | Status of the task to create.      |

    Either `description` or `isCompleted` must be sent in this method, or a 
    bad request error will trigger.

* RESPONSES

  | Status Code | Status Message | Body Message                  |
  |-------------|----------------|-------------------------------|
  | 200         | Ok             | NA                            |   
  | 400         | Bad Request    | Bad body                      |   
  | 404         | Not found      | Task with {id} doesn't exists |   

* RESPONSE BODY EXAMPLE
  
  ```json
    {
      "id": "3805b4dc-9a6c-4e02-b168-3fa643627c4e",
      "description": "I am an updated task",
      "isCompleted": true
    }
  ```
      
#### DELETE `"/tasks/{id}`

* PARAMETERS
    
    | Name        | Data Type     | Param Type | Description                                     |
    |-------------|---------------|------------|-------------------------------------------------|
    | id*         | string (uuid) | param      | Allows to query for the task with the given id. |
    
    Required*

* RESPONSES

  | Status Code | Status Message | Body Message                  |
  |-------------|----------------|-------------------------------|
  | 200         | Ok             | NA                            |   
  | 404         | Not found      | Task with {id} doesn't exists |   

* RESPONSE BODY EXAMPLE
    
    No body response is replied with this method unless it throws an error.

---

## Tabla De Contenido

---

1. [Introducción](#introducción)
2. [Configuración del proyecto](#configuración-del-proyecto)
    1. [Consumir servicios desde el despliegue.](#despliegue-remoto)
    2. [Docker.](#configuración-con-docker)
    3. [Local.](#configuración-local)
3. [Documentación de la API.]() 
   1. [Base "/"](#ruta-base-)
      * [GET](#get--1)
   2. [Tasks "/tasks"](#tasks-tasks-1)
      * [GET](#get-tasks-1)
      * [GET ID](#get-tasksid-1)
      * [POST](#post-tasks-1)
      * [PUT](#put-tasksid-1)
      * [DELETE](#delete-tasksid-1)

## Introducción

---

Esta es una aplicación con operaciones CRUD desarrollada en Spring Boot 3.2.0 con 
Java 21. Esta aplicación permite administrar tareas donde se podrá ver su 
descripción y si están completadas o no. Por lo tanto, su funcionalidad será la 
de una aplicación de Lista de Que Haceres y Crear, Consultar, Actualizar y 
Eliminar tareas estarán entre las operaciones funcionales de la aplicación.

En cuanto a requisitos no funcionales para lograr el correcto desarrollo de la 
aplicación se encuentra:
* Desarrollo Backend en Spring Boot y Java.
* Implementación de pruebas unitarias siguiendo el paradigma de TDD para 
asegurar la calidad del código.
* Uso de Git y GitHub para versionamiento del código y su almacenamiento remoto.
* Uso de Docker para facilitar el despliegue y la configuración de un entorno 
de desarrollo.
* Uso de una base de datos en memoria para persistencia. En este caso, se 
selecciona la base de datos H2.

## Configuración del proyecto

---

Este proyecto está disponible para usarse con las siguientes opciones:
* [Consumo de los endpoints a través del entorno desplegado.](#despliegue-remoto)
* [Consumo de los endpoints de manera local usando Docker](#configuración-con-docker)
* [Consumo de los endpoints de manera local tradicional](#configuración-local)

Para conocer los requisitos y detalles de cómo consumir los endpoints disponibles 
puede consultar la [documentacion del API](#api-documentation)

### Despliegue remoto

Este proyecto backend está desplegado en la siguiente 
url: https://evolution-code-test-backend.onrender.com

El servidor se encuentra en un servicio gratuito de host en nube provisto por la 
plataforma Render. Para poder usar la API del proyecto se pueden utilzar las 
siguientes herramientas:

* Postman.
* CURL CLI o similar.
* Acceder mediante un navegador (Chrome, Firefox, Edge, etc.).

### Configuración con Docker

Un archivo Dockerfile está disponible en la carpeta raíz de la aplicación. 
Para poder usar este archivo, necesita contar con Docker instalado en su sistema 
y será necesario clonar el repositorio con la aplicación en su máquina local. 
Para esto por favor siga las siguientes instrucciones:

1. Cree un directorio en su máquina donde almacenará la aplicación.
2. Clone el repositorio utilizando el siguiente comando:   
   `git clone https://github.com/neortegab/evolutioncode-technicaltest.git`
3. Ubíquese en el proyecto clonado usando su Explorador de Archivos o el 
comando `cd` en una terminal de Shell o CMD.
4. Use la interfaz de comandos de Docker para hacer el build de la imagen:

   `docker build -t evolution-code-test .`

   Con este comando le decimos a Docker que construya una imagen con el tag 
"evolution-code-test" y que use el Dockerfile ubicado en la ubicación actual 
para ejecutar los comandos necesarios para su construcción. Una vez terminado 
ejecute:

   `docker run -dp 8080:8080 --name java-backend evolution-code-test`

   Aquí le instruimos a Docker que ejecute un contenedor en `localhost:8080` con 
el nombre `java-backend` y usando la imagen previamente construida 
`evolution-code-test`.
5. Ahora usted podrá utilizar herramientas como Postman, CURL o su navegador en 
la URI `http://localhost:8080` para usar la API de la aplicación.


>Nota: Debido a que utilizamos el parámetro `-d` en el comando para ejecutar el 
contenedor, la aplicación continuará ejecutándose en segundo plano incluso si no 
se está usando activamente, para detener la aplicación completamente puede usar 
alguno de los siguientes dos comandos:   
`docker stop java-backend`   
`docker kill java-backend`   
Tenga en cuenta que si configuró un tag distinto a java-backend al ejecutar el 
contenedor deberá poner ese tag personalizado en lugar de java-backend.

### Configuración Local

Para poder utilizar este proyecto localmente es necesario instalar Java 21, Open 
JDK 21, Maven y un IDE compatible con Java. Posteriormente a asegurarse que 
cuenta con estos requisitos puede seguir las siguientes instrucciones para 
ejecutar la aplicación:

1. Clonar el código fuente desde el repositorio usando    
   `git clone https://github.com/neortegab/evolutioncode-technicaltest.git`
2. Abrir el código fuente en un entorno de desarrollo (IntellijIDEA, NetBeans y 
Eclipse son los más sugeridos).
3. Con el aplicativo IDE ejecutar el archivo `EvolutionCodeTestApplication.java` 
ubicado en el paquete `src/main/java/com/example/evolutioncodetest/`.
4. Usted podrá utilizar la API del proyecto mientras su programa de entorno de 
desarrollo se encuentre en ejecución en la URL `http://localhost:8080/`.

## Documentacion API

---

### Ruta base "/"

Esta ruta proveerá todas las subrutas disponibles en la aplicación.

#### GET `"/"`

* PARAMETROS

  Este método no recibe parámetros.

* RESPUESTA

  | Código de estado | Mensaje de Estado | Mensaje en el Cuerpo |
  |------------------|-------------------|----------------------|
  | 200              | Ok                | NA                   |   

* EJEMPLO CUERPO DE RESPUESTA

  ```json
    {
        "tasks": "http://localhost:8080/tasks"
    }
  ```

### Tasks "/tasks"

Esta ruta permitirá crear, consultar, actualizar o eliminar tareas.

#### GET `"/tasks"`

* PARÁMETROS

  | Nombre      | Tipo de dato | Tipo de parámetros | Descripción                                                           |
  |-------------|--------------|--------------------|-----------------------------------------------------------------------|
  | description | string       | query              | Permite consultar las tareas que contienen la descripción mencionada. |
  | completed   | boolean      | query              | Permite consultar las tareas con el estado mencionado.                |

* RESPUESTAS

  | Código | Mensaje HTTP | Mensaje en el cuerpo |
  |--------|--------------|----------------------|
  | 200    | Ok           | NA                   |   

* EJEMPLO DE CUERPO DE RESPUESTA

    ```json
    [
      {
        "id": "3805b4dc-9a6c-4e02-b168-3fa643627c4e",
        "description": "Soy una tarea",
        "isCompleted": false
      },
      {
        "id": "d2fcbc2d-fe2f-4154-81b8-949136e41ecf",
        "description": "Soy otra tarea",
        "isCompleted": true
      }
    ]
    ```

#### GET `"/tasks/{id}"`

* PARAMETERS

  | Nombre | Tipo de dato  | Tipo de parámetro | Descripción                            |
  |--------|---------------|-------------------|----------------------------------------|
  | id*    | string (uuid) | param             | ID de la tarea que se desea consultar. |

  Obligatorio*

* RESPUESTAS

  | Código | Mensaje HTTP | Mensaje en el cuerpo |
  |--------|--------------|----------------------|
  | 200    | Ok           | NA                   |   
  | 400    | Bad Request  | Bad parameter type   |   
  | 404    | Not found    | Task not found       |   

* EJEMPLO DE RESPUESTA EN EL CUERPO

  ```json
    {
      "id": "3805b4dc-9a6c-4e02-b168-3fa643627c4e",
      "description": "Soy una tarea",
      "isCompleted": false
    }
  ```

#### POST `"/tasks/`

* PARÁMETROS
    * CUERPO

  | Nombre       | Tipo de dato | Descripción                   |
  |--------------|--------------|-------------------------------|
  | description* | string       | Descripción de la nueva tarea |
  | isCompleted  | boolean      | Estado de la nueva tarea      |

  Obligatorio*

* EJEMPLO DE CUERPO DE PETICIÓN

  `"isCompleted"` se predetermina al valor `false`

  ```json
    {
      "description": "Soy una tarea nueva"
    }
  ```
  Incluya el atributo `"isCompleted"` cuando desee que el estado indicando si 
la tarea está completada sea distinto a `false`.

  ```json
    {
      "description": "Soy una nueva tarea ya completada",
      "isCompleted": true
    }
  ```

* RESPUESTAS

  | Código | Mensaje HTTP | Mensaje en el cuerpo |
  |--------|--------------|----------------------|
  | 200    | Ok           | NA                   |   
  | 400    | Bad Request  |                      |   

* EJEMPLO DEL CUERPO DE RESPUESTA

  ```json
    {
      "id": "3805b4dc-9a6c-4e02-b168-3fa643627c4e",
      "description": "Soy una tarea recientemente creada",
      "isCompleted": false
    }
  ```

#### PUT `"/tasks/{id}`

* PARÁMETROS
    * RUTA

      | Nombre | Tipo de dato  | Tipo de parámetro | Descripción                         |
      |--------|---------------|-------------------|-------------------------------------|
      | id*    | string (uuid) | param             | Identificador de la tarea a buscar. |

  Obligatorio*

    * CUERPO

      | Nombre      | Tipo de dato | Descripción                          |
      |-------------|--------------|--------------------------------------|
      | description | string       | Descripción actualizada de la tarea. |
      | isCompleted | boolean      | Estado actualizado de la tarea.      |

      >Se debe incluir en el cuerpo a `description` o `isCompleted` cuando se
      ejecute este método, de otra manera se proveerá una respuesta de Bad Request.

* RESPUESTAS

  | Código | Mensaje HTTP | Mensaje en el cuerpo          |
  |--------|--------------|-------------------------------|
  | 200    | Ok           | NA                            |   
  | 400    | Bad Request  | Bad body                      |   
  | 404    | Not found    | Task with {id} doesn't exists |   

* EJEMPLO DEL CUERPO DE LA RESPUESTA

  ```json
    {
      "id": "3805b4dc-9a6c-4e02-b168-3fa643627c4e",
      "description": "Soy una respuesta actualizada",
      "isCompleted": true
    }
  ```

#### DELETE `"/tasks/{id}`

* PARÁMETROS

  | Nombre | Tipo de dato  | Tipo de parámetro | Descripción                            |
  |--------|---------------|-------------------|----------------------------------------|
  | id*    | string (uuid) | param             | Identificador de la tarea a consultar. |

  Obligatorio*

* RESPUESTA

  | Código | Mensaje HTTP | Mensaje en el cuerpo          |
  |--------|--------------|-------------------------------|
  | 200    | Ok           | NA                            |   
  | 404    | Not found    | Task with {id} doesn't exists |   

* EJEMPLO DEL CUERPO DE LA RESPUESTA

  No se provee un cuerpo de respuesta para esta petición.

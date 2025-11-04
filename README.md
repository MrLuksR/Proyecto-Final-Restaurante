# Proyecto Final - Restaurante con SQL
<img width="522" height="339" alt="Logo1" src="https://github.com/user-attachments/assets/4e1f71da-8e05-4f71-bbde-08b1a6d22846" />

## Descripción general

Este proyecto es un sistema de gestión para restaurantes, desarrollado en Java utilizando el IDE IntelliJ IDEA.
Dicho sistema permite tomar el control sobre las mesas, pedidos, productos y personal desde una interfaz 
visual que enfatiza en la comodidad del usuario, centralizando la administración del restaurante en una sola aplicación.

Programas utilizados en la realización del proyecto:
- java 21.0.8 2025-07-15 LTS
- MySQL / MariaDB
- Intellij IDEA

## Estructura

La organización del proyecto se centra principalmente en tres packages (paquetes), para una mejor organización
del contenido. Dichos paquetes se organizan de la siguiente manera:

### InterfacesEimpl

<img width="289" height="96" alt="image" src="https://github.com/user-attachments/assets/4e93a9dc-e723-4296-9a88-6f0f7050997a" />

Dentro de este package se contienen tres clases encargadas de la conexión, el guardado e implementación de la base de datos.

1. Clase: ConexionBD
  - Su función es obtener la conexión directa con la base de datos creada previamente en MariaDB (restaurante).

2. Intefaz: GeneralDAO
   - Una interfaz general que contiene los métodos para el guardado de datos dentro de la base.

3. Clase: FinalDAO:
   - Una clase que implementa los métodos de GeneralDAO, a fin de concentrar el guardado de datos en una sola clase.

## Modelo

<img width="300" height="422" alt="image" src="https://github.com/user-attachments/assets/ea628108-8094-4890-b589-76c9474a7567" />

### 1. Clase abstracta: Producto
El objetivo de dicha clase es proporcionar una base común para los diferentes productos que se van a utilizar. De ella
heredan las clases: Comida, Bebida y Postre. Todas ellas con los mismos atributos y métodos.

### 2. fafasd

Atributos: numMesa, estado (booleano), capacidad

Métodos: getters/setters, toString()

4. Cliente

Atributos: nombre, apellido, telefono

Métodos: getters/setters, agregarTelefono, toString()

5. Personal

Atributos: cedula, nombre, apellido, rol, usuario, password

Métodos: getters/setters, toString()

6. Reserva

Atributos: ciCliente, apellido, fecha, hora, mesa, personas

Métodos: getters

7. Excepciones personalizadas

CantidadNegativaException (RuntimeException)

StockInsuficienteException (RuntimeException)

## UI
Este apartado contiene los .form con el diseño del proyecto, además de sus respectivos .java asociados a los form

<img width="301" height="174" alt="image" src="https://github.com/user-attachments/assets/6699f38d-2e1f-428a-b265-a772e336d66a" />

## Sección a ejecutar

<img width="675" height="357" alt="image" src="https://github.com/user-attachments/assets/b257d50b-aa6a-4d61-9e33-65f66dcf274b" />

Para correr el proyecto se debe ir al apartado UI, y ejecutar el main de FormIngreso, cabe destacar que se debe tener la base de datos creada con antelación.

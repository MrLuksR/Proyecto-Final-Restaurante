# Proyecto Final - Restaurante con SQL

## Descripción general

Este proyecto es un sistema de gestión para un restaurante, desarrollado en Java utilizando el IDE IntelliJ IDEA.
El sistema permite controlar las mesas, pedidos, productos, clientes y personal desde una interfaz visual, centralizando la administración del restaurante en una sola aplicación.

Los programas utilizados para la elaboración de este proyecto fueron:
- java 21.0.8 2025-07-15 LTS
- MySQL / MariaDB
- Intellij IDEA

## Estructura del proyecto

El proyecto está organizado en tres paquetes:

InterfacesEimpl

<img width="289" height="96" alt="image" src="https://github.com/user-attachments/assets/4e93a9dc-e723-4296-9a88-6f0f7050997a" />

Este paquete contiene tres clases, las cuales son las encargadas de realizar la conexión con la base de datos

1. Clase: ConexionBD:
  - La función de esta clase, es la gestión de la conexión con la base de datos MySQL (restaurante).
  - Observaciones: Maneja errores mostrando JOptionPane. Esto se observa al no tener la base de datos ya que se muestra un error si esta no existe.

2. Intefaz: GeneralDAO:
   - Esta cumple la función de definir métodos de acceso a datos para todas las entidades del modelo (**Cliente, Personal, Comida, Bebida, Postre, Mesa, Reserva**).

3. Clase: FinalDAO:
   - La función de esta clase es la implementación concreta de los métodos de la interfaz GeneralDAO. 

## Modelo

<img width="300" height="191" alt="image" src="https://github.com/user-attachments/assets/b670b343-d2b6-4ad5-9e96-98d7c4b23514" />

## Dentro contiene las clases:
1. Producto (abstracta)

Atributos: nombre, precio, categoria, stock, descripcion

Métodos: calcImp() (abstracto), getters/setters, toString()

Herencia: Comida, Bebida, Postre

2. Comida, Bebida, Postre

Heredan de Producto

Implementan calcImp()

3. Mesa

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

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

## InterfacesEimpl

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

Diferentes clases utilizadas para la modularización del código y mejorar la legibilidad.

### Clase abstracta: Producto
El objetivo de dicha clase es proporcionar una base común para los diferentes productos que se van a utilizar. De ella
heredan las clases: Comida, Bebida y Postre. Todas ellas con los mismos atributos y métodos.

### Clases normales (.java)
Como clases normales se encuentran Mesa, Factura, Pedido, Personal y Cliente. Su finalidad es tener una mejor estructura
a la hora de modificar algún atributo dentro de la base o simplemente ingresar nuevos datos.

### Excepciones
Dentro del modelo también se encuentran dos diferentes excepciones personalizadas que se utilizan para controlar
funcionamientos dentro del proyecto: CantidadNegativaException, sirve para controlar y verificar que el usuario
no ingrese valores negativos, ya sea para precios, cantidad o propina; StockInsuficienteException, se encarga
de controlar que las cantidades de productos que se pidan no sobrepasen los valores que hay en stock, como así
también se encarga de dar aviso si el stock de agún producto está por debajo de 5.

## UI

<img width="329" height="154" alt="image" src="https://github.com/user-attachments/assets/67d1b722-ee73-4815-9f55-64149ac5aa87" />

Package encargado de contener las distintas ventanas del programa y se compone de la siguiente manera:

### FormIngreso
Ventana principal a ejecutar, tiene como objetivo controlar que el usuario ingrese al sistema de manera segura.
Evita ingresos de personas al azar solicitando un nombre de usuario y contraseña contenidos en la base de datos
del restaurante.

### FormPrincipal
Es el main o ventana principal en dónde todo el programa se ejecuta. Contiene diferentes pestañas que sirven para
organizar mejor el contenido de cada sector del restaurante siendo estas la administración de reservas, mesas,
pedidos, carta de productos e historial de ventas.

### DialogConfirmFactura
Una ventana encargada de confirmar la creación de una factura virtual que muestra al detalle todo lo consumido
por una mesa en hora de servicio. Dentro se puede ingresar el nombre del cliente y la propina brindada por el
mismo.

## Requisistos para ejecutar el proyecto
Para poder ejecutar este proyecto es necesario tener una base de datos llamada restaurante en MariaDB, el programa creará las tablas necesarias dentro de la base de datos de manera automática.
Se debe ejecutar la ventana FormIngreso en primera instancia, se mostrará un mensaje con los datos necesarios para ingresar. 

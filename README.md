# Screenmatch Animes
Este proyecto forma parte del curso de Alura Latam ONE (Oracle Next Education). Fue desarrollado utilizando Java, el framework Spring, y PostgreSQL para el lado del servidor. La aplicación obtiene datos de animes conectándose a la API de OMDB, los cuales son posteriormente guardados en la base de datos PostgreSQL y mostrados en el navegador.

Para el front-end se utilizó JavaScript, HTML y CSS. Esta aplicación web emula una plataforma para ver animes.

## Imagenes del proyecto
<img width="20%" src="https://github.com/IsaacCuautle/Screenmatch-animes/assets/65583500/99116ce8-4a5c-4b9e-9bc9-9485626bad58"/>
<img width="30%" src="https://github.com/IsaacCuautle/Screenmatch-animes/assets/65583500/80c64ef4-7396-4d95-b2a5-0bb7bb3859df"/>
<img width="45%" src="https://github.com/IsaacCuautle/Screenmatch-animes/assets/65583500/098bceff-7e80-46bb-857f-e3b09b8647a7"/>

## ¿Cómo Funciona?
- Obtención de Datos: <br>
La aplicación utiliza la dependencia org.springframework.boot:spring-boot-starter-web para crear un servidor web que obtiene datos de la [API de OMDB](https://www.omdbapi.com/) y los almacena en una base de datos PostgreSQL mediante org.springframework.boot:spring-boot-starter-data-jpa.

- Interfaz de Usuario: <br>
Se emplean tecnologías como HTML, CSS y JavaScript para construir una interfaz de usuario interactiva en el navegador.

- Visualización de Información: <br>
Al hacer clic en un póster de anime, se muestra una ventana emergente con sinopsis y episodios disponibles, que pueden filtrarse por temporada.

### Dependencias Utilizadas
- org.postgresql
- org.springframework.boot
- spring-boot-devtools
- spring-boot-starter-web
- spring-boot-starter-data-jpa

## ¿Cómo usar el proyecto?

1. Clone o descargue el proyecto desde [este enlace](https://github.com/IsaacCuautle/Screenmatch-animes/releases/download/v1.0.0/Screenmatch-animes-main.zip).
2. Abra la carpeta del proyecto en IntelliJ y configure los archivos `.env` y `application.properties`.
3. Ejecute la clase `ScreenmatchApplication.java` en IntelliJ.
4. En Visual Studio Code, vaya al directorio `screenmatch-front-end` y utilice la extensión Live Server para ejecutar el archivo `index.html`.

> **Nota:**
>  - Asegúrese de tener PostgreSQL instalado, ya que la base de datos se generará al ejecutar la aplicación en IntelliJ.
>  - Descomente la implementación de CommandLineRunner en la clase `ScreenmatchApplication` para acceder a los métodos necesarios para poblar la base de datos o ajustar las preferencias del contenido de la aplicación.

## Tecnologias utilizadas
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white) 
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

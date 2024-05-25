package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.*;
import com.aluracursos.screenmatch.repositorio.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private Dotenv dotenv = Dotenv.load();
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repository;
    private List<Serie> series;
    private Optional<Serie> serieBuscada;

    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 -  Buscar series por titulo 
                    5 -  Top 5 mejores series
                    6 -  Buscar por genero/categoria
                    7 -  Buscar por temporada y evaluacion
                    8 -  Buscar por nombre del episodio
                    9 -  Buscar top 5 episodios por serie
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 6:
                    buscarSeriePorCategoria();
                    break;
                case 7:
                    buscarSeriePorEvaluacionYTemporada();
                    break;
                case 8:
                    buscarPorNombreEpisodio();
                    break;
                case 9:
                    buscarTop5EpisodiosPorSerie();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }


    // Busca una serie y la guarda en la DB
    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        System.out.println(serie);
        repository.save(serie);
    }

    // Obtiene los datos de la serie mediante una consulta al api de OMDB
    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&apikey=" + dotenv.get("apikey_omdb"));
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    // Trae los episodios de una serie disponible en la BD
    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.print("Escribe el nombre de la serie, que contiene los episodios: ");
        var nombreSerie = teclado.nextLine();
        teclado.nextLine();
        serieBuscada = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if (serieBuscada.isPresent()) {
            var serieEncontrada = serieBuscada.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();
            System.out.println(temporadas);
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); ++i) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + (Integer) i + "&apikey=" + dotenv.get("apikey_omdb"));

                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(e -> new Episodio((Integer) t.numero(),e)))
                            .collect(Collectors.toList());

            serieEncontrada.setEpisodio(episodios);
            repository.save(serieEncontrada);
        }

    }

    // Buscar una serie por coincidencias en su titulo
    private void buscarSeriePorTitulo() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.next();
        teclado.nextLine();

        serieBuscada = repository.findByTituloContainsIgnoreCase(nombreSerie);
        if (serieBuscada.isPresent()) {
            System.out.println("La serie buscada es: " + serieBuscada.get());
        } else {
            System.out.println("Serie no encontrada");
        }

    }


    // Muestra las series que fueron buscadas anteriormente
    private void mostrarSeriesBuscadas() {
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void top5MejoresSeries() {
        List<Serie> topSeries = repository.findTop5ByOrderByEvaluacionDesc();

        topSeries.forEach(s -> System.out.println("Titilo: " + s.getTitulo() + " Evaluacion: " + s.getEvaluacion()));
    }

    private void buscarSeriePorCategoria()
    {
        System.out.println("Escribe el genero/categoria de la serie que buscas:" );
        var genero = teclado.next();
        teclado.nextLine();

        var categoria = Categoria.fromEspamol(genero);
        List<Serie> seriePorCategoria = repository.findByGenero(categoria);

        System.out.println("Las series con la categoria:" + genero);
        seriePorCategoria.forEach(s -> System.out.println(s.getTitulo()));
    }

    private void buscarSeriePorEvaluacionYTemporada()
    {
        System.out.println("Ingresa el total de temporas a buscar: ");
        var totalTemporadas = teclado.nextInt();
        teclado.nextLine();

        System.out.println("Ingresa la evaluacion a buscar: ");
        var evaluacion = teclado.nextDouble();
        teclado.nextLine();

        List<Serie> seriePorCategoria = repository.SeriesPorTemporadaYEvaluacion(totalTemporadas, evaluacion);
        seriePorCategoria.forEach(s -> System.out.println("Titulo: "+s.getTitulo()+" Temporadas: "+s.getTotalTemporadas()+" Evaluacion: "+s.getEvaluacion()));
    }

    private void buscarPorNombreEpisodio() {
        System.out.println("Ingresa el nombre del episodio que deseas buscar: ");
        var nombreEpisodio = (String) teclado.nextLine();

        List<Episodio> episodiosEncontrados = repository.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach( e -> System.out.printf("Serie: %s | Episodio: %s | Temporada: %s | Evaluacion: %s | \n",
                e.getSerie().getTitulo(),
                e.getTitulo(),
                e.getTemporada(),
                e.getEvaluacion()));
    }

    private void buscarTop5EpisodiosPorSerie() {
         buscarSeriePorTitulo();
         if (serieBuscada.isPresent()){
             Serie serie = serieBuscada.get();
             System.out.println(serie);
             List<Episodio> topEpisodios = repository.top5Episodios(serie);

             topEpisodios.forEach(e -> System.out.println("""
                        Serie: %s
                        Episodio: %s
                     """.formatted(e.getSerie().getTitulo(),e.getTitulo(),e.getEvaluacion())));
         }
    }

}


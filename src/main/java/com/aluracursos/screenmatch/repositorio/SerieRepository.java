package com.aluracursos.screenmatch.repositorio;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long>
{
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    // Devuelve las 5 mejores series evaluadas en orden decendente
    List<Serie> findTop5ByOrderByEvaluacionDesc();

    // Filtra series por una categoria espeficica
    List<Serie> findByGenero(Categoria categoria);

    // Native query SQL
//    @Query(value = "select * from series where series.total_temporadas <= 6 and series.evaluacion >= 7.5"
//            , nativeQuery = true)

    // JPQL
    // Filtra series por temporada y evaluacion
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas >= :totalTemporadas and s.evaluacion >= :evaluacion")
    List<Serie> SeriesPorTemporadaYEvaluacion(int totalTemporadas, double evaluacion);

//    @Query("SELECT e From Serie s JOIN Episodios e WHERE e.titulo ILIKE %:nombreEpisodio%" )
    // Busca un episodio por nombre
    @Query("SELECT e From Serie s JOIN s.episodio e WHERE e.titulo ILIKE %:nombreEpisodio%" )
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    // Filtra los 5 mejores episodios de una serie
    @Query("SELECT e From Serie s JOIN s.episodio e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);

    // Lanzamientos mas recientes
    @Query("SELECT s FROM Serie s " +
            " JOIN s.episodio e " +
            " GROUP BY s " +
            " ORDER BY MAX(e.fechaDeLanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosRecientes();

    // Buscar por temporada
    @Query("SELECT e FROM Serie s " +
            "JOIN s.episodio e " +
            "WHERE s.id = :id AND e.temporada = :numero")
    List<Episodio> obtenerTemporada(long id, long numero);
}

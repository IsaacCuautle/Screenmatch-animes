package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repositorio.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SerieService
{

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> convierteDatos(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getEvaluacion(),
                        s.getPoster(),
                        s.getGenero(),
                        s.getActores(),
                        s.getSinopsis()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSereies()
    {
        return convierteDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5(){
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());

    };

    public List<SerieDTO> obtenerLanzamientosRecientes()
    {
        return convierteDatos(repository.lanzamientosRecientes());
    }

    public SerieDTO obtenerSerie(long id)
    {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent())
        {
            Serie s = serie.get();
            return new SerieDTO(
                    s.getId(),
                    s.getTitulo(),
                    s.getTotalTemporadas(),
                    s.getEvaluacion(),
                    s.getPoster(),
                    s.getGenero(),
                    s.getActores(),
                    s.getSinopsis());
        }else{
            return null;
        }
    }

    public List<EpisodioDTO> obtenerTemporadas(Long id)
    {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent())
        {
            Serie s = serie.get();
            return s.getEpisodio().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }else{
            return null;
        }
    }

    public List<EpisodioDTO> obtenerTemporada(long id, long numero)
    {
        return repository.obtenerTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriesCategoria(String categoria) {
        Categoria genero = Categoria.fromEspamol(categoria);
        return convierteDatos(repository.findByGenero(genero));
    }
}

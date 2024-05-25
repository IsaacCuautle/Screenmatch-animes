package com.aluracursos.screenmatch.controller;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/series")
public class SerieController
{

    @Autowired
    private SerieService service;

    @GetMapping()
    public List<SerieDTO> obtenerSeries()
    {
        return service.obtenerSereies();
    }

    @GetMapping("/top5")
    public List<SerieDTO> top5Series()
    {
        return service.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> recientes()
    {
        return  service.obtenerLanzamientosRecientes();
    }

    @GetMapping("/{id}")
    public SerieDTO obtenerSerie(@PathVariable Long id)
    {
        return  service.obtenerSerie(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obtnerTemporadas(@PathVariable Long id)
    {
        return service.obtenerTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obtenerTemporada(@PathVariable long id,@PathVariable long numero)
    {
        return service.obtenerTemporada(id, numero);
    }

    @GetMapping("/categoria/{genero}")
    public List<SerieDTO> obtenerSeriesCategoria(@PathVariable String genero)
    {
        return service.obtenerSeriesCategoria(genero);
    }
}

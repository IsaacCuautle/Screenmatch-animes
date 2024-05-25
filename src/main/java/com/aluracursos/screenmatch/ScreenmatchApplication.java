package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.repositorio.SerieRepository;
import com.aluracursos.screenmatch.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.util.WebUtils;

@SpringBootApplication
//public class ScreenmatchApplication implements  CommandLineRunner{
public class ScreenmatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

//	@Autowired
//	private SerieRepository repository;
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchApplication.class, args);
//	}

//	public void run(String[] args) {
//		Principal principal = new Principal(repository);
//		principal.muestraElMenu();
//	}

}

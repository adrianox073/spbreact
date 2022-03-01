package com.personal.springboot.reactor.app;

import com.personal.springboot.reactor.app.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.Locale;

@SpringBootApplication
@Slf4j
public class SpringBootReactorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) {

		Flux<String> nombres = Flux.just("A 1", "C 2", "D 3", "E 4", "F 5", "A 11");

		Flux<Usuario> usuarios =
				nombres.map(u -> new Usuario(u.split(" ")[0].toUpperCase(), u.split(" ")[1].toUpperCase()))
				.filter(u -> u.getNombre().equals("A"))
				.doOnNext(
						elemento -> {
							if (elemento.getNombre().isEmpty()) {
								throw new RuntimeException("Elemento vacio");
							}
							System.out.println(elemento.getNombre().concat(" ").concat(elemento.getApellido()));
						}
		).map(usuario -> {
			usuario.setNombre(usuario.getNombre().toLowerCase());
			return usuario;
		});

		usuarios.subscribe(e -> log.info(e.toString()), err -> log.error(err.getMessage()), () -> log.info("Ha finalizado ")
		);
	}
}

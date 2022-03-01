package com.personal.springboot.reactor.app;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Slf4j
public class SpringBootReactorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Flux<String> nombres = Flux.just("A", "C", "D", "D1", "E").doOnNext(

						elemento -> {
							if (elemento.isEmpty()) {
								throw new RuntimeException("Elemento vacio");
							} else
							{

								System.out.println(elemento); }
						}
		);

		nombres.subscribe(e -> log.info(e), err -> log.error(err.getMessage()), new Runnable () {

					@Override
					public void run() {
                           log.info("Ha finalizado ");
					}
				}
		);
	}
}

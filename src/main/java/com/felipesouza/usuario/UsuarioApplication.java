//Classe principal que inicia todos os microserviços

package com.felipesouza.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication	//Indica que é uma aplicação do spring e faz as configurações necessárias
public class UsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioApplication.class, args);
	}
}
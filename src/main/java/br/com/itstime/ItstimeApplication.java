package br.com.itstime;

import br.com.itstime.model.Usuario;
import br.com.itstime.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class ItstimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItstimeApplication.class, args);
	}

	@Bean
	public CommandLineRunner demonstracao(UsuarioService usuarioService) {
		return args -> {
			Scanner sc = new Scanner(System.in);

			System.out.println("\n=== CADASTRO DE USUARIO ===");

			System.out.print("Nome: ");
			String nome = sc.nextLine();

			System.out.print("Email: ");
			String email = sc.nextLine();

			System.out.print("Senha: ");
			String senha = sc.nextLine();

			try {
				Usuario u = usuarioService.cadastrar(nome, email, senha);
				System.out.println("\nUsuario cadastrado! ID: " + u.getId());
			} catch (RuntimeException e) {
				System.out.println("\nErro: " + e.getMessage());
			}
			sc.close();
		};
	}
}
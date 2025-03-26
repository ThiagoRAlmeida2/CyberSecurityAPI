/* package CyberSecurity.scr.cli;

import CyberSecurity.scr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@Component
public class UserCli implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public UserCli(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Registrar usuário");
            System.out.println("2. Fazer login");
            System.out.println("3. Excluir usuario:");
            System.out.println("4. Listar todos os usuários");
            System.out.println("5. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do usuário:");
                    String nomeRegistro = scanner.nextLine();
                    System.out.println("Digite a senha:");
                    String senhaRegistro = scanner.nextLine();
                    String resultadoRegistro = userService.registerUser(nomeRegistro, senhaRegistro);
                    System.out.println(resultadoRegistro);
                    break;
                case 2:
                    System.out.println("Digite o nome do usuário:");
                    String nomeLogin = scanner.nextLine();
                    System.out.println("Digite a senha:");
                    String senhaLogin = scanner.nextLine();

                    // Chame o serviço web de login
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "http://localhost:8080/login?nome=" + nomeLogin + "&senha=" + senhaLogin;
                    boolean resultadoLogin = Boolean.TRUE.equals(restTemplate.postForObject(url, null, boolean.class));

                    if (resultadoLogin) {
                        System.out.println("Login bem-sucedido!");
                    } else {
                        System.out.println("Login falhou.");
                    }
                    break;
                case 3:
                    System.out.println("Digite o nome do usuário a ser excluído:");
                    String nomeExcluir = scanner.nextLine();
                    String resultadoExclusao = userService.excluirUsuario(nomeExcluir);
                    System.out.println(resultadoExclusao);
                    break;
                case 4:
                    System.out.println("Lista de usuários:");
                    userService.listarTodosUsuarios().forEach(user ->
                            System.out.println("Nome: " + user.getNome())
                    );
                    break;
                case 5:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
*/
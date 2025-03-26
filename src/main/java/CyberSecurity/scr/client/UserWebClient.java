package CyberSecurity.scr.client;

import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class UserWebClient {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Registrar usuário");
            System.out.println("2. Fazer login");
            System.out.println("3. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do usuário:");
                    String nomeRegistro = scanner.nextLine();
                    System.out.println("Digite a senha:");
                    String senhaRegistro = scanner.nextLine();

                    String registerUrl = "http://localhost:8080/register-submit?nome=" + nomeRegistro + "&senha=" + senhaRegistro;
                    String registerResponse = restTemplate.postForObject(registerUrl, null, String.class);

                    if (registerResponse != null) {
                        System.out.println(registerResponse);
                    } else {
                        System.out.println("Erro ao registrar usuário.");
                    }
                    break;
                case 2:
                    System.out.println("Digite o nome do usuário:");
                    String nomeLogin = scanner.nextLine();
                    System.out.println("Digite a senha:");
                    String senhaLogin = scanner.nextLine();

                    String loginUrl = "http://localhost:8080/login-submit?nome=" + nomeLogin + "&senha=" + senhaLogin;
                    String loginResponse = restTemplate.postForObject(loginUrl, null, String.class);

                    if (loginResponse != null) {
                        System.out.println(loginResponse);
                    } else {
                        System.out.println("Erro ao fazer login.");
                    }
                    break;
                case 3:
                    System.out.println("Saindo...");
                    return;
                case 4:
                    System.out.println("Digite o nome do usuário a ser excluído:");
                    String nomeExcluir = scanner.nextLine();

                    String excluirUrl = "http://localhost:8080/excluir-usuario?nome=" + nomeExcluir;
                    String excluirResponse = restTemplate.postForObject(excluirUrl, null, String.class);

                    if (excluirResponse != null) {
                        System.out.println(excluirResponse);
                    } else {
                        System.out.println("Erro ao excluir usuário.");
                    }
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
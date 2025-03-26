package CyberSecurity.scr.service;

import CyberSecurity.scr.exeption.UserNotFoundException;
import CyberSecurity.scr.model.User;
import CyberSecurity.scr.repository.UserRepository;
import CyberSecurity.scr.util.HashUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Map<String, Integer> tentativasLogin = new HashMap<>();
    private final Map<String, LocalDateTime> ultimoLoginFalho = new HashMap<>();
    private static final int LIMITE_TENTATIVAS = 2;
    private static final int TEMPO_ESPERA = 60; // segundos

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(String nome, String senha) {
        String senhaCriptografada = HashUtil.hashSenha(senha);
        System.out.println("Senha criptografada para " + nome + ": " + senhaCriptografada);
        User novoUsuario = new User(nome, senhaCriptografada);
        userRepository.save(novoUsuario);
        return "Usuário cadastrado com sucesso!";
    }

    public boolean loginUser(String nome, String senha, HttpServletRequest request) { // Certifique-se de que o parâmetro está correto
        String ipAddress = request.getRemoteAddr();
        String location = getLocation(ipAddress);
        System.out.println("Tentando login para: " + nome + " - IP: " + ipAddress + " - Localização: " + location);
        System.out.println("Tentativas atuais: " + tentativasLogin.get(nome));
        System.out.println("Ultimo login falho: " + ultimoLoginFalho.get(nome));

        User user = userRepository.findByNome(nome);
        if (user != null) {
            if (tentativasLogin.containsKey(nome) && tentativasLogin.get(nome) >= LIMITE_TENTATIVAS) {
                LocalDateTime agora = LocalDateTime.now();
                LocalDateTime tempoEspera = ultimoLoginFalho.get(nome).plusSeconds(TEMPO_ESPERA);

                System.out.println("Limite de tentativas excedido para o usuário: " + nome);
                System.out.println("Tempo de espera: " + tempoEspera);

                if (agora.isBefore(tempoEspera)) {
                    System.out.println("Login bloqueado devido a muitas tentativas. Tempo restante: " + java.time.Duration.between(agora, tempoEspera).getSeconds() + " segundos.");
                    return false;
                } else {
                    System.out.println("Tempo de espera decorrido. Reiniciando tentativas.");
                    tentativasLogin.put(nome, 0);
                }
            }

            if (HashUtil.verificarSenha(user.getSenha(), senha)) {
                System.out.println("Senha criptografada do banco de dados: " + user.getSenha());
                tentativasLogin.remove(nome);
                ultimoLoginFalho.remove(nome);
                return true;
            } else {
                System.out.println("Senha criptografada do banco de dados: " + user.getSenha());
                tentativasLogin.put(nome, tentativasLogin.getOrDefault(nome, 0) + 1);
                ultimoLoginFalho.put(nome, LocalDateTime.now());
                return false;
            }
        } else {
            System.out.println("Usuário não encontrado: " + nome);
            return false;
        }
    }

    public User buscarUsuarioPorNome(String nome) {
        User user = userRepository.findByNome(nome);
        if (user == null) {
            throw new UserNotFoundException("Usuário não encontrado com o nome: " + nome);
        }
        return user;
    }

    public String excluirUsuario(String nome) {
        try {
            User user = userRepository.findByNome(nome);
            if (user == null) {
                throw new UserNotFoundException("Usuário não encontrado com o nome: " + nome);
            }
            userRepository.delete(user);
            return "Usuário excluído com sucesso!";
        } catch (Exception e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
            return "Erro ao excluir usuário.";
        }
    }

    public List<User> listarTodosUsuarios() {
        return userRepository.findAll();
    }

    private String getLocation(String ipAddress) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://ipinfo.io/" + ipAddress + "/json";
        String location = "Localização não encontrada"; // Valor padrão
        try {
            String json = restTemplate.getForObject(url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            if (jsonNode != null && jsonNode.has("city")) {
                location = jsonNode.get("city").asText();
            }
        } catch (IOException e) {
            System.err.println("Erro ao obter localização do IP: " + e.getMessage());
        }
        return location;
    }
}
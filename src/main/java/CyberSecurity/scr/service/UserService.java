package CyberSecurity.scr.service;

import CyberSecurity.scr.exeption.UserNotFoundException;
import CyberSecurity.scr.model.User;
import CyberSecurity.scr.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final Map<String, Integer> tentativasLogin = new HashMap<>();
    private final Map<String, LocalDateTime> ultimoLoginFalho = new HashMap<>();
    private static final int LIMITE_TENTATIVAS = 2;
    private static final int TEMPO_ESPERA = 60; // segundos
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(String nome, String senha) {
        String senhaCriptografada = passwordEncoder.encode(senha);
        logger.info("Senha criptografada para {}: {}", nome, senhaCriptografada);
        User novoUsuario = new User(nome, senhaCriptografada);
        userRepository.save(novoUsuario);
        return "Usuário cadastrado com sucesso!";
    }

    public boolean loginUser(String nome, String senha, HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        String location = getLocation(ipAddress);
        logger.info("Tentando login para: {} - IP: {} - Localização: {}", nome, ipAddress, location);
        logger.info("Tentativas atuais: {}", tentativasLogin.get(nome));
        logger.info("Ultimo login falho: {}", ultimoLoginFalho.get(nome));

        User user = userRepository.findByNome(nome);
        if (user != null) {
            if (tentativasLogin.containsKey(nome) && tentativasLogin.get(nome) >= LIMITE_TENTATIVAS) {
                LocalDateTime agora = LocalDateTime.now();
                LocalDateTime tempoEspera = ultimoLoginFalho.get(nome).plusSeconds(TEMPO_ESPERA);

                logger.warn("Limite de tentativas excedido para o usuário: {}", nome);
                logger.warn("Tempo de espera: {}", tempoEspera);

                if (agora.isBefore(tempoEspera)) {
                    logger.warn("Login bloqueado devido a muitas tentativas. Tempo restante: {} segundos.", java.time.Duration.between(agora, tempoEspera).getSeconds());
                    //EmailService.sendAlert(email, ipAddress); // Removido: email não está disponível aqui
                    return false;
                } else {
                    logger.info("Tempo de espera decorrido. Reiniciando tentativas.");
                    tentativasLogin.put(nome, 0);
                }
            }

            if (passwordEncoder.matches(senha, user.getSenha())) {
                logger.info("Login bem-sucedido para o usuário: {}", nome);
                tentativasLogin.remove(nome);
                ultimoLoginFalho.remove(nome);
                return true;
            } else {
                logger.warn("Senha incorreta para o usuário: {}", nome);
                tentativasLogin.put(nome, tentativasLogin.getOrDefault(nome, 0) + 1);
                ultimoLoginFalho.put(nome, LocalDateTime.now());
                return false;
            }
        } else {
            logger.warn("Usuário não encontrado: {}", nome);
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
            logger.warn("Excluindo usuário: {}", nome);
            User user = userRepository.findByNome(nome);
            if (user == null) {
                throw new UserNotFoundException("Usuário não encontrado com o nome: " + nome);
            }
            userRepository.delete(user);
            return "Usuário excluído com sucesso!";
        } catch (Exception e) {
            logger.error("Erro ao excluir usuário: {}", e.getMessage());
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
            logger.error("Erro ao obter localização do IP: {}", e.getMessage());
        }
        return location;
    }

    // Métodos removidos do AuthService
}
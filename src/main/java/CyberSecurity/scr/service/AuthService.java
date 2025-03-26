package CyberSecurity.scr.service;

import CyberSecurity.scr.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private static final int MAX_ATTEMPTS = 5;
    private static final Map<String, Integer> attempts = new HashMap<>();
    private static final Map<String, LocalDateTime> blockedIps = new HashMap<>();

    private final List<User> users = new ArrayList<>();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(String nome, String senha) {
        String senhaCriptografada = passwordEncoder.encode(senha);
        users.add(new User(nome, senhaCriptografada));
        return "Usuário cadastrado com sucesso!";
    }

    public String login(String nome, String senha, String email) throws UnknownHostException {
        String ip = getClientIp();

        if (isBlocked(ip)) {
            return "IP bloqueado. Tente novamente mais tarde.";
        }

        if (!validateUser(nome, senha)) {
            trackAttempt(ip, email);
            return "Usuário ou senha incorretos.";
        }

        resetAttempts(ip);
        return "Login bem-sucedido.";
    }

    private boolean validateUser(String nome, String senha) {
        for (User user : users) {
            if (user.getNome().equals(nome) && passwordEncoder.matches(senha, user.getSenha())) {
                return true;
            }
        }
        return false;
    }

    private void trackAttempt(String ip, String email) {
        int currentAttempts = attempts.getOrDefault(ip, 0) + 1;
        attempts.put(ip, currentAttempts);

        if (currentAttempts >= MAX_ATTEMPTS) {
            int penaltyMinutes = (currentAttempts - MAX_ATTEMPTS + 1) * 5;
            blockedIps.put(ip, LocalDateTime.now().plusMinutes(penaltyMinutes));
            EmailService.sendAlert(email, ip);
        }
    }

    private boolean isBlocked(String ip) {
        return blockedIps.containsKey(ip) && blockedIps.get(ip).isAfter(LocalDateTime.now());
    }

    private void resetAttempts(String ip) {
        attempts.remove(ip);
        blockedIps.remove(ip);
    }

    private String getClientIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
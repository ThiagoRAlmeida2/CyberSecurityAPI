package CyberSecurity.scr.service;

import CyberSecurity.scr.util.HashUtil;
import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private static final int MAX_ATTEMPTS = 5;
    private static final Map<String, Integer> attempts = new HashMap<>();
    private static final Map<String, LocalDateTime> blockedIps = new HashMap<>();

    public String encryptPassword(String password) {
        return HashUtil.sha256(password);
    }

    public String login(String username, String password, String email) throws UnknownHostException {
        String ip = getClientIp();

        if (isBlocked(ip)) {
            return "IP bloqueado. Tente novamente mais tarde.";
        }

        if (!validateUser(username, password)) {
            trackAttempt(ip, email);
            return "Usuário ou senha incorretos.";
        }

        resetAttempts(ip);
        return "Login bem-sucedido.";
    }

    private boolean validateUser(String username, String password) {
        return "admin".equals(username) && HashUtil.sha256(password).equals(HashUtil.sha256("admin123"));
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


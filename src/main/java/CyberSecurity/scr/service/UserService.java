package CyberSecurity.scr.service;

import CyberSecurity.scr.exeption.UserNotFoundException;
import CyberSecurity.scr.model.User;
import CyberSecurity.scr.repository.UserRepository;
import CyberSecurity.scr.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    public String registerUser(String nome, String senha) {
        String senhaCriptografada = HashUtil.hashSenha(senha); // Usa Argon2 para hashear
        User novoUsuario = new User(nome, senhaCriptografada);
        userRepository.save(novoUsuario);
        return "Usuário cadastrado com sucesso!";
    }

    public boolean loginUser(String nome, String senha) {
        User user = userRepository.findByNome(nome);
        if (user != null) {
            return HashUtil.verificarSenha(user.getSenha(), senha); // Verifica senha com Argon2
        }
        return false;
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User buscarUsuarioPorNome(String nome) {
        User user = userRepository.findByNome(nome);
        if (user == null) {
            throw new UserNotFoundException("Usuário nao encontrado com o nome: " + nome);
        }
        return user;
    }

    public List<User> listarTodosUsuarios() {
        return userRepository.findAll();
    }
}
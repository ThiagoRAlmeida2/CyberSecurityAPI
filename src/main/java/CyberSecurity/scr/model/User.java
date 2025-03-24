package CyberSecurity.scr.model;

public class User {
    private String nome;
    private String senha;

    public User(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }
}

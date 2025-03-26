package CyberSecurity.scr.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Optional: Specify table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    // Constructors, getters, setters...

    public User() {
    }

    public User(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
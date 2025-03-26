package CyberSecurity.scr.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class HashUtil {

    private static final Argon2 argon2 = Argon2Factory.create();

    // Método para gerar hash da senha
    public static String hashSenha(String senha) {
        return argon2.hash(2, 65536, 1, senha); // 2 iterações, 64MB de memória, 1 thread
    }

    // Método para verificar se a senha está correta
    public static boolean verificarSenha(String hash, String senha) {
        return argon2.verify(hash, senha);
    }
}
# CyberSecurity Project

Este projeto implementa funcionalidades básicas de segurança, incluindo criptografia de senhas, autenticação de usuários, controle de tentativas de login, e envio de alertas por e-mail em caso de múltiplas tentativas de login. O projeto utiliza o Spring Boot para o desenvolvimento do back-end, aplicando boas práticas de segurança.

## Funcionalidades

- **Criptografia de Senha**: Utiliza o algoritmo SHA-256 para criptografar senhas.
- **Autenticação de Usuários**: Valida credenciais de login e limita tentativas de login para prevenir ataques de força bruta.
- **Controle de Tentativas de Login**: Bloqueia o IP de um usuário após múltiplas tentativas de login falhas.
- **Envio de Alertas**: Envia um alerta por e-mail ao administrador quando há múltiplas tentativas de login suspeitas de um mesmo IP.
- **Spring Security**: Configuração do Spring Security para proteger rotas e habilitar login com um formulário personalizado.

## Estrutura do Projeto

### 1. **Configuração de Segurança** (`SecurityConfig.java`)

Configura o Spring Security para:
- Desabilitar CSRF (se não necessário).
- Permitir acesso público a rotas específicas (`/public/**`).
- Exigir autenticação para qualquer outra requisição.
- Configurar um login com página personalizada.
- Permitir logout.

### 2. **Controlador de Autenticação** (`AuthController.java`)

Define rotas para:
- **Criptografar senhas**: `POST /auth/encrypt`
- **Login**: `POST /auth/login`, valida as credenciais de usuário e senha, e controla as tentativas de login.

### 3. **Serviço de Autenticação** (`AuthService.java`)

Responsável por:
- **Criptografar senhas** com o SHA-256.
- **Validar credenciais** de login.
- **Rastrear tentativas** de login para bloquear IPs após múltiplas tentativas falhas.
- **Enviar alertas** por e-mail em caso de tentativas de login suspeitas.

### 4. **Serviço de Envio de E-mail** (`EmailService.java`)

Responsável por enviar e-mails de alerta em caso de múltiplas tentativas de login de um mesmo IP.

### 5. **Utilitário de Hash** (`HashUtil.java`)

Fornece a funcionalidade de gerar um hash SHA-256 a partir de uma senha fornecida.

### 6. **Aplicação Principal** (`ScrApplication.java`)

Classe principal que inicializa o Spring Boot.

## Como Rodar o Projeto

### Requisitos

- JDK 11 ou superior.
- Maven ou Gradle (dependendo de sua configuração).
- Uma conta de e-mail válida para envio de alertas.

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/ThiagoRAlmeida23/CyberSecurityAPI.git
   ```
2. Navegue até a pasta do projeto:
   ```bash   
    cd CyberSecurity.scr
    ```
3. Execute o projeto:
   ```bash
    mvn spring-boot:run
   ```
4. Acesse a aplicação no navegador em: http://localhost:8080.

## Rotas disponiveis

* POST "/auth/encrypt" – Criptografa a senha enviada no corpo da requisição.
* POST "/auth/login" – Realiza o login com os parâmetros username, password e email. Se houver falhas repetidas de login, o IP será bloqueado temporariamente e um alerta será enviado.

## Configuração de E-mail:

O serviço de envio de alertas por e-mail está configurado para usar o Gmail. Para que o envio funcione, substitua as credenciais do e-mail na classe EmailService.java:

```bash
private static final String senderEmail = "seu-email@gmail.com";
private static final String senderPassword = "sua-senha";
```

 * Observação: Para contas do Gmail, é necessário ativar o acesso a aplicativos menos seguros ou configurar a autenticação de dois fatores e gerar uma senha de aplicativo.

## Contibuindo

1. Faça um fork do projeto.
2. Crie uma branch para a sua feature (git checkout -b feature/nova-feature).
3. Faça commit das suas mudanças (git commit -am 'Adiciona nova feature').
4. Faça push para a branch (git push origin feature/nova-feature).
5. Abra um Pull Request.


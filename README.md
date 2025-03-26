# Projeto CyberSecurity.scr

Este projeto Spring Boot fornece uma API REST para gerenciamento de usuários com foco em segurança cibernética. Ele inclui funcionalidades para registro, login e busca de usuários, além de implementar medidas de segurança como criptografia de senhas usando BCrypt e proteção contra ataques de força bruta.

## Funcionalidades
- **Registro de Usuário**: Permite o registro de novos usuários com senhas criptografadas usando BCrypt.
- **Login de Usuário**: Permite que usuários autenticados façam login na API.
- **Busca de Usuário**: Permite buscar usuários por nome.
- **Listagem de Usuários**: Permite listar todos os usuários cadastrados.
- **Proteção contra Força Bruta**: Implementa um sistema de bloqueio de IP após múltiplas tentativas de login falhas.
- **Alerta por E-mail**: Envia um e-mail de alerta quando um IP é bloqueado devido a tentativas de login suspeitas.

## Tecnologias
- Spring Boot
- Spring Security
- BCryptPasswordEncoder
- JPA (Java Persistence API)
- MySQL (ou outro banco de dados compatível com JPA)
- Java Mail API (para envio de e-mails)

## Estrutura do Projeto

```
CyberSecurity.scr/
├── config/
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   └── UserController.java
├── exeption/
│   └── UserNotFoundException.java
├── model/
│   └── User.java
├── repository/
│   └── UserRepository.java
├── service/
│   ├── AuthService.java
│   ├── UserService.java
│   └── EmailService.java
├── util/
│   └── HashUtil.java
└── ScrApplication.java
```

- **config/**: Configurações de segurança (SecurityConfig).
- **controller/**: Controladores REST (AuthController, UserController).
- **exeption/**: Exceções personalizadas (UserNotFoundException).
- **model/**: Classes de modelo (User).
- **repository/**: Repositórios JPA (UserRepository).
- **service/**: Serviços de negócio (AuthService, UserService, EmailService).
- **util/**: Classes utilitárias (HashUtil).
- **ScrApplication.java**: Classe principal da aplicação Spring Boot.

## Configuração
### Banco de Dados
Configure as propriedades de conexão com o banco de dados no arquivo `application.properties` ou `application.yml`.

### E-mail
Configure as variáveis de ambiente `EMAIL_USER` e `EMAIL_PASSWORD` com as credenciais do seu servidor de e-mail.

## Como Executar
1. Clone o repositório.
2. Execute o comando:
   ```sh
   mvn spring-boot:run
   ```
   na raiz do projeto.
---
## Endpoints da API
- **POST** `/auth/login` - Realiza o login de um usuário.
- **POST** `/auth/registrar` - Registra um novo usuário.
- **GET** `/api/usuarios/{nome}` - Busca um usuário por nome.
- **GET** `/api/usuarios` - Lista todos os usuários.

## Segurança
- As senhas são criptografadas usando **BCrypt** para armazenamento seguro.
- O sistema de **bloqueio de IP** protege contra ataques de força bruta.
- O envio de **e-mails de alerta** notifica sobre atividades suspeitas.

## Observações
- Este README fornece uma visão geral do projeto. Para detalhes específicos, consulte o código-fonte.
- A implementação de segurança pode ser aprimorada com a adição de mais medidas, como **autenticação de dois fatores** e **políticas de senha mais rigorosas**.

## Classes Principais
### `SecurityConfig.java`
Configura o **BCryptPasswordEncoder** para criptografia de senhas.

### `AuthController.java`
Controlador REST para **autenticação de usuários** (login e registro).

### `UserController.java`
Controlador REST para **gerenciamento de usuários** (busca e listagem).

### `UserNotFoundException.java`
Exceção personalizada para quando um usuário não é encontrado.

### `User.java`
Classe de modelo para representar um usuário.

### `UserRepository.java`
Interface JPA Repository para acesso ao banco de dados de usuários.

### `AuthService.java`
Serviço de negócio para **autenticação de usuários**.

### `UserService.java`
Serviço de negócio para **gerenciamento de usuários**.

### `EmailService.java`
Serviço para **envio de e-mails de alerta**.

### `HashUtil.java`
Classe utilitária para **hashing de senhas**.

### `ScrApplication.java`
Classe principal da aplicação Spring Boot.

## Dependências
- `spring-boot-starter-security`
- `spring-boot-starter-data-jpa`
- `mysql-connector-java` (ou outro driver de banco de dados)
- `spring-boot-starter-mail`
---
# Passo a Passo para Executar o Projeto CyberSecurityAPI

Este guia detalha como configurar e executar o projeto **CyberSecurityAPI** em seu ambiente local.

## Pré-requisitos

Antes de começar, certifique-se de ter os seguintes pré-requisitos instalados:

- **Java JDK**: Certifique-se de ter o Java JDK (versão 17 ou superior) instalado.
- **Maven**: O Maven é necessário para gerenciar as dependências e construir o projeto.
- **Git**: O Git é necessário para clonar o repositório.
- **MySQL**: Um servidor MySQL está em execução para o banco de dados.
- **Conta GitHub**: Uma conta GitHub é necessária para fazer um fork do repositório.
---
## 1. Fork do Repositório

1. Acesse o repositório [CyberSecurityAPI](https://github.com/ThiagoRAlmeida/CyberSecurityAPI) no GitHub.
2. No canto superior direito da página, clique no botão "Fork".
3. Selecione a sua conta GitHub onde deseja fazer o fork.
4. Aguarde alguns segundos enquanto o GitHub cria o fork em sua conta.
---
## 2. Clonar o Repositório Forked

1. Após o fork ser criado, você será redirecionado para a página do seu repositório forked.
2. Clique no botão verde **"Code"** e copie a URL do repositório (HTTPS ou SSH).
3. Abra o terminal ou Git Bash no diretório onde deseja clonar o projeto.
4. Execute o seguinte comando, substituindo `<URL_DO_SEU_REPOSITORIO>` pela URL copiada:

   ```bash
   git clone <URL_DO_SEU_REPOSITORIO>
   ```
5. Navegue até o diretório do projeto clonado:
   ```bash
   cd CyberSecurityAPI
   ```
---
## 3. Configurar o Banco de Dados MySQL

1. Crie um banco de dados MySQL para o projeto.
2. Abra o arquivo `src/main/resources/application.properties`
3. Configure as propriedades de conexão com o banco de dados:
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_seu_banco_de_dados
   spring.datasource.username=seu_usuario_mysql
   spring.datasource.password=sua_senha_mysql
   spring.jpa.hibernate.ddl-auto=update
   ```
Substitua `nome_do_seu_banco_de_dados`, `seu_usuario_mysql` e `sua_senha_mysql` pelas informações corretas.
---
## 5. Executar a Aplicação

1. Abra o terminal na raiz do projeto.
2. Execute o seguinte comando para construir e executar a aplicação:
   ```bash
   mvn spring-boot:run
   ```
3. Aguarde até que a aplicação seja iniciada.
4. A API estará disponível em http://localhost:8080.
---
## 6. Testar a API
Use um cliente HTTP como Postman, Insomnia ou curl para testar os endpoints da API.

Exemplos de endpoints:
* POST /auth/login
* POST /auth/registrar
* GET /api/usuarios/{nome}
* GET /api/usuarios
---
## 7. Contribuir (Opcional)

1. Crie um branch para suas alterações:
   ```bash
   git checkout -b minha-alteracao
   ```

2. Faça suas alterações e commit:
   ```bash
   git add .
   git commit -m "Minha alteração"
   ```
   
3. Envie as suas alterações para o seu repositório forked:
   
   ```bash
   git push origin minha-alteracao
   ```
   
4. Crie um pull request (PR) do seu branch para o branch main do repositório original.

---
## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir **issues** e **pull requests**.

---
## 🔗 Links

[![LinkedIn](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/thiago-ribeiro-139727260/)
[![Gmail](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:thiagoralmeida23@gmail.com)
[![GitHub](https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/ThiagoRAlmeida2)
[![YouTube](https://img.shields.io/badge/YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://www.youtube.com/@Thiago.Ralmeida2)
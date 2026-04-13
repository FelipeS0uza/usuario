# 🚀 Back End de Cadastro de Usuários

## API REST desenvolvida para gerenciamento de usuários, permitindo operações de cadastro, consulta, atualização e remoção.

## 📌 Tecnologias utilizadas
* Java 21
* Spring Boot
* Spring Security
* Spring Web
* Spring Data JPA
* Banco de Dados PostgreSQL
* Gradle
* Lombok

## 📂 Estrutura do projeto
`src/`  
`├── controller/controller        # Camada de controle (endpoints)`  
`├── business/service             # Regras de negócio`  
`├── business/dto                 # Objetos de transferência`  
`├── infrastructure/repository    # Acesso ao banco de dados`  
`├── infrastructure/entity        # Entidades`  
`└── security                     # Autenticação e segurança`  

## ⚙️ Funcionalidades
* ✅ Cadastro de usuário
* 🔍 Busca de usuários
* ✏️ Atualização de dados
* ❌ Remoção de usuário
* 🔐 Autenticação com JWT

## ▶️ Como executar o projeto
* Pré-requisitos
* Java 21 instalado
* Gradle instalado
* IDE (IntelliJ, VS Code, Eclipse)

### Clone o repositório
`git clone https://github.com/seu-usuario/seu-repo.git`

### Acesse a pasta
`cd ./usuario`

### Execute o projeto pela sua IDE

## 🌐 Endpoints principais
|  Método  |   Endpoint       |  Descrição         |
| ------ | -------------- | ----------------- |
| POST   | /usuario             | Cadastrar usuário |
| POST   | /usuario/login       | Login do usuário |
| POST   | /usuario/endereco    | Cadastrar novo endereço |
| POST   | /usuario/telefone    | Cadastrar novo telefone |
| GET    | /usuario?email={email}     | Busca usuário   |
| PUT    | /usuario       | Atualiza usuario por email    |
| PUT    | /usuario/telefone?id={id} | Atualizar telefone |
| PUT    | /usuario/endereco?id={id} | Atualizar endereco |
| DELETE | /usuarios/{email} | Deletar usuário   |

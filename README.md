# Microsserviços - Mony Courses
Este repositório contém a implementação de microsserviços do projeto.

🗃️ Banco de Dados
O banco de dados utilizado é o PostgreSQL.
Para iniciar o projeto, siga os passos abaixo:

✅ Link para instalar o PostgreSQL
https://sbp.enterprisedb.com/getfile.jsp?fileid=125956

Crie o banco de dados:
No PostgreSQL, execute o seguinte comando para criar o banco:

CREATE DATABASE BD;

📦 RabbitMQ
Utilizamos o RabbitMQ como sistema de mensageria entre os microsserviços.

🔗 Instalação
Baixe o RabbitMQ:

Baixar o RabbitMQ Server - https://github.com/rabbitmq/rabbitmq-server/releases/download/v4.1.0/rabbitmq-server-4.1.0.exe
 
Baixar o Erlang - https://github.com/erlang/otp/releases/download/OTP-28.0/otp_win64_28.0.exe        

No terminal como administrador
Instalar Pluggins para Projeto: rabbitmq-plugins enable rabbitmq_management
Iniciar servidor: rabbitmq-service start

🚀 Iniciando os Microsserviços

Com o banco de dados PostgreSQL, inicie cada um dos microsserviços do projeto. Lembre-se de muda a senha no application.properties de cada microsserviços 

📌 Observações
Antes de iniciar os microsserviços, verifique se o banco de dados e o Kafka estão devidamente configurados e em execução.

Em caso de problemas, consulte a documentação oficial do PostgreSQL e do Kafka para obter detalhes sobre configuração e troubleshooting.

📞 Contato:
Em caso de dúvidas ou sugestões, fique à vontade para enviar um email para:
turmadamonycasquad99@gmail.com



## 🧪 Testando a API com Insomnia/Postman

Siga os passos abaixo para testar o sistema utilizando o **Insomnia/Postman**. 

> ⚠️ **Importante:** Todas as requisições após o login exigem o token de autenticação (Bearer Token) no **Header**.
>
> ⚠️ **Atenção:** Baixe e rode os microsserviços do seguinte repositorio: ``` https://github.com/Plataforma-De-Cursos-Online/sistema-de-pagamento.git ```

---

### 👤 Criação de Usuário

**URL:** `POST http://localhost:8081/usuario`  
**Body (JSON):**  

```json
{
  "nome": "Maikon",
  "login": "maikon.projetos@gmail.com",
  "password": "senhaSegura123",
  "tipoUsuario": "professor"
} 
``` 

### 🔐 Login do Usuário

**URL:** `POST http://localhost:8081/usuario/login`  
**Body (JSON):**
``` json
{
  "login": "maikon.projetos@gmail.com",
  "password": "senhaSegura123"
}
``` 

### 🏦 Criação de Conta

**URL:** `POST http://localhost:8080/api/conta`  
**Body (JSON):**
``` json
{
  "dataVencimento": "2025-07-01",
  "limite": 1500.00,
  "agencia": 1234,
  "numero": 1232,
  "banco": 1,
  "tipoCartao": "CREDITO",
  "idUsuario": "COLOQUE ID DO USUARIO CRIADO AQUI"
}
``` 


### 📃 Criação de curso (PRECISA SER UM TOKEN DE UM USUÁRIO DO TIPO PROFESSOR)

**URL:** `POST http://localhost:8080/api/curso-professor`  
**Body (JSON):**
``` json
{
  "titulo": "Dieta",
  "descricao": "Curso completo de dieta",
  "preco": 500,
  "disponivel": true,
  "idUsuario": "COLOQUE ID DO USUARIO CRIADO AQUI",
  "tipoCurso": "COLOQUE ID DO CURSO CRIADO AQUI"
}
``` 

### 💸 Transação

**URL:** `POST http://localhost:8080/api/transacao`  
**Body (JSON):**
``` json
{
  "tipo": "CREDITO",
  "cartao": "COLOQUE ID DO CARTÃO CRIADO AUTOMATICAMENTE AQUI",
  "curso": "COLOQUE ID DO CURSO CRIADO AQUI"
}
```


### 📕 Realizar matricula

**URL:** `POST http://localhost:8080/api/matricula`  
**Body (JSON):**
``` json
{
  "idUsuario": "COLOQUE ID DO USUARIO CRIADO AQUI",
  "idCurso": "COLOQUE ID DO CURSO CRIADO AQUI"
}
```

### 🤝 Contribuidores
Este projeto foi desenvolvido com a colaboração de um time dedicado e comprometido. Agradecimentos especiais aos integrantes do squad:

* Eduardo Kendi De Sousa Miyasaki:
	- Microsserviço de usuario
	- Serviços de comentarios e avaliações
* João Lázaro Neto:
  	- Microsserviço de curso
	- Teste de carga
* Mônica Jiuliani Leamari:
  	- Microsserviço conteudo
  	- Servico de certificado
* Maikon Douglas Da Silva Gomes:
  	- Microsserviço de matricula
  	- Sistema de pagamento
 

### Realizado em conjunto :

- BackLog
- DER
- Arquitetura

Cada um contribuiu ativamente para o desenvolvimento, testes, arquitetura e melhorias dessa aplicação. O trabalho em equipe foi essencial para transformar a ideia em um projeto funcional e robusto. 💪🚀

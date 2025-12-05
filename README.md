# Prontuário Digital

## Descrição do projeto 📄  

O projeto foi desenvolvido para a matéria de Banco de Dados I e Engenharia de Software da Universidade Estadual da Bahia (UESB), no curso de Ciência da Computação. O objetivo era que ao escolher um tema relevante, fosse aplicado conceitos de Engenharia de Software para estruturar todo o processo de criação da aplicação, além disso também foi proposto a implementação de conceitos de banco de dados para persistir as informações do aplicativo. 

O sistema consiste em uma aplicação desktop, que permite um enfermeiro que atue em um hospital ou casa de acolhimento, gerenciar os prontuários dos pacientes de forma simples e prática. Foram utilizadas as tecnologias Maven, Postgresql, Java 8, Javafx e Figma.  


## Funcionalidades 🔍

- Cadastrar enfermeiros  
- Cadastrar, remover e modificar pacientes  
- Criar, editar e remover prontuários
- Gerar um arquivo .pdf com as informações do prontuário
- Buscar pacientes por nome ou CPF


## Capturas de tela 📸: 

- Tela de login:
  
  <img width="695" height="494" alt="Captura de tela 2025-12-03 203905" src="https://github.com/user-attachments/assets/d76ad9c0-6011-4a21-9956-e89a97ff0e16" />

- Tela para cadastrar ou acessar pacientes:

  <img width="692" height="489" alt="Captura de tela 2025-12-03 204931" src="https://github.com/user-attachments/assets/a8c13e64-2be8-4204-ad00-863734af0b98" />

- Tela para acessar os pacientes:

  <img width="695" height="493" alt="Captura de tela 2025-12-03 204956" src="https://github.com/user-attachments/assets/1736bd9a-3737-4cc9-a74f-26c084282d6f" />
  

 ## Tecnologias utilizadas 💻: 

![JAVA](https://img.shields.io/badge/Java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white&logoWidth=30)
![MAVEN](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![POSTGRESQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![FIGMA](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)

## Requisitos para executar o sistema 🔧: 

- Java 8: [Baixar java](https://www.oracle.com/br/java/technologies/javase/javase8-archive-downloads.html)
- JavaFx: [Baixar javafx](https://www.oracle.com/java/technologies/install-javafx-sdk.html)
- Maven: [Baixar Maven](https://maven.apache.org/download.cgi)
- Postgresql: [Baixar postgresql](https://www.pgadmin.org/download/)


## Executando o projeto 📲: 

Para executar a aplicação, você deve:

- Certificar-se de que o PostgreSQL / pgAdmin 4 esteja instalado no seu computador
- Criar o banco de dados prontuario no pgAdmin 4 (use o comando create database prontuario) e criar as tabelas a partir do script armazenado na pasta sql
- Acessar a classe ConnectionFactory na pasta db, e inserir o nome de usuário (por padrão, postgres) e a senha da sua conta no PostgreSQL
- Abra a pasta do projeto em alguma IDE (Eclipse, Netbeans, etc.) e a converta para um projeto Maven para conseguir executar todas as suas dependências
- Caso na primeira execução, ocorrer uma falha de conexão com o banco de dados, feche o programa e execute novamente na sua IDE, pois às vezes a conexão pode falhar momentaneamente

## Aprendizados obtidos 📝: 

- Persistência de dados utilizando Postgresql
- Conexão entre aplicação e SGBD utilizando JDBC
- Conceitos de engenharia de software, como Scrum para metodologia ágil
- Modelar um problema do mundo real para um software funcional
- Organização de tarefas utilizando Kanban   


## Colaboradores 🤝: 

- [Gabriel Alves](https://github.com/gabriellvz)
- [Fatima Leite Lobo](https://github.com/fatimaleitelobo)
- [Diogo Oliveira](https://github.com/Diogosousagamer)
- [Maria Paula Ferraz](https://github.com/Mapilumie)
- [Diego Armando](https://github.com/DiegoArmando11)
- [Clarice Sofia](https://github.com/clarisofia)
- [Emerson Jesus](https://github.com/Emerson484)


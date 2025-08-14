# Zyntra - WebApp – Versão Escolar

Este projeto foi desenvolvido por alunos do Instituto Germinare como atividade escolar de desenvolvimento web, inspirado no sistema real da Zyntra.

O objetivo era simular o funcionamento básico de um sistema com funcionalidades principais, interface simples e estrutura organizada para avaliação prática.

---

## ✨ Funcionalidades implementadas

- Landing Page apresentando o sistema
- Área restrita páginas com operações CRUD (Criar, Listar, Editar, Excluir)
- Interface desenvolvida com HTML e CSS
- Login e cadastro em área restrida por Servlets
- Comunicação com banco de dados do Aion-Mobile por RPA

---

## 🛠️ Tecnologias utilizadas

- HTML5
- CSS3
- JSP
- Git + GitHub
- API JDBC
- Java Servlet por Apache Tomcat
- SQL Database

---

## 🧑‍💻 Equipe do projeto

| Integrante              | Responsável por                |
|-------------------------|--------------------------------|
| Ana Clara Blefari       | Desing de páginas do site      |
| Beatriz Frisina Battista| Design de páginas do site      |
| Caio Gomide Amoroso     | Backend do site por Java       |
| Davi Luz Pereira        | Frontend do site por JSP e HTML| 
| Eduardo Farias Domingues| Backend do site por Java       |
| Erick Neves Barbosa     | Gestão do database SQL do site |
| Lucas Caramigo Pereira  | Frontend do site por JSP e HTML|
---

## 📁 Estrutura simulada do projeto
```bash
src/
├── main/                            # Arquivos do código principal do site
│   ├── java/                        # Código Java (back-end)
│   │   ├── org.example.controller/              # Camada de controle (Servlets e lógica de requisições)
│   │   ├── org.example.dao/                     # Camada de acesso a dados (Data Access Object)
│   │   ├── org.example.model/                   # Classes de modelo (entidades, JavaBeans)
│   │   ├── org.example/             # Pacote auxiliar ou exemplos
│   │   └── org.example.regex/                   # Lógica com expressões regulares
│   ├── resources/                   # Arquivos de recursos usados pelo Java (configurações, etc.)
│   └── webapp/                      # Parte visual do projeto (front-end JSP)
│       ├── assets/                  # Recursos estáticos
│       ├── css/                     # Estilos CSS
│       ├── js/                      # Scripts JavaScript
│       ├── pages/                   # Páginas JSP organizadas por módulo
│       └── WEB-INF/                 # Configurações internas protegidas (ex: web.xml)
├── test/                            # Diretório de testes do projeto
```


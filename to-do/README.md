<h1 align="center">
  TODO List
</h1>

API para gerenciar tarefas (CRUD)

## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [PostgreSQL](https://www.postgresql.org/download/)

## Práticas adotadas

- SOLID, DRY, YAGNI, KISS
- API REST
- Consultas com Spring Data JPA
- Injeção de Dependências
- Tratamento de respostas de erro
- Geração automática do Swagger com a OpenAPI 3

## Como Executar

- Clonar repositório git
- Construir o projeto:

```
$ ./mvnw clean package
```

- Executar a aplicação:

```
$ java -jar target/todolist-0.0.1-SNAPSHOT.jar
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [httpie](https://httpie.io):

- Criar Tarefa

```
$ http POST :8080/todos name="Todo 1" description="Desc Todo 1" accomplished="false" priority="LOW"

  {
    "id": 1,
    "name": "Todo 1",
    "description": "Desc Todo 1",
    "accomplished": false,
    "priority": "LOW",
    "createdAt": "2024-04-22T22:11:11.954+00:00",
	"updatedAt": "2024-04-22T22:11:11.954+00:00"
  }
```

- Listar Todas Tarefas

```
$ http GET :8080/todos

[
  {
    "id": 1,
    "name": "Todo 1",
    "description": "Desc Todo 1",
    "accomplished": false,
    "priority": "LOW",
    "createdAt": "2024-04-22T22:11:11.954+00:00",
	"updatedAt": "2024-04-22T22:11:11.954+00:00"
  }
]
```

- Listar Tarefa por Id

```
$ http GET :8080/todos/1

  {
    "id": 1,
    "name": "Todo 1",
    "description": "Desc Todo 1",
    "accomplished": false,
    "priority": "LOW",
    "createdAt": "2024-04-22T22:11:11.954+00:00",
	"updatedAt": "2024-04-22T22:11:11.954+00:00"
  }
```

- Atualizar Tarefa

```
$ http PUT :8080/todos/1 name="Todo 1 Up" description="Desc Todo 1 Up" accomplished=false priority="MEDIUM"

  {
    "id": 1,
    "name": "Todo 1 Up",
    "description": "Desc Todo 1" Up,
    "accomplished": false,
    "priority": "MEDIUM",
    "createdAt": "2024-04-22T22:11:11.954+00:00",
	"updatedAt": "2024-04-22T22:11:11.954+00:00"
  }
```

- Remover Tarefa

```
http DELETE :8080/todos/1

```

package com.todolist.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.todolist.todo.models.ToDoModel;
import com.todolist.todo.models.ToDoModel.Priority;

import static com.todolist.todo.TestConstants.TODOS;
import static com.todolist.todo.TestConstants.TODO;

@SpringBootTest
class ToDoApplicationTests {

	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	@Sql("/remove.sql")
	class TodolistApplicationTests {
		@Autowired
		private WebTestClient webTestClient;

		@Test
		void testCreateToDoSuccess() {
			var toDo = new ToDoModel(
					"todo 1", "desc todo 1", false, Priority.LOW);

			webTestClient
					.post()
					.uri("/todos")
					.bodyValue(toDo)
					.exchange() // inicia a execução da requisição
					.expectStatus().isCreated()
					.expectBody()
					.jsonPath("$.name").isEqualTo(toDo.getName())
					.jsonPath("$.description").isEqualTo(toDo.getDescription())
					.jsonPath("$.accomplished").isEqualTo(toDo.getAccomplished())
					.jsonPath("$.priority").isEqualTo(toDo.getPriority());
		}

		@Test
		public void testCreateToDoFailure() {
			webTestClient
					.post()
					.uri("/todos")
					.bodyValue(
							new ToDoModel(
									"", "", false, Priority.LOW))
					.exchange()
					.expectStatus().isBadRequest();
		}

		@Sql("/import.sql")
		@Test
		public void testListAllTodos() throws Exception {
			webTestClient
					.get()
					.uri("/todos")
					.exchange()
					.expectStatus().isOk()
					.expectBody()
					.jsonPath("$").isArray()
					.jsonPath("$.length()").isEqualTo(5)
					.jsonPath("$[0]").isEqualTo(TODOS.get(0))
					.jsonPath("$[1]").isEqualTo(TODOS.get(1))
					.jsonPath("$[2]").isEqualTo(TODOS.get(2))
					.jsonPath("$[3]").isEqualTo(TODOS.get(3))
					.jsonPath("$[4]").isEqualTo(TODOS.get(4));
		}

		@Sql("/import.sql")
		@Test
		public void testListTodo() throws Exception {
			webTestClient
					.get()
					.uri("/todos/" + TODO.getId())
					.exchange()
					.expectStatus().isOk()
					.expectBody()
					.jsonPath("$.name").isEqualTo(TODO.getName())
					.jsonPath("$.description").isEqualTo(TODO.getDescription())
					.jsonPath("$.accomplished").isEqualTo(TODO.getAccomplished())
					.jsonPath("$.priority").isEqualTo(TODO.getPriority());
		}

		@Test
		public void testListTodoFailure() {
			var unexinstingId = 1L;

			webTestClient
					.delete()
					.uri("/todos/" + unexinstingId)
					.exchange()
					.expectStatus().isBadRequest();
		}

		@Sql("/import.sql")
		@Test
		public void testUpdateToDoSuccess() {

			LocalDateTime now = LocalDateTime.now();
			Timestamp updatedAt = Timestamp.valueOf(now);

			var toDo = new ToDoModel(TODO.getId(), TODO.getName() + " Up", TODO.getName() + " Up", !TODO.getAccomplished(),
					Priority.MEDIUM, TODO.getCreatedAt(), updatedAt);

			webTestClient
					.put()
					.uri("/todos/" + TODO.getId())
					.bodyValue(toDo)
					.exchange()
					.expectStatus().isOk()
					.expectBody()
					.jsonPath("$.name").isEqualTo(toDo.getName())
					.jsonPath("$.description").isEqualTo(toDo.getDescription())
					.jsonPath("$.accomplished").isEqualTo(toDo.getAccomplished())
					.jsonPath("$.priority").isEqualTo(toDo.getPriority());
		}

		@Test
		public void testUpdateTodoFailure() {
			var unexinstingId = 1L;

			webTestClient
					.put()
					.uri("/todos/" + unexinstingId)
					.bodyValue(
							new ToDoModel(unexinstingId, "", "", false, Priority.LOW, null, null))
					.exchange()
					.expectStatus().isBadRequest();
		}

		@Sql("/import.sql")
		@Test
		public void testDeleteTodoSuccess() {
			webTestClient
					.delete()
					.uri("/todos/" + TODOS.get(0).getId())
					.exchange()
					.expectStatus().isOk();
		}

		@Test
		public void testDeleteTodoFailure() {
			var unexinstingId = 1L;

			webTestClient
					.delete()
					.uri("/todos/" + unexinstingId)
					.exchange()
					.expectStatus().isBadRequest();
		}
	}
}

package org.mangonotes.controller;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.mangonotes.handler.ErrorType;
import org.mangonotes.model.dto.req.TaskReqDTO;
import org.mangonotes.model.dto.req.TodoReqDTO;
import org.mangonotes.model.dto.res.TaskResDTO;
import org.mangonotes.model.dto.res.TodoResDTO;
import org.mangonotes.services.todo.ITodoCommandService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class TodoQueryControllerTest {

    @Inject
    ITodoCommandService queryService;
    private TodoReqDTO todoReqDTO;
    @Inject
    EntityManager entityManager;
    @BeforeEach
     void setup(){
        todoReqDTO = new TodoReqDTO();
        todoReqDTO.setName("test");
        TaskReqDTO taskReqDTO = new TaskReqDTO();
        taskReqDTO.setDescription("dummy");
        taskReqDTO.setName("test");
        List<TaskReqDTO> taskReqDTOList= List.of(taskReqDTO);
        todoReqDTO.setTasks(taskReqDTOList);
       // cleanup();


    }

    public void cleanup(){
        Query query= entityManager.createQuery("from TodoEntity");
        query.getResultList().forEach(entity-> entityManager.remove(entity));
    }
    @Test
    @Order(1)
    @Transactional
    void getAllWithoutData() {
        cleanup();
        TodoResDTO[] actual =  given().get("/todos")
                .then()
                .log().body()
                .statusCode(200)
                .extract().response().as(TodoResDTO[].class);
    }
    @Transactional
    @Test
    @Order(2)
    void findById_NotFind() {
        cleanup();
        String  type =  given().get("/todos/10")
                .then()
                .log().all()
                .statusCode(400)
                .extract().path("type");
        assertEquals(ErrorType.ITEM_NOT_FOUND.name(), type);
    }

    @Test
@Order(3)
    void findById() {
    TodoResDTO create =
            given()
                    .contentType(ContentType.JSON)
                    .body(todoReqDTO)
                    .when()
                    .post("/todos")
                    .then()
                    .log().body()
                    .statusCode(201)
                    .extract().response().as(TodoResDTO.class);

      System.out.println("find by id id " + create.getId());
        TodoResDTO  resDTO =  given().get("/todos/"+ create.getId())
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(TodoResDTO.class);;
        assertEquals(resDTO.getId(), create.getId());
    }

    @Order(4)
    @Test
    void getAllWithData() {
        queryService.create(todoReqDTO);
        TodoResDTO[] actual =  given().get("/todos")
                .then()
                .log().body()
                .statusCode(200)
                .extract().response().as(TodoResDTO[].class);
        assertTrue(actual.length >0);
    }





}
package org.mangonotes.controller;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.mangonotes.handler.ErrorType;
import org.mangonotes.model.dto.req.TaskReqDTO;
import org.mangonotes.model.dto.req.TodoReqDTO;
import org.mangonotes.model.dto.res.TodoResDTO;
import org.mangonotes.model.entity.TaskEntity;
import org.mangonotes.services.todo.ITodoCommandService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoCommandControllerTest {
    @Inject
    ITodoCommandService queryService;
    private TodoReqDTO todoReqDTO;
    @BeforeEach
    void setup(){
        todoReqDTO = new TodoReqDTO();
        todoReqDTO.setName("name");
        TaskReqDTO taskReqDTO = new TaskReqDTO();
        taskReqDTO.setDescription("dummy");
        taskReqDTO.setName("test");
        List<TaskReqDTO> taskReqDTOList= List.of(taskReqDTO);
        todoReqDTO.setTasks(taskReqDTOList);
    }
@DisplayName("Test for create TODO")
    @Test
    @Order(1)
   @TestTransaction
    void createTest() {
        TodoResDTO actual =
                given()
                .contentType(ContentType.JSON)
                .body(todoReqDTO)
                .when()
                .post("/todos")
                .then()
                .log().body()
               .statusCode(201)
                .extract().response().as(TodoResDTO.class);
        assertTrue(actual.getId()>0);
    }

    @DisplayName("Test for create TODO with validation error")
    @Test
    @Order(2)
    void createTest_validation_error() {
        todoReqDTO.setName(null);
        String  type =
                given()
                        .contentType(ContentType.JSON)
                        .body(todoReqDTO)
                        .when()
                        .post("/todos")
                        .then()
                        .log().body()
                        .statusCode(400)
                        .extract().path("type");
                      ;
       assertEquals(type, ErrorType.VALIDATION_ERROR.name());
    }
    @DisplayName("Test for create TODO with validation error for empty task name")
    @Test
    void createTest_validation_error_task() {
        todoReqDTO.getTasks().get(0).setName(null);
        String  type =
                given()
                        .contentType(ContentType.JSON)
                        .body(todoReqDTO)
                        .when()
                        .post("/todos")
                        .then()
                        .log().body()
                        .statusCode(400)
                        .extract().path("type");
        ;
        assertEquals(type, ErrorType.VALIDATION_ERROR.name());
    }
    @DisplayName("Test for delete TODO ")
    @Test
    void deleteTest(){
        TodoResDTO actual =
                given()
                        .contentType(ContentType.JSON)
                        .body(todoReqDTO)
                        .when()
                        .post("/todos")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().response().as(TodoResDTO.class);

        given()
                .when()
                .delete("/todos/" + actual.getId())
                .then()
                .log().body()
                .statusCode(204);


    }
    @DisplayName("Test for delete TODO with item not found")
    @Test
    void deleteTest_not_found() {
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

        given()
                .when()
                .delete("/todos/" + create.getId())
                .then()
                .log().body()
                .statusCode(204);
     String errorType=   given()
                .when()
                .delete("/todos/" + create.getId())
                .then()
                .log().body()
                .statusCode(400)
                .extract().path("type");
     assertEquals(errorType, ErrorType.ITEM_NOT_FOUND.name());
    }
    @DisplayName("Test for edit TODO ")
    @Test
    void editTest(){

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
        String editedName = "edited";
        todoReqDTO.setName(editedName);
        TodoResDTO edited =
                given()
                        .contentType(ContentType.JSON)
                        .body(todoReqDTO)
                        .when()
                        .put("/todos/"+ create.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response().as(TodoResDTO.class);
        assertEquals(edited.getName(),editedName);
    }
    @DisplayName("Test for edit TODO  add new rows")
    @Test
    void editTest_add_new_rows(){
         TodoReqDTO todoReqUpdDTO= new TodoReqDTO();
         todoReqUpdDTO.setName("newname");
        TaskReqDTO taskReqDTO = new TaskReqDTO();
        taskReqDTO.setDescription("dummy");
        taskReqDTO.setName("test");
        List<TaskReqDTO> taskEntityList= new ArrayList<>();
        taskEntityList.add(todoReqDTO.getTasks().get(0));
        taskEntityList.add(taskReqDTO);
        todoReqUpdDTO.setTasks(taskEntityList);

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
        String editedName = "edited";
        todoReqDTO.setName(editedName);



        TodoResDTO edited =
                given()
                        .contentType(ContentType.JSON)
                        .body(todoReqUpdDTO)
                        .when()
                        .put("/todos/"+ create.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response().as(TodoResDTO.class);
        assertEquals(edited.getTasks().size(),todoReqUpdDTO.getTasks().size());
    }
    @DisplayName("Test for edit TODO  remove exist rows")
    @Test
    void editTest_remove_exist_rows(){
        TodoReqDTO todoReqUpdDTO= new TodoReqDTO();
        todoReqUpdDTO.setName("newname");
        TaskReqDTO taskReqDTO = new TaskReqDTO();
        taskReqDTO.setDescription("dummy");
        taskReqDTO.setName("test");
        List<TaskReqDTO> taskEntityList= new ArrayList<>();
        taskEntityList.add(todoReqDTO.getTasks().get(0));
        taskEntityList.add(taskReqDTO);
        todoReqUpdDTO.setTasks(taskEntityList);

        TodoReqDTO todoReqRemDTO= new TodoReqDTO();
        todoReqRemDTO.setName("newname_remove");
        TaskReqDTO taskReqRemDTO = new TaskReqDTO();
        taskReqRemDTO.setDescription("dummy");
        taskReqRemDTO.setName("test");
        List<TaskReqDTO> taskEntityRemList= new ArrayList<>();
        taskEntityRemList.add(taskReqRemDTO);
        todoReqRemDTO.setTasks(taskEntityRemList);

        TodoResDTO create =
                given()
                        .contentType(ContentType.JSON)
                        .body(todoReqUpdDTO)
                        .when()
                        .post("/todos")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().response().as(TodoResDTO.class);
        String editedName = "edited";
        todoReqDTO.setName(editedName);



        TodoResDTO edited =
                given()
                        .contentType(ContentType.JSON)
                        .body(todoReqRemDTO)
                        .when()
                        .put("/todos/"+ create.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response().as(TodoResDTO.class);
        assertEquals(edited.getTasks().size(),todoReqRemDTO.getTasks().size());
    }
    @DisplayName("Test for edit TODO ")
    @Test
    void editTest_removed(){

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
        given()
                .when()
                .delete("/todos/" + create.getId())
                .then()
                .log().body()
                .statusCode(204);
        String editedName = "edited";
        todoReqDTO.setName(editedName);
        String  type =
                given()
                        .contentType(ContentType.JSON)
                        .body(todoReqDTO)
                        .when()
                        .put("/todos/"+ create.getId())
                        .then()
                        .log().body()
                        .statusCode(400)
                        .extract().path("type");
        assertEquals(type , ErrorType.ITEM_NOT_FOUND.name());
    }


}
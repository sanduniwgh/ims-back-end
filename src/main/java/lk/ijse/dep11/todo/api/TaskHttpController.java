//package lk.ijse.dep11.todo.api;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import lk.ijse.dep11.todo.to.TaskTO;
//import lombok.Builder;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import javax.validation.Valid;
//import javax.validation.groups.Default;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//
//@RestController
//@RequestMapping("/tasks")
//@CrossOrigin
//public class TaskHttpController {
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping(produces = "application/json", consumes = "application/json")
//    public TaskTO createTask(@RequestBody @Validated TaskTO task){
//
////        if(task.getId() != null){
////            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id should be empty");
////        } else if (task.getDescription()==null || task.getDescription().isBlank()) {
////            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description cant be empty");
////        } else if (task.getStatus() !=null) {
////            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status should be empty");
////        }
////
////        System.out.println(" creatTasks()");
////        return null;
//
//        try {
//            Connection connection = pool.getConnection();
//            PreparedStatement stm = connection.
//                    prepareStatement("INSERT INTO task (description, status) VALUES (?,  FALSE)",
//                            Statement.RETURN_GENERATED_KEYS);
//                            stm.setString(1,task.getDescription());
//                            stm.executeUpdate();
//                            ResultSet generatedKeys = stm.getGeneratedKeys();
//                            generatedKeys.next();
//                            int id = generatedKeys.getInt(1);
//                            task.setId(id);
//                            task.setStatus(false);
//                            return  task;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    private  final HikariDataSource pool;
//
//    public TaskHttpController() {
//        HikariConfig config = new HikariConfig();
//        config.setUsername("postgres");
//        config.setPassword("hold");
//        config.setJdbcUrl("jdbc:postgresql://localhost:15000/dep11_todo_app");
//        config.setDriverClassName("org.postgresql.Driver");
//        config.addDataSourceProperty("maximumPoolSize", 10);
//        pool = new HikariDataSource(config);
//    }
//
//    @PreDestroy //take from using dependency
//    public void destroy(){
////        System.out.println("I am being destroyed");
//        pool.close();
//    }
//
////    @PostConstruct //take from using dependency
////    public void created(){
////        System.out.println("I am being created");
////    }
//
//
//
//    @GetMapping(produces = "application/json")
//    public List<TaskTO> getAllTask(){
////        System.out.println("getAllTasks()");
//
//        try (Connection connection = pool.getConnection()) {
//            Statement stm = connection.createStatement();
//            ResultSet rst = stm.executeQuery("SELECT * FROM task ORDER BY id");
//            List<TaskTO> taskList = new LinkedList<>();
//            while(rst.next()){
//                int id = rst.getInt("id");
//                String description = rst.getString("description");
//                boolean status = rst.getBoolean("description");
//                taskList.add(new TaskTO(id, description,status));
//
//            }
//            return taskList;
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    //PATCH/tasks/{id}
//    @PatchMapping(value = "/{id}", consumes = "application/json")
//    public void updateTask(@PathVariable int id,
//                           @RequestBody @Validated({TaskTO.Update.class, Default.class}) TaskTO task){
////        System.out.println("UpdateTask()");
//
//        try (Connection connection = pool.getConnection()) {
//            PreparedStatement stmExist = connection.prepareStatement("SELECT * FROM task WHERE id = ?");
//            stmExist.setInt(1, id);
//            if(!stmExist.executeQuery().next()){
//                throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
//
//            }
//
//            PreparedStatement stm = connection.
//                    prepareStatement("UPDATE task SET description = ?, status=? WHERE id=?");
//            stm.setString(1, task.getDescription());
//            stm.setBoolean(2, task.getStatus());
//            stm.setInt(3, task.getId());
//            stm.executeUpdate();
//        }catch (SQLException e){
//            throw new RuntimeException();
//        }
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/{id}")
//    public  void deleteTask(@PathVariable int id){
////        System.out.println("deleteTasks()");3
//
//        try (Connection connection = pool.getConnection()) {
//            PreparedStatement stmExist = connection.
//                    prepareStatement("SELECT * FROM task WHERE id = ?");
//            stmExist.setInt(1, id);
//            if(!stmExist.executeQuery().next()){
//                throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
//
//            }
//            PreparedStatement stm = connection.
//                    prepareStatement("DELETE FROM task  WHERE id=?");
//
//            stm.setInt(1, id);
//            stm.executeUpdate();
//        }catch (SQLException e){
//            throw new RuntimeException();
//        }
//
//
//    }
//}


package lk.ijse.dep11.todo.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lk.ijse.dep11.todo.to.TaskTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import javax.validation.Valid;
import javax.validation.groups.Default;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
//@CrossOrigin(origins = {"http://localhost:5500"})
@CrossOrigin
public class TaskHttpController {

    /*
        The schema object defines the content of the request and response. In other words,
        it refers to the definition and set of rules (validation rules) for representing
        the structure of API data
    */

    private final HikariDataSource pool;

    public TaskHttpController() {
        HikariConfig config = new HikariConfig();
        config.setUsername("postgres");
        config.setPassword("hold");
        config.setJdbcUrl("jdbc:postgresql://localhost:15000/dep11_todo_app");
        config.setDriverClassName("org.postgresql.Driver");
        config.addDataSourceProperty("maximumPoolSize", 10);
        pool = new HikariDataSource(config);
    }

    @PreDestroy
    public void destroy(){
        pool.close();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public TaskTO createTask(@RequestBody @Validated TaskTO task) {
        try (Connection connection = pool.getConnection()){
            PreparedStatement stm = connection
                    .prepareStatement("INSERT INTO task (description, status) VALUES (?, FALSE)",
                            Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, task.getDescription());
            stm.executeUpdate();
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            task.setId(id);
            task.setStatus(false);
            return task;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{id}", consumes = "application/json")
    public void updateTask(@PathVariable int id,
                           @RequestBody @Validated(TaskTO.Update.class) TaskTO task) {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement stmExist = connection
                    .prepareStatement("SELECT * FROM task WHERE id = ?");
            stmExist.setInt(1, id);
            if (!stmExist.executeQuery().next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found");
            }

            PreparedStatement stm = connection
                    .prepareStatement("UPDATE task SET description = ?, status=? WHERE id=?");
            stm.setString(1, task.getDescription());
            stm.setBoolean(2, task.getStatus());
            stm.setInt(3, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") int taskId) {
        try(Connection connection = pool.getConnection()){
            PreparedStatement stmExist = connection
                    .prepareStatement("SELECT * FROM task WHERE id = ?");
            stmExist.setInt(1, taskId);
            if (!stmExist.executeQuery().next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found");
            }

            PreparedStatement stm = connection.prepareStatement("DELETE FROM task WHERE id=?");
            stm.setInt(1, taskId);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* @ResponseStatus(HttpStatus.OK) */
    @GetMapping(produces = "application/json")
    public List<TaskTO> getAllTasks() {
        try(Connection connection = pool.getConnection()){
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM task ORDER BY id");
            List<TaskTO> taskList = new LinkedList<>();
            while (rst.next()){
                int id = rst.getInt("id");
                String description = rst.getString("description");
                boolean status = rst.getBoolean("status");
                taskList.add(new TaskTO(id, description, status));
            }
            return taskList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
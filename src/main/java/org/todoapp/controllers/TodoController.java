package org.todoapp.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.todoapp.dto.TodoDto;
import org.todoapp.services.CategoryService;
import org.todoapp.services.TodoService;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@Slf4j(topic = "TODO-CONTROLLER")
@Tag(name = "TODO-CONTROLLER")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TodoController {

    private final TodoService todoService;

    private final CategoryService categoryService;

    @PostMapping("/create")
    public Map<String, Object> createTodo(@RequestBody TodoDto todoDto) {
        log.info("create todo {}",todoDto );


        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "create todo");
        result.put("data", todoService.save(todoDto));
        return result;


    }

    @PutMapping("/update")
    public Map<String, Object> updateTodo(@RequestBody TodoDto todoDto) {

        log.info("update todo {}",todoDto );


        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "update todo");
        result.put("data", todoService.save(todoDto));
        return result;
    }

    @GetMapping("/all")
    public Map<String, Object> getAllTodos() {

        log.info("Get all to do {}");


        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "get all todos");
        result.put("data", todoService.findAll());
        return result;


    }

    @GetMapping("/{id}")
    public Map<String, Object> getTodoDetail(@PathVariable Long id) {

        log.info("Get to do detail of id {}", id);


        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "get todo by id");
        result.put("data", todoService.findById(id));
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteTodo(@PathVariable Long id) {
        log.info("delete to do {}", id);
        todoService.delete(id);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "delete to do");
        result.put("data", id);
        return result;
    }


}

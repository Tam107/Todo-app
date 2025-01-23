package org.todoapp.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todoapp.dto.CategoryDto;
import org.todoapp.dto.UserDto;
import org.todoapp.services.CategoryService;
import org.todoapp.services.TodoService;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@Slf4j(topic = "CATEGORY-CONTROLLER")
@RequiredArgsConstructor
@Tag(name = "CATEGORY-CONTROLLER")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {

    private final TodoService todoService;

    private final CategoryService categoryService;

    // create category
    @PostMapping("/create")
    public Map<String, Object> createCategory(@RequestBody CategoryDto categoryDto){

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", categoryService.save(categoryDto));
        return result;
    }

    // update category
    @PutMapping("/update")
    public Map<String, Object> updateCategory(@RequestBody CategoryDto categoryDto){
        log.info("Update category");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "update category");
        result.put("data", categoryService.save(categoryDto));
        return result;
    }


    // get all category
    @GetMapping("/catgories")
    public Map<String, Object> getAllCategories(@RequestParam(required = false) String keyword
            , @RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "20") int size ){
        Pageable pageRequest = PageRequest.of(page -1, size);
        // Add filtering logic if needed
        Page<CategoryDto> categoryDtoPage = (keyword != null && !keyword.isEmpty())
                ? categoryService.findAll(keyword, pageRequest)
                : (Page<CategoryDto>) categoryService.findAll();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", categoryDtoPage.getContent());
        return result;

    }

    // get all todo by categories id
    @GetMapping("/categories/todos/{id}")
    public Map<String, Object> getAllTodoByCategoriesId(@PathVariable Long id){

        log.info("Get all todo by categories Id {}",id );

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "Get all todo by categories Id");
        result.put("data",todoService.findByCategory(id));

        return result;
    }

    // get all todo by categories for today
    @GetMapping("/categories/todos/today/{userId}")
    public Map<String, Object> getAllTodoByCategoriesForToday(@PathVariable Long userId){
        log.info("Get all todo by categories for today with userId {}",userId );

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "get all categories by user id");
        result.put("data",categoryService.getAllToDoByCategoriesForToday(userId));

        return null;
    }


    // get all categories by user id
    @GetMapping("/categories/users/{id}")
    @Operation(summary = "Get categories by user id")
    public Map<String, Object> getAllCategoriesByUserId(@PathVariable Long id){
        log.info("Get all categories by user id");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "get all categories by user id");
        result.put("data",categoryService.findAllByUserId(id) );

        return result;
    }



    @GetMapping("/categories/{id}")
    public Map<String, Object> getCategory(@PathVariable @Min(value = 1, message = "The id must be equal or greater than 1") Long id){

        log.info("Get category detail by id");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "get all categories by user id");
        result.put("data",categoryService.findById(id) );


        return result;
    }

    // delete categories by id
    @DeleteMapping("/categories/delete/{id}")
    @Operation(summary = "Delete category by ID", description = "Deletes a category by ID and returns a status message")
    public ResponseEntity<Map<String, Object>> deleteCategory(
            @PathVariable @Valid @Min(value = 1, message = "Category ID must be greater than 0") Long id) {

        log.info("Delete categories with id {}", id);
        Map<String, Object> response = new LinkedHashMap<>();

        try {
            // Check if the category exists before trying to delete it
            if (categoryService.findById(id) == null) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Category with ID " + id + " not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Proceed with the deletion
            categoryService.delete(id);

            response.put("status", HttpStatus.NO_CONTENT.value());
            response.put("message", "Category with ID " + id + " deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            // Log the error for debugging
            log.error("Error occurred while deleting category with ID {}", id, e);

            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "An error occurred while processing your request");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

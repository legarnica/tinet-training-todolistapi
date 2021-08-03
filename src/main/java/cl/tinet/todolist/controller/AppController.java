package cl.tinet.todolist.controller;

import cl.tinet.todolist.model.CustomResponse;
import cl.tinet.todolist.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppController {

    @GetMapping
    public ResponseEntity<CustomResponse<String>> isAlive(){
        CustomResponse<String> customResponse = new CustomResponse<>();
        customResponse.setMessage("it's alive");

        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }
}
package cl.tinet.todolist.controller;

import cl.tinet.todolist.model.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class AppController {

    /**
     * Useful to prevent error logs in aws.bs
     *
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity<CustomResponse<String>> isAlive(HttpServletRequest request){
        CustomResponse<String> customResponse = new CustomResponse<>();
        String host = request.getHeader("host");

        customResponse.setBody("see documentation in: " + host + "/swagger-ui.html");
        customResponse.setMessage("it's alive!");

        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }
}

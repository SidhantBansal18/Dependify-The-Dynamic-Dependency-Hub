package uwb.css533.repositoryManager.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

public class DependencyNotFoundException extends  RuntimeException{
    public DependencyNotFoundException(String message) {

        //Calls constructor of parent class RuntimeException
        super(message);

    }
}

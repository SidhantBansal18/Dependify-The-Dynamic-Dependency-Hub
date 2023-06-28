package uwb.css533.repositoryManager.uploadService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uwb.css533.repositoryManager.model.DependencyInfo;
import uwb.css533.repositoryManager.serverHandler.ServerManager;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/dependencies")
public class UploadServiceInterface {

    private final UploadServiceImplementation uploadServiceImplementation;


    public UploadServiceInterface(UploadServiceImplementation uploadServiceImplementation) {
        this.uploadServiceImplementation = uploadServiceImplementation;
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<DependencyInfo> addDependency(@Valid @RequestBody DependencyInfo dependency){
        DependencyInfo newDependency = uploadServiceImplementation.addDependency(dependency);
        return new ResponseEntity<>(newDependency, HttpStatus.CREATED);
    }

//    @CrossOrigin
//    @PutMapping("/update")
//    public ResponseEntity<DependencyInfo> updateDependency(@RequestBody DependencyInfo dependency){
//        DependencyInfo updateDependency = uploadServiceImplementation.updateDependency(dependency);
//        return new ResponseEntity<>(updateDependency, HttpStatus.OK);
//    }

    @CrossOrigin
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file){
        return uploadServiceImplementation.uploadFile(file);
    }

//    @CrossOrigin
//    @Transactional
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteDependency(@PathVariable("id") int id){
//        uploadServiceImplementation.deleteDependency(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler (MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

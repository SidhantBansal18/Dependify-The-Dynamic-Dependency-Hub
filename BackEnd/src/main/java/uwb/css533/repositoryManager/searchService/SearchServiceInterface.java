package uwb.css533.repositoryManager.searchService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uwb.css533.repositoryManager.model.DependencyInfo;

import java.util.List;

@RestController
@RequestMapping("/dependencies")
public class SearchServiceInterface {

    private final SearchServiceImplementation searchServiceImplementation;

    public SearchServiceInterface(SearchServiceImplementation searchServiceImplementation) {
        this.searchServiceImplementation = searchServiceImplementation;
    }

    @CrossOrigin
    @GetMapping("/all")
    //Will return HTTP response and in the body there is going to be a list of dependencies
    public ResponseEntity<List<DependencyInfo>> getAllDependencies(){
        List<DependencyInfo> deps = searchServiceImplementation.findAllDependencies();
        return new ResponseEntity<>(deps, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/find/{id}")
    public ResponseEntity<List<DependencyInfo>> getDependencyByKeyword(@PathVariable("id") String id){
        List<DependencyInfo> dep = searchServiceImplementation.findDependencyByKeyword(id);
        return new ResponseEntity<>(dep, HttpStatus.OK);
    }
}

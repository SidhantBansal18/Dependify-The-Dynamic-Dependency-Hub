package uwb.css533.repositoryManager.searchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uwb.css533.repositoryManager.model.DependencyInfo;
import uwb.css533.repositoryManager.repo.IDependenciesRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchServiceImplementation {
    private final IDependenciesRepo depRepo;

    @Autowired
    public SearchServiceImplementation(IDependenciesRepo depRepo) {
        this.depRepo = depRepo;
    }

    public List<DependencyInfo> findAllDependencies(){
        return depRepo.findAll();
    }

    public List<DependencyInfo> findDependencyByKeyword(String keywords){
        Set<DependencyInfo> uniqueSearches = new HashSet<>();
        uniqueSearches.addAll(depRepo.findBySearchContaining(keywords));
        uniqueSearches.addAll(depRepo.findByArtifactIdContaining(keywords));
        uniqueSearches.addAll(depRepo.findByGroupIdContaining(keywords));
        uniqueSearches.addAll(depRepo.findByVersionContaining(keywords));

        List<DependencyInfo> searchResults = new ArrayList<>(uniqueSearches);
        return searchResults;
    }

}

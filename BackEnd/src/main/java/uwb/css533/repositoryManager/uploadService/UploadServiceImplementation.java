package uwb.css533.repositoryManager.uploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uwb.css533.repositoryManager.model.DependencyInfo;
import uwb.css533.repositoryManager.repo.IDependenciesRepo;
import uwb.css533.repositoryManager.serverHandler.ServerManager;

//For CRUD operations or executing JDBC queries
@Service
public class UploadServiceImplementation {
    private final IDependenciesRepo depRepo;
    private ServerManager s3;

    @Autowired
    public UploadServiceImplementation(IDependenciesRepo depRepo,ServerManager s3) {
        this.depRepo = depRepo;
        this.s3 = s3;
    }

    public DependencyInfo addDependency(DependencyInfo dependency){
        if(depRepo.existsDependencyInfoByArtifactId(dependency.getArtifactId()) && depRepo.existsDependencyInfoByGroupId(dependency.getGroupId())){
            return this.updateDependency(dependency);
        }
        dependency.setSearch(dependency.getSearch());
        dependency.setId(dependency.getId());
        dependency.setLastModifiedDate(dependency.getLastModifiedDate());
        return depRepo.save(dependency);
    }

    public String uploadFile(MultipartFile file){
        return s3.uploadFile(file);
    }

    public DependencyInfo updateDependency(DependencyInfo dependency){
        dependency.setSearch(dependency.getSearch());
        dependency.setLastModifiedDate(dependency.getLastModifiedDate());
        depRepo.updateEntry(dependency.getGroupId(), dependency.getArtifactId(), dependency.getSearch(), dependency.getVersion(), dependency.getLastModifiedDate());
        return dependency;
    }

    public void deleteDependency(int id){
        depRepo.deleteDependencyById(id);
    }
}
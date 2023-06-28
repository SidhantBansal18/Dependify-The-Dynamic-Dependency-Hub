package uwb.css533.repositoryManager.downloadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uwb.css533.repositoryManager.repo.IDependenciesRepo;
import uwb.css533.repositoryManager.serverHandler.ServerManager;

import java.io.ByteArrayOutputStream;

@Service
public class DownloadServiceImplementation {
    private final IDependenciesRepo depRepo;
    private ServerManager s3;

    @Autowired
    public DownloadServiceImplementation(IDependenciesRepo depRepo, ServerManager s3) {
        this.depRepo = depRepo;
        this.s3 = s3;
    }

    public ByteArrayOutputStream downloadFile(String keyName){
        return s3.downloadFile(keyName);
    }

}

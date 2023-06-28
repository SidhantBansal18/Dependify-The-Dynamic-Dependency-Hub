package uwb.css533.repositoryManager.downloadService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/dependencies")
public class DownloadServiceInterface {

    private final DownloadServiceImplementation downloadServiceImplementation;

    public DownloadServiceInterface(DownloadServiceImplementation downloadServiceImplementation) {
        this.downloadServiceImplementation = downloadServiceImplementation;
    }

    @CrossOrigin
    @GetMapping("/downloadFile/{keyName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String keyName) {
        ByteArrayOutputStream downloadInputStream = downloadServiceImplementation.downloadFile(keyName);

        return ResponseEntity.ok()
                .contentType(contentType(keyName))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + keyName + "\"")
                .body(downloadInputStream.toByteArray());
    }

    private MediaType contentType(String keyName) {
        String[] arr = keyName.split("\\.");
        String type = arr[arr.length-1];
        switch(type) {
            case "txt": return MediaType.TEXT_PLAIN;
            case "png": return MediaType.IMAGE_PNG;
            case "jpg": return MediaType.IMAGE_JPEG;
            default: return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

}

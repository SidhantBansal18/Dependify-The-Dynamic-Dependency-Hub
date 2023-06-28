package uwb.css533.repositoryManager.serverHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.List;

//Managing the S3 Server where all the dependencies files would be stored.

@Service
public class ServerManager {

    private Logger logger = LoggerFactory.getLogger(ServerManager.class);

    AWSCredentials credentials;
    static AmazonS3 s3;
    static Bucket named_bucket = null;

    @Autowired
    public ServerManager() {
        credentials = new BasicAWSCredentials("accessKey", "secretKey");
        s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.DEFAULT_REGION).build();
        createBucket("css533-assignment2-dependency-manager");
    }

    public static Bucket getBucket(String bucket_name) {
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }

    public static Bucket createBucket(String bucket_name) {
        if (s3.doesBucketExistV2(bucket_name)) {
            named_bucket = getBucket(bucket_name);
        } else {
            try {
                named_bucket = s3.createBucket(bucket_name);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }
        return named_bucket;
    }

    public static void addObject(String key_name, String file_path) {
        System.out.println(key_name);
        try {
            s3.putObject(named_bucket.getName(), key_name, new File(file_path));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    public String uploadFile(MultipartFile multipartFile){
        String fileURL = "";
        try{
            File file = convertMultipartFileToFile(multipartFile);
            String fileName = multipartFile.getOriginalFilename();
            this.addObject(fileName, file.getAbsolutePath());
            file.delete(); //deleting the file from the local server
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return fileURL;
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    public ByteArrayOutputStream downloadFile(String keyName) {
        try {
            S3Object s3object = s3.getObject(new GetObjectRequest("css533-assignment2-dependency-manager", keyName));

            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }

            return baos;
        } catch (IOException ioe) {
            logger.error("IOException: " + ioe.getMessage());
        }
        catch (AmazonServiceException ase) {
            logger.info("sCaught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
            throw ase;
        }
        catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
            throw ace;
        }
        return null;
    }
}
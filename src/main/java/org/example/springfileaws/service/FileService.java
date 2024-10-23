package org.example.springfileaws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Autowired
    public FileService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    // Upload a file
    public String uploadFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            s3Client.putObject(bucketName, file.getOriginalFilename(), inputStream, metadata);
            return "File uploaded successfully: " + file.getOriginalFilename();
        } catch (IOException e) {
            // Handle the exception and log it
            throw new IOException("Failed to upload file: " + file.getOriginalFilename(), e);
        }
    }
    public List<String> listFiles() {
        return s3Client.listObjects(bucketName).getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    // Delete a file from the S3 bucket
    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return "File deleted successfully: " + fileName;
    }
    // Download a file from S3
 public Path downloadFile(String fileName) throws IOException {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        // Ensure the suffix is valid; using ".tmp" as a common temporary file extension
        String suffix = ".tmp";
        Path tempFile = Files.createTempFile("download_", suffix);
        try (InputStream inputStream = s3Object.getObjectContent()) {
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }
        return tempFile;
    }

}


package org.example.springfileaws.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.springfileaws.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
public class FileController {
    @Autowired
    private FileService fileService;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @GetMapping("/")
    public String welcomePage() {
        return "welcome"; // Return the welcome.jsp view
    }

    @GetMapping("/indexjsp")
    public String indexjsp(Model model) {
        populateFileList(model);
        return "index"; // Returns the index.jsp view
    }

    private void populateFileList(Model model) {
        List<String> files = fileService.listFiles();
        model.addAttribute("files", files);
    }

    // Handle file uploads (single or multiple)
    @PostMapping("/upload")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files, Model model) {
        StringBuilder messages = new StringBuilder();
        for (MultipartFile file : files) {
            try {
                String message = fileService.uploadFile(file);
                messages.append(message).append("<br>");
            } catch (IOException e) {
                messages.append("File upload failed: ").append(file.getOriginalFilename()).append(" - ").append(e.getMessage()).append("<br>");
            }
        }
        model.addAttribute("uploadMessage", messages.toString());
        populateFileList(model);
        return "index"; // Return to the index view
    }

    // Delete file
    @PostMapping("/delete")
    public String deleteFile(@RequestParam("fileName") String fileName, Model model) {
        String message = fileService.deleteFile(fileName);
        model.addAttribute("fileMessage", message);
        populateFileList(model);
        return indexjsp(model); // Return to the index view
    }

    // Download file
    @GetMapping("/download")
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        try {
            File tempFile = fileService.downloadFile(fileName).toFile();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + tempFile.getName() + "\"");
            try (FileInputStream in = new FileInputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    response.getOutputStream().write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("File download failed: " + e.getMessage());
        }
    }
}

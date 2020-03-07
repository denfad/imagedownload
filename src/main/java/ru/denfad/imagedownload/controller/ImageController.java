package ru.denfad.imagedownload.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {


    @PostMapping
    public String upload(@RequestParam MultipartFile attachment) throws IOException {
        String DIRECTORY_PATH = "C:\\Users\\Admin\\IdeaProjects\\imagedownload\\src\\main\\resources\\storage";
        Path path = Paths.get(DIRECTORY_PATH, attachment.getOriginalFilename());
        Path file = Files.createFile(path);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file.toString());
            stream.write(attachment.getBytes());
            return "good work";
        } finally {
            stream.close();

        }

    }

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(){
        try{
            String DIRECTORY_PATH = "C:\\Users\\Admin\\IdeaProjects\\imagedownload\\src\\main\\resources\\storage\\";
            Path path = Paths.get(DIRECTORY_PATH+"delta-v map.png");
            Resource resource = new UrlResource(path.toUri());
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename="+ resource.getFilename())
                    .body(resource);
        }catch(IOException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

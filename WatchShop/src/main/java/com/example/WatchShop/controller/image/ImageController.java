package com.example.WatchShop.controller.image;

import com.example.WatchShop.util.ImageFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("api/image")
@CrossOrigin(origins = "*")
public class ImageController {

  @GetMapping("/{imageName}")
  public ResponseEntity<?> getImage(@PathVariable("imageName") String imageName) {
    if (imageName != null) {
      Path fileName = Paths.get(ImageFile.PATH_IMAGE, imageName);
      try {
        byte[] buffer = Files.readAllBytes(fileName);
        ByteArrayResource bytes = new ByteArrayResource(buffer);
        return ResponseEntity
            .ok()
            .contentLength(buffer.length)
            .contentType(MediaType.IMAGE_PNG)
            .body(bytes);
      } catch (IOException e) {
        if (!"undefined".equals(imageName)) {
          return ResponseEntity
              .badRequest()
              .build();
        }
        return ResponseEntity
            .ok()
            .build();
      }
    }
    return ResponseEntity
        .badRequest()
        .build();
  }
}

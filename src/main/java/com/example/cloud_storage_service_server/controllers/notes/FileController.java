package com.example.cloud_storage_service_server.controllers.notes;

import com.example.cloud_storage_service_server.entities.CloudStorage;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URI;

@RestController
@RequestMapping(path="/file")
public class FileController {
    //download from server
    //upload to server
    private final CloudStorage cloudStorage = new CloudStorage();

    @PostMapping(path = "/{id}/{date}/{time}")
    Boolean uploadTOServer(
            //@RequestParam("name") String name,
                           @RequestBody MultipartFile file,
                           @PathVariable String id,
                           @PathVariable String date,
                           @PathVariable String time) {
        String path = CloudStorage.storagePath+"\\"+id+"\\"+date+"\\"+time;
        String name = "test.file";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(path+"\\"+name));
                stream.write(bytes);
                stream.close();
                return true;
            } catch (Exception e) {
                CloudStorage.LOGGER.error("Не удалось загрузить файл " + name + " => " + e.getMessage());
                return false;
            }
        } else {
            CloudStorage.LOGGER.error("Не удалось загрузить файл " + name + " => Файл пустой");
            return false;
        }
    }

    @GetMapping(path = "/{id}/{date}/{time}")
    ResponseEntity<Resource> downloadFromServer(@PathVariable String id,
                               @PathVariable String date,
                               @PathVariable String time){
        String path = CloudStorage.storagePath+"\\"+id+"\\"+date+"\\"+time;
        String name = "test.file";
        try {
            Resource resource = new UrlResource(URI.create(path+"\\"+name));
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
        } catch (MalformedURLException e){
            CloudStorage.LOGGER.error(String.valueOf(e));
            return null;
        }
    }
}

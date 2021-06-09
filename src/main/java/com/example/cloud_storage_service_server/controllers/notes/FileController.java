package com.example.cloud_storage_service_server.controllers.notes;

import com.example.cloud_storage_service_server.entities.CloudStorage;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

@RestController
@RequestMapping(path = "/file")
public class FileController {
    //download from server
    //upload to server
    private final CloudStorage cloudStorage = new CloudStorage();

    @PostMapping(path = "/{id}/{date}/{time}")
    Boolean uploadToServer(
            //@RequestParam("name") String name,
            @RequestBody MultipartFile file,
            @PathVariable String id,
            @PathVariable String date,
            @PathVariable String time,
            @RequestHeader HttpHeaders headers
    ) {
        String path = CloudStorage.storagePath + "\\" + id + "\\" + date + "\\" + time;
        String name = headers.getContentDisposition().getFilename();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(path + "\\" + name));
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
                                                @PathVariable String time) {
        String path = "\\" + id + "\\" + date + "\\" + time;
        String name = cloudStorage.getFileName(path);
        assert name != null;
        Resource file = cloudStorage.getResource(path, name);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping(path = "/{id}/{date}/{time}/name")
    public String fileExists(@PathVariable String id,
                              @PathVariable String date,
                              @PathVariable String time) {
        String path = "\\" + id + "\\" + date + "\\" + time;
        if (cloudStorage.fileCheck(id, date, time)){
            return cloudStorage.getFileName(path);
        }
        return null;
    }
}

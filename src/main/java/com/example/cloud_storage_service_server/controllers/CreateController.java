package com.example.cloud_storage_service_server.controllers;

import com.example.cloud_storage_service_server.Lib;
import com.example.cloud_storage_service_server.entities.CloudStorage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/create")
public class CreateController {
    private final CloudStorage cloudStorage = new CloudStorage();

    @PostMapping("/{id}/{date}/{time}")
    public void createText(@RequestBody String text,
                           @PathVariable String id,
                           @PathVariable String date,
                           @PathVariable String time) {
        if(Lib.isInt(id) && Lib.isInt(date) && Lib.isInt(time))
            cloudStorage.createFile(text,id,date,time);
    }
}

package com.example.cloud_storage_service_server.controllers;

import com.example.cloud_storage_service_server.Lib;
import com.example.cloud_storage_service_server.entities.CloudStorage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/delete")
public class DeleteController {
    private final CloudStorage cloudStorage = new CloudStorage();

    @DeleteMapping(path="/{id}/{date}/{time}")
    public void deleteTime(@PathVariable String id,
                           @PathVariable String date,
                           @PathVariable String time) {
        if(Lib.isInt(id) && Lib.isInt(date) && Lib.isInt(time))
            cloudStorage.deleteTime(id,date,time);
    }
}

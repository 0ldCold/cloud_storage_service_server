package com.example.cloud_storage_service_server.controllers;

import com.example.cloud_storage_service_server.Lib;
import com.example.cloud_storage_service_server.entities.CloudStorage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/view")
public class ViewController {
    private final CloudStorage cloudStorage = new CloudStorage();

    @GetMapping("/{id}")
    public List<String> viewStorageData(@PathVariable String id) {
        if(!Lib.isInt(id))
            return null;
        return cloudStorage.storageNavigation(id, null, null);
    }

    @GetMapping("/{id}/{date}")
    public List<String> viewStorageData(@PathVariable String id, @PathVariable String date) {
        if(!Lib.isInt(id) || !Lib.isInt(date))
            return null;
        return cloudStorage.storageNavigation(id, date, null);
    }

    @GetMapping("/{id}/{date}/{time}")
    public List<String> viewStorageData(@PathVariable String id, @PathVariable String date, @PathVariable String time) {
        if(!Lib.isInt(id) || !Lib.isInt(date) || !Lib.isInt(time))
            return null;
        return cloudStorage.storageNavigation(id, date, time);
    }
}

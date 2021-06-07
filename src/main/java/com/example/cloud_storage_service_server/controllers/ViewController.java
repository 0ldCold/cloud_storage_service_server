package com.example.cloud_storage_service_server.controllers;

import com.example.cloud_storage_service_server.Lib;
import com.example.cloud_storage_service_server.entities.CloudStorage;
import com.example.cloud_storage_service_server.entities.dto.MessageDTO;
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
        return cloudStorage.storageNavigation(id, null);
    }

    @GetMapping("/{id}/{date}")
    public List<String> viewStorageData(@PathVariable String id, @PathVariable String date) {
        if(!Lib.isInt(id) || !Lib.isInt(date))
            return null;
        return cloudStorage.storageNavigation(id, date);
    }

    @GetMapping("/{id}/{date}/{time}")
    public MessageDTO viewStorageData(@PathVariable String id, @PathVariable String date, @PathVariable String time) {
        if(!Lib.isInt(id) || !Lib.isInt(date) || !Lib.isInt(time))
            return null;
        String text = cloudStorage.getFileText(id,date,time);
        if (text==null)
            return null;
        return MessageDTO.create(text);
    }
}

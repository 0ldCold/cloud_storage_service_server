package com.example.cloud_storage_service_server.controllers;

import com.example.cloud_storage_service_server.Lib;
import com.example.cloud_storage_service_server.entities.CloudStorage;
import com.example.cloud_storage_service_server.entities.dto.MessageDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/view")
public class ViewController {
    private final CloudStorage cloudStorage = new CloudStorage();

    @GetMapping(path="/{id}")
    public List<String> viewStorageData(@PathVariable String id) {
        if(!Lib.isInt(id))
            return null;
        return cloudStorage.storageNavigation(id, null);
    }

    @GetMapping(path="/{id}/{date}")
    public List<String> viewStorageData(@PathVariable String id, @PathVariable String date) {
        if(!Lib.isInt(id) || !Lib.isInt(date))
            return null;
        return cloudStorage.storageNavigation(id, date);
    }

    @GetMapping(path="/{id}/{date}/{time}", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public MessageDTO viewStorageData(@PathVariable String id, @PathVariable String date, @PathVariable String time) {
        if(!Lib.isInt(id) || !Lib.isInt(date) || !Lib.isInt(time))
            return null;
        String text = cloudStorage.getFileText(id,date,time);
        if (text==null)
            return null;
        return MessageDTO.create(text);
    }
}

package com.example.cloud_storage_service_server.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CloudStorage {
    public static final String storagePath = "D:\\Ucheba\\summer_2020\\practice\\CloudService0720_storage";
    public static final Logger LOGGER = LoggerFactory.getLogger(CloudStorage.class);


    //список файлов в папке
    private File[] getFilesInFolder(String path){
        File dir = new File(storagePath+path);
        if(dir.exists()){
            return dir.listFiles();
        }
        LOGGER.debug("Файла не существует");
        return null;
    }
    //массив файлов в коллекцию имен этих файлов
    private List<String> listFileToString(File[] fileArr){
        if (fileArr == null){
            LOGGER.debug("Файлов не существует");
            return  null;
        }
        List<String> item = new ArrayList<String>();
        for (File file : fileArr)
        {
            item.add(file.getName());
        }
        return item;
    }
    //читает файл
    private String getTextFromFile(File file){
        String res;
        try(FileReader reader = new FileReader(file))
        {
            char[] buf = new char[256];
            int c;
            while((c = reader.read(buf))>0){

                if(c < 256){
                    buf = Arrays.copyOf(buf, c);
                }
            }
            res = new String(buf);
            return res.replaceAll("\\p{Cntrl}", "");
        }
        catch(IOException ex){
            LOGGER.error(ex.getMessage());
        }
        return null;
    }
    //ищет файл
    private String searchFile(File[] fileArr){
        if(fileArr==null)
            return null;
        for(File file : fileArr){
            if (file.getName().equals("text.txt")){
                return getTextFromFile(file);
            }
        }
        LOGGER.debug("Текстовый файл не найден");
        return null;
    }
    //навигация по папкам
    public List<String> storageNavigation(String id, String date){
        if (id!=null){
            if (date==null){
                return listFileToString(getFilesInFolder("\\" + id));
            }
            return listFileToString(getFilesInFolder("\\" + id + "\\" + date));
        }
        LOGGER.debug("id empty");
        return null;
    }
    public String getFileText(String id, String date, String time){
        if (id!=null && date!=null && time!=null)
            return searchFile(getFilesInFolder("\\" + id + "\\" + date + "\\" + time));
        return null;
    }


    private void writeFile(File file, String text){
        try(FileWriter writer = new FileWriter(file, false))
        {
            writer.write(text);
            writer.flush();
        }
        catch(IOException ex){
            LOGGER.error(ex.getMessage());
        }
    }
    public void createFile(String text, String id, String date, String time){
        File dir = new File(storagePath+"\\"+id+"\\"+date+"\\"+time);
        if (!dir.mkdirs())
            LOGGER.warn("Путь "+dir.getAbsolutePath()+" не создан");
        File outputFile = new File(dir,"text.txt");
        writeFile(outputFile, text);
    }

    public void deleteTime(String id, String date, String time){
        File file = new File(storagePath+"\\"+id+"\\"+date+"\\"+time);
        if (!file.exists()) {
            LOGGER.warn("Файла " + file.getAbsolutePath() + " не существует");
            return;
        }
        if(FileSystemUtils.deleteRecursively(file)){
            if(storageNavigation(id, date).isEmpty()){
                file = new File(storagePath+"\\"+id+"\\"+date);
                FileSystemUtils.deleteRecursively(file);
            }
        } else LOGGER.warn("Файл "+file.getAbsolutePath()+" не удален");
    }

    public boolean fileCheck(String id, String date, String time){
        File[] files = getFilesInFolder("\\" + id + "\\" + date + "\\" + time);
        return files != null && files.length > 1;
    }

    public Resource getResource(String path, String fileName){
        Path rootLocation = Paths.get(storagePath+path);
        try {
            Path file = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                LOGGER.error("Файл " + fileName + " не найден");
                return null;
            }
        }
        catch (MalformedURLException e) {
            LOGGER.error("Файл " + fileName + " не найден => " + e);
            return null;
        }
    }
    public String getFileName(String path){
        File[] files = getFilesInFolder(path);
        assert files != null;
        for (File file : files)
        {
            if (!file.getName().equals("text.txt"))
                return file.getName();
        }
        return null;
    }
}

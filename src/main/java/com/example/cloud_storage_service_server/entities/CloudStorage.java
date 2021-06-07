package com.example.cloud_storage_service_server.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CloudStorage {
    private final String storagePath = "D:\\Ucheba\\summer_2020\\practice\\CloudService0720_storage";
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudStorage.class);


    public CloudStorage(){}

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
    private List<String> searchFile(File[] fileArr){
        if(fileArr==null)
            return null;
        for(File file : fileArr){
            if (file.getName().equals("text.txt")){
                return Collections.singletonList(getTextFromFile(file));
            }
        }
        LOGGER.debug("Текстовый файл не найден");
        return null;
    }
    //навигация по папкам
    public List<String> storageNavigation(String id, String date, String time){
        if (id!=null){
            if (date==null && time==null){
                return listFileToString(getFilesInFolder("\\" + id));
            }
            if (time == null)
                return listFileToString(getFilesInFolder("\\" + id + "\\" + date));
            return searchFile(getFilesInFolder("\\" + id + "\\" + date + "\\" + time));
        }
        LOGGER.debug("id == null");
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
        if(!FileSystemUtils.deleteRecursively(file))
            LOGGER.warn("Файл "+file.getAbsolutePath()+" не создан");
    }


}

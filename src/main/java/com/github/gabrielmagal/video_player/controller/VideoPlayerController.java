package com.github.gabrielmagal.video_player.controller;

import com.github.gabrielmagal.video_player.controller.enums.MediaTypeEnum;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VideoPlayerController {
    public Response streamMedia(String path, String mediaName) {
        String videoPath = System.getProperty("user.home") + "/videos/" + path + "/" + mediaName;
        if (!new File(videoPath).exists()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try {
            return Response.ok(Files.readAllBytes(Paths.get(videoPath)))
                    .header("Content-Type", getMediaType(mediaName).getContentType())
                    .header("Content-Disposition", "inline; filename=" + mediaName)
                    .build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response uploadFile(FormData formData) {
        try {
            String path = System.getProperty("user.home") + "/videos/" + formData.fileName;
            int lastSeparatorIndex = path.lastIndexOf(File.separator);
            String directory = path.substring(0, lastSeparatorIndex);
            String fileName = path.substring(lastSeparatorIndex + 1);
            Files.createDirectories(Path.of(directory));
            Path destinationPath = Paths.get(directory, fileName);
            Files.copy(formData.file, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return Response.ok("File uploaded successfully to directory: " + directory + ", with file name: " + fileName).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error uploading file: " + e.getMessage()).build();
        }
    }

    public List<String> listFiles(String path) {
        List<String> stringList = new ArrayList<>();
        File folder = new File(System.getProperty("user.home") + "/videos/" + path);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        stringList.add(file.getName());
                    }
                }
            } else {
                throw new RuntimeException("A pasta está vazia.");
            }
        } else {
            throw new RuntimeException("A pasta não existe ou não é um diretório.");
        }
        return stringList;
    }

    private MediaTypeEnum getMediaType(String mediaName) {
        if(mediaName.contains(".png")) {
            return MediaTypeEnum.PHOTO;
        }
        if(mediaName.contains(".mp3")) {
            return MediaTypeEnum.MUSIC;
        }
        if(mediaName.contains(".mp4")) {
            return MediaTypeEnum.VIDEO;
        }
        return MediaTypeEnum.VIDEO;
    }
}

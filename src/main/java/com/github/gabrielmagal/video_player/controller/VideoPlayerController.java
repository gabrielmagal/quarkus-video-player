package com.github.gabrielmagal.video_player.controller;

import com.github.gabrielmagal.video_player.controller.enums.MediaTypeEnum;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@ApplicationScoped
public class VideoPlayerController {
    public Response streamMedia(String path, String mediaName) {
        String videoPath = path + mediaName;
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

    public Response uploadFile(String path, FormData formData) {
        try {
            Path filePath = Path.of(path + formData.fileName);
            Files.copy(formData.file, filePath, StandardCopyOption.REPLACE_EXISTING);
            return Response.ok("File uploaded successfully: " + filePath).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error uploading file: " + e.getMessage()).build();
        }
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

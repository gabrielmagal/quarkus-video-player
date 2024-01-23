package com.github.gabrielmagal.video_player.controller;

import com.github.gabrielmagal.video_player.controller.enums.MediaTypeEnum;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class VideoPlayerController {
    private final String path_videos = System.getProperty("user.home").replace("\\", File.separator) + File.separator + "videos_video_player" + File.separator;

    public Response streamMedia(String path, String mediaName) {
        if(Objects.isNull(path) || path.isEmpty() || path.isBlank()) {
            throw new RuntimeException("A pasta é obrigatória!");
        }

        if(Objects.isNull(mediaName) || mediaName.isEmpty() || mediaName.isBlank()) {
            throw new RuntimeException("O arquivo é obrigatório!");
        }

        String videoPath = path_videos + removeLeadingAndTrailingSlash(path) + "/" + removeLeadingAndTrailingSlash(mediaName);

        if (!new File(videoPath).exists()) {
            throw new RuntimeException("Arquivo não encontrado!");
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
            if (formData.fileName == null || formData.fileName.trim().isEmpty()) {
                throw new RuntimeException("O nome do arquivo é obrigatório!");
            }

            formData.fileName = removeLeadingAndTrailingSlash(formData.fileName);
            String path = path_videos + formData.fileName;

            int lastSeparatorIndex = path.lastIndexOf(File.separator);

            if (lastSeparatorIndex == -1) {
                throw new RuntimeException("Caminho do arquivo inválido!");
            }

            String directory = path.substring(0, lastSeparatorIndex);
            String fileName = path.substring(lastSeparatorIndex + 1);

            Files.createDirectories(Path.of(directory));
            Path destinationPath = Paths.get(directory, fileName);

            if (Files.exists(destinationPath)) {
                throw new FileAlreadyExistsException("Um arquivo com o mesmo nome já existe!");
            }

            Files.copy(formData.file, destinationPath);

            return Response.ok("Arquivo enviado com sucesso para o diretório: " + directory + ", com nome de arquivo: " + fileName).build();
        } catch (FileAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Erro: " + e.getMessage()).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao enviar arquivo: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro: " + e.getMessage()).build();
        }
    }

    public List<String> listFiles(String path) {
        List<String> stringList = new ArrayList<>();
        File folder = new File(path_videos + path);
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

    private String removeLeadingAndTrailingSlash(String str) {
        if (str.startsWith("/") || str.startsWith("\\"))
            str = str.substring(1);
        if (str.endsWith("/") || str.endsWith("\\"))
            str = str.substring(0, str.length() - 1);
        return str;
    }
}

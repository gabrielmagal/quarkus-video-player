package com.github.gabrielmagal.video_player.view;

import com.github.gabrielmagal.video_player.controller.FormData;
import com.github.gabrielmagal.video_player.controller.VideoPlayerController;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/videos")
public class VideoPlayerResource {
    private final String path = "C:/git/teste/";

    @Inject
    VideoPlayerController videoPlayerController;

    @GET
    @Path("/{mediaName}")
    public Response streamMedia(@PathParam("mediaName") String mediaName) {
        return videoPlayerController.streamMedia(path, mediaName);
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadFile(@MultipartForm FormData formData) {
        return videoPlayerController.uploadFile(path, formData);
    }
}

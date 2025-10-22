package com.suyash.Base64UseCases.controllers.usecase3;

import com.suyash.Base64UseCases.DTOs.usecase3.FileAck;
import com.suyash.Base64UseCases.DTOs.usecase3.FilePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
@Slf4j
public class FileWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    public FileWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    //client will send pdf to /app/uplaod
    @MessageMapping("/upload")
    @SendTo("/topic/files")
    public FileAck handleUpload(FilePayload payload) throws IOException {

        try{
            String filename = payload.getFilename();
            String base64 = payload.getBase64();

            if(filename==null || filename.isBlank()){
                filename="upload_"+System.currentTimeMillis()+".pdf";
            }

            if(base64==null || base64.isBlank()){
                log.warn("upload failed: base64 payload is missing");
                return new FileAck(filename, "empty-payload");
            }

            log.info("Recieved uploaded filename={}, base64lenght={}", filename, base64.length());

            //decode pdf and save to disk
            byte[] data = Base64.getDecoder().decode(base64);
            Path outputPath = Paths.get("tmp", filename); //we need to make sure that folde tmp exists
            Files.createDirectories(outputPath.getParent());

            return new FileAck(filename, "recieved");
        } catch(Exception e){
            log.error("Error processing upload", e);
            return new FileAck("unknown", "error");
        }

    }
}

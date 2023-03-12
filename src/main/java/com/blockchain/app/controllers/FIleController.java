package com.blockchain.app.controllers;

import com.blockchain.app.models.dtos.TransferRequest;
import com.blockchain.app.services.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/file"})
public class FIleController {

    private final FileService fileService;


    @GetMapping("/template/download")
    public ResponseEntity<Resource> downloadOAMExport(
            @RequestParam(required = false) Boolean sampleData
    ) throws IOException {

        log.info("Requested Download CSV file template");

        byte[] bytes = fileService.downloadTemplate(sampleData != null ? sampleData : false);
        ByteArrayResource resource = new ByteArrayResource(bytes);

        return ResponseEntity.ok()
                .header("Content-disposition", "attachment; filename=template.csv")
                .contentLength(bytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping(value = "/elaborate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<TransferRequest>> elaborateFile(
            @RequestParam MultipartFile file
    ) {

        log.info("Requested elaborate CSV file");

        return fileService.elaborateFile(file);
    }
}

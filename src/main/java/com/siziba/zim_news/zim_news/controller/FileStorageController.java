package com.siziba.zim_news.zim_news.controller;

import com.siziba.zim_news.zim_news.entity.FileStorage;
import com.siziba.zim_news.zim_news.exception.CustomServiceException;
import com.siziba.zim_news.zim_news.service.news.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files/")
public class FileStorageController {
    private final FileStorageService fileStorageService;

    @GetMapping("/download/{fileId}")
    public void downloadFile(@PathVariable UUID fileId, HttpServletResponse response) {
        log.info("FileStorageController/downloadFile");
        FileStorage file = fileStorageService.getFile(fileId);
        if (file == null) throw new CustomServiceException(HttpStatus.NOT_FOUND, "File not found");

        try {
            response.setContentType(file.getFileType());
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName());
            response.getOutputStream().write(file.getData());
            response.getOutputStream().flush();
        } catch (Exception e) {
            throw new CustomServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while downloading file");
        }
    }
}
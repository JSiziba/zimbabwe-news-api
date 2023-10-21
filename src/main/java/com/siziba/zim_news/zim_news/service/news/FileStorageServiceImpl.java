package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.dto.FileRequest;
import com.siziba.zim_news.zim_news.entity.FileStorage;
import com.siziba.zim_news.zim_news.exception.CustomServiceException;
import com.siziba.zim_news.zim_news.repository.FileStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final FileStorageRepository fileStorageRepository;
    @Override
    public FileStorage saveFile(FileRequest fileRequest) {
        try {
            FileStorage fileStorage = FileStorage.builder()
                    .fileName(fileRequest.getFile().getOriginalFilename())
                    .fileType(fileRequest.getFile().getContentType())
                    .data(fileRequest.getFile().getBytes())
                    .build();
            fileStorageRepository.save(fileStorage);
            return fileStorage;
        } catch (IOException e) {
            throw new CustomServiceException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public FileStorage getFile(UUID fileId) {
        return fileStorageRepository.findById(fileId)
                .orElseThrow(() -> new CustomServiceException(HttpStatus.NOT_FOUND, "File not found"));
    }

    @Override
    public void deleteFile(UUID id) {
        FileStorage fileToDelete = fileStorageRepository.findById(id)
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("File not found")
                        .build());
        fileStorageRepository.delete(fileToDelete);
    }
}
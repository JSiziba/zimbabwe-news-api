package com.siziba.zim_news.zim_news.dto;

import com.siziba.zim_news.zim_news.entity.FileStorage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private UUID id;
    private String fileName;
    private String fileType;
    private byte[] data;
    private String fileDownloadUri;

    public static FileResponse from(FileStorage fileStorage) {
        return FileResponse.builder()
                .id(fileStorage.getId())
                .fileName(fileStorage.getFileName())
                .fileType(fileStorage.getFileType())
                .fileDownloadUri("/api/v1/files/download/" + fileStorage.getId())
                .build();
    }
}
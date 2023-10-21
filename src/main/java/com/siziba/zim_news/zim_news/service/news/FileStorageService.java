package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.dto.FileRequest;
import com.siziba.zim_news.zim_news.entity.FileStorage;

import java.util.UUID;

public interface FileStorageService {
    FileStorage saveFile(FileRequest fileRequest);
    FileStorage getFile(UUID fileId);

    void deleteFile(UUID id);
}

package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.dto.FileRequest;
import com.siziba.zim_news.zim_news.entity.FileStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    @Override
    public FileStorage saveFile(FileRequest fileRequest) {
        return null;
    }

    @Override
    public FileStorage getFile(UUID fileId) {
        return null;
    }
}
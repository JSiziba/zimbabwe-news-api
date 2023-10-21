package com.siziba.zim_news.zim_news.dto.publication;


import com.siziba.zim_news.zim_news.dto.FileResponse;
import com.siziba.zim_news.zim_news.entity.Publication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicationResponse {
    public UUID id;
    public String name;
    public String description;
    public String url;
    public String country;
    public FileResponse logo;
    public java.sql.Timestamp createdAt;
    public java.sql.Timestamp updatedAt;

    public static PublicationResponse from(Publication publication) {
        return PublicationResponse.builder()
                .id(publication.getId())
                .name(publication.getName())
                .description(publication.getDescription())
                .url(publication.getUrl())
                .country(publication.getLocation())
                .logo(FileResponse.builder()
                        .id(publication.getLogo().getId())
                        .fileName(publication.getLogo().getFileName())
                        .fileType(publication.getLogo().getFileType())
                        .build())
                .createdAt(publication.getCreatedAt())
                .updatedAt(publication.getUpdatedAt())
                .build();
    }

    public static List<PublicationResponse> fromList(List<Publication> publications) {
        return publications.stream()
                .map(PublicationResponse::from)
                .toList();
    }
}

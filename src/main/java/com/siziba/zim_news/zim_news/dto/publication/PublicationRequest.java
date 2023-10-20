package com.siziba.zim_news.zim_news.dto.publication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicationRequest {
    public String name;
    public String description;
    public String url;
    public String country;
}

package com.siziba.zim_news.zim_news.repository;

import com.siziba.zim_news.zim_news.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    List<Publication> findPublicationsByIdNotIn(List<UUID> excludedPublications);

    Publication findByUrl(String url);
}

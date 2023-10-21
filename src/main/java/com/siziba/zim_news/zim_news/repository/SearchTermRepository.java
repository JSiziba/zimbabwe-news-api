package com.siziba.zim_news.zim_news.repository;

import com.siziba.zim_news.zim_news.entity.SearchTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SearchTermRepository extends JpaRepository<SearchTerm, UUID> {
    List<SearchTerm> findTop5OrderByCreatedAtDesc();

    SearchTerm findSearchTermByTermLike(String searchTerm);
}

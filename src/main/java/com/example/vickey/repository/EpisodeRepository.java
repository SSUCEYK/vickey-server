package com.example.vickey.repository;

import com.example.vickey.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//DB 상호작용 관련 부분
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findAll();

//    @Query("SELECT new com.example.vickey.EpisodeTitleCountDto(e.title, e.episodeCount) FROM Episode e")
//    List<EpisodeTitleCountDto> findTitleAndEpisodeCount();

    @Query("SELECT e.thumbnailUrl FROM Episode e")
    List<String> findAllThumbnailUrls();

    @Query("SELECT COALESCE(MAX(e.episodeId), 0) FROM Episode e")
    int findMaxEpisodeId();

    @Query(value = "SELECT * FROM Episode ORDER BY RANDOM() LIMIT :n", nativeQuery = true)
    List<Episode> findRandomEpisodes(@Param("n") int n);

    // 추가적인 쿼리 메서드
}

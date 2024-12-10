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

    @Query("SELECT e FROM Episode e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR LOWER(e.castList) LIKE LOWER(CONCAT('%', :searchQuery, '%'))")
    List<Episode> searchEpisodes(@Param("searchQuery") String var1);

    @Query("SELECT e FROM Episode e WHERE e.episodeId = :contentInfoQuery")
    Episode contentInfoEpisodes(@Param("contentInfoQuery") Long var1);

    @Query("SELECT e FROM Episode e JOIN FETCH e.videos WHERE e.episodeId = :contentInfoQuery")
    Episode findEpisodeWithVideos(@Param("contentInfoQuery")Long contentInfoQuery);

    // 추가적인 쿼리 메서드
}

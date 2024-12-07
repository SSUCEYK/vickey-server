package com.example.vickey.repository;

import com.example.vickey.entity.Episode;
import com.example.vickey.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findAll();

    List<Video> findByEpisode(Episode episode);

    @Query("SELECT v FROM Video v WHERE v.episode.episodeId = :episodeId")
    List<Video> findByEpisodeId(@Param("episodeId") Long episodeId);

    // 현재 에피소드의 최대 videoNum을 가져오는 쿼리
    @Query("SELECT MAX(v.videoNum) FROM Video v WHERE v.episode.episodeId = :episodeId")
    Integer findMaxVideoNumByEpisodeId(@Param("episodeId") Long episodeId);

    // 추가적인 쿼리 메서드 정의
}

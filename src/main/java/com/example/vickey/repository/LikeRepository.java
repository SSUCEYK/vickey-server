package com.example.vickey.repository;

import com.example.vickey.LikeKey;
import com.example.vickey.dto.LikedVideosResponse;
import com.example.vickey.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeKey> {

    // 사용자 ID로 좋아요를 찾음
    @Query("SELECT l FROM Like l WHERE l.user.userId = :userId")
    List<Like> findAllByUserId(@Param("userId") String userId);

    // 특정 사용자 ID와 에피소드 ID로 좋아요된 비디오 찾음
    @Query("SELECT l FROM Like l " +
            "WHERE l.user.userId = :userId " +
            "AND l.video.episode.episodeId = :episodeId")
    List<Like> findAllByUserIdAndVideo_Episode_EpisodeId(String userId, Long episodeId);

    @Query("SELECT new com.example.vickey.dto.LikedVideosResponse(v.videoId, v.thumbnailUrl, v.videoNum) " +
            "FROM Like l " +
            "JOIN l.video v " +
            "JOIN v.episode e " +
            "WHERE l.user.userId = :userId AND e.episodeId = :episodeId")
    List<LikedVideosResponse> findLikedVideoInfoByUserIdAndEpisodeId(@Param("userId") String userId, @Param("episodeId") Long episodeId);

    @Query("SELECT l FROM Like l WHERE l.user.userId = :userId AND l.video.videoId = :videoId")
    Optional<Like> findByUserIdAndVideoId(@Param("userId") String userId, @Param("videoId") Long videoId);

}

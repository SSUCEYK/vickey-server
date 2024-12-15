package com.example.vickey.repository;

import com.example.vickey.dto.CheckWatchedResponse;
import com.example.vickey.entity.CheckWatched;
import com.example.vickey.CheckWatchedKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckWatchedRepository extends JpaRepository<CheckWatched, CheckWatchedKey> {

    @Query("SELECT cw FROM CheckWatched cw WHERE cw.user.userId = :userId ORDER BY cw.watchedDate DESC")
    List<CheckWatched> findAllByUserId(@Param("userId") String userId);

    @Query("""
       SELECT new com.example.vickey.dto.CheckWatchedResponse(
       v.id, e.id, v.thumbnailUrl, v.videoNum, cw.progress, e.title
       )
       FROM CheckWatched cw
       JOIN cw.video v
       LEFT JOIN v.episode e
       WHERE cw.user.userId = :userId
       ORDER BY cw.watchedDate DESC
       """)
    List<CheckWatchedResponse> findTopByUserId(@Param("userId") String userId, Pageable pageable);


    @Modifying
    @Query(value = "INSERT INTO check_watched (user_id, video_id, progress, watched_date) " +
            "VALUES (:userId, :videoId, :progress, CURRENT_TIMESTAMP) " +
            "ON DUPLICATE KEY UPDATE progress = :progress, watched_date = CURRENT_TIMESTAMP",
            nativeQuery = true)
    void upsertWatched(@Param("userId") String userId, @Param("videoId") Long videoId, @Param("progress") int progress);

}

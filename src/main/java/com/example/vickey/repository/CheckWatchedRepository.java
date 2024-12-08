package com.example.vickey.repository;

import com.example.vickey.entity.CheckWatched;
import com.example.vickey.CheckWatchedKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckWatchedRepository extends JpaRepository<CheckWatched, CheckWatchedKey> {

    @Query("SELECT cw FROM CheckWatched cw WHERE cw.user.userId = :userId")
    List<CheckWatched> findAllByUserId(@Param("userId") String userId);
}

package com.example.vickey.api;


import com.example.vickey.dto.CheckWatchedResponse;
import com.example.vickey.service.CheckWatchedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class CheckWatchedController {

    private final CheckWatchedService checkWatchedService;

    @Autowired
    public CheckWatchedController(CheckWatchedService checkWatchedService) {
        this.checkWatchedService = checkWatchedService;
    }

    @GetMapping("/user/{userId}")
    public List<CheckWatchedResponse> getUserHistory(@PathVariable String userId) {
        return checkWatchedService.getUserHistory(userId);
    }

    @PostMapping("/user/{userId}/videos/{videoId}")
    public ResponseEntity<Void> markVideoAsWatched(@PathVariable String userId, @PathVariable Long videoId) {
        checkWatchedService.markAsWatched(userId, videoId);
        return ResponseEntity.ok().build();
    }

}


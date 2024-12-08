package com.example.vickey.service;

import com.example.vickey.dto.CheckWatchedResponse;
import com.example.vickey.entity.CheckWatched;
import com.example.vickey.repository.CheckWatchedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckWatchedService {

    private CheckWatchedRepository checkWatchedRepository;
    private final EpisodeService episodeService; // EpisodeService 주입

    @Autowired
    public CheckWatchedService(CheckWatchedRepository checkWatchedRepository, EpisodeService episodeService) {
        this.checkWatchedRepository = checkWatchedRepository;
        this.episodeService = episodeService;
    }

    public List<CheckWatchedResponse> getUserHistory(String userId) {
        List<CheckWatched> histories = checkWatchedRepository.findAllByUserId(userId);

        return histories.stream().map(history -> {

            CheckWatchedResponse response = new CheckWatchedResponse(history);

            Long episodeId = history.getVideo().getEpisodeId();
            response.setEpisode(episodeService.getEpisodeById(episodeId)); // VideoId로 Episode 조회 후 Set

            return response;

        }).collect(Collectors.toList());
    }

}

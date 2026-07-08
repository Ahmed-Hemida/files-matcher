package com.evision.matcher.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.evision.matcher.model.MatchResult;
import com.evision.matcher.service.MatchService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class MatchRunner implements CommandLineRunner  {

    @Qualifier("matchServiceImpl")
    private final MatchService matchService;

    @Override
    public void run(String... args) throws Exception {
        List<MatchResult> results = matchService.match();
        for (MatchResult result : results) {
            log.info("File: {}, Score: {}%", result.file(), result.score());
        }
    }

 

}

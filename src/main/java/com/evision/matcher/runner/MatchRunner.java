package com.evision.matcher.runner;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.evision.matcher.service.MatchService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component

public class MatchRunner implements CommandLineRunner  {

    @Qualifier("matchServiceImpl")
    private final MatchService matchService;

    @Override
    public void run(String... args) throws Exception {
        matchService.match();
    }

 

}

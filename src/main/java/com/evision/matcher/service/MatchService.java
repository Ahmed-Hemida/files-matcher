package com.evision.matcher.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.evision.matcher.model.MatchResult;

@Service
public interface MatchService {

     List<MatchResult> match() throws IOException;

}

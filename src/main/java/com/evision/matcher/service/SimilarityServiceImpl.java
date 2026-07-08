package com.evision.matcher.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.evision.matcher.parser.TextParser;
import com.evision.matcher.scanner.FileScanner;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SimilarityServiceImpl implements SimilarityService {


    @Override
    public double calculate(Set<String> referenceWords,
                            Set<String> candidateWords) {

        if (referenceWords.isEmpty() && candidateWords.isEmpty()) {
            return 100.0;
        }

        Set<String> intersection = new HashSet<>(referenceWords);
        intersection.retainAll(candidateWords);

        // Set<String> union = new HashSet<>(referenceWords);
        // union.addAll(candidateWords);

        return Math.round(((((double) intersection.size() / referenceWords.size()) * 10000)/100.0) * 100.0) / 100.0;
    }
    

}

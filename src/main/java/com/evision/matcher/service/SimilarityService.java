package com.evision.matcher.service;

import java.util.Set;

public interface SimilarityService {
    
     double calculate(final Set<String> referenceWords,
                     final Set<String> candidateWords);
                    

}

package com.evision.matcher.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.evision.matcher.model.MatchResult;
import com.evision.matcher.parser.TextParser;
import com.evision.matcher.scanner.FileScanner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {
    @Qualifier("fileScannerImpl")
    private final FileScanner fileScanner;

    @Qualifier("parserImpl")
    private final TextParser textParser;

    @Qualifier("similarityServiceImpl")
    private final SimilarityService similarityService;



    @Override
    public List<MatchResult> match() throws IOException {
          Path referenceFile = fileScanner.getReferenceFile();
        List<Path> poolFiles = fileScanner.getPoolFiles();

        // Parse reference once
        final Set<String> referenceWords = textParser.parse(referenceFile);

        List<MatchResult> results = new ArrayList<>();

        int threadCount = Math.min(poolFiles.size(), Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(Math.max(threadCount, 1));


        for (Path poolFile : poolFiles) {

            MatchResult matchResult = compareFile(poolFile, referenceWords);
            log.info("\n File: {}, Score: %{}", matchResult.file(), matchResult.score());
            // results.add(matchResult);
        }

        // results.sort(
        //         Comparator.comparingDouble(MatchResult::score)
        //                   .reversed());
        return null;
        // return results;
    }

      private MatchResult compareFile(Path poolFile,
                                    final Set<String> referenceWords) throws IOException {

        Set<String> candidateWords = textParser.parse(poolFile);

        double score =
                similarityService.calculate(referenceWords, candidateWords);

        return new MatchResult(poolFile, score);
    }


}

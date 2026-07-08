package com.evision.matcher.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        List<Future<MatchResult>> futures = new ArrayList<>();

        int threadCount = Math.min(poolFiles.size(), Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(Math.max(threadCount, 1));


        for (Path poolFile : poolFiles) {
            futures.add(executor.submit(() -> compareFile(poolFile, referenceWords)));
        }
        List<MatchResult> results = new ArrayList<>();
        for (Future<MatchResult> future : futures) {
            try {
                results.add(future.get());
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof IOException ioException) {
                    throw ioException;
                }
                throw new RuntimeException("Error while matching file", cause);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Matching interrupted", e);
            }
        }

        results.sort(
                Comparator.comparingDouble(MatchResult::score)
                          .reversed()
                        );
        return results;
    }

      private MatchResult compareFile(Path poolFile,
                                    final Set<String> referenceWords) throws IOException {

        Set<String> candidateWords = textParser.parse(poolFile);

        double score =
                similarityService.calculate(referenceWords, candidateWords);

        return new MatchResult(poolFile, score);
    }


}

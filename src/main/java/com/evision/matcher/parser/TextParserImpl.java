package com.evision.matcher.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class TextParserImpl implements TextParser {

    
    private static final Pattern WORD_PATTERN = Pattern.compile("[A-Za-z]+");

    @Override
    public Set<String> parse(Path path) throws IOException {

        Set<String> words = new HashSet<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {

            String line;

            while ((line = reader.readLine()) != null) {

                Matcher matcher = WORD_PATTERN.matcher(line);

                while (matcher.find()) {
                    words.add(matcher.group().toLowerCase());
                }
            }
        }

        return words;
    }

    @Override
    public List<Set<String>>  parse(List<Path> files) throws IOException {
        List<Set<String>> wordsList = new ArrayList<Set<String>>();
       for (Path file : files) {
            wordsList.add(parse(file));
        }
        return wordsList;
    }

}

package com.evision.matcher.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public interface  TextParser {

    Set<String> parse(Path file) throws IOException;
     List<Set<String>>  parse(List<Path> files) throws IOException;
    
}

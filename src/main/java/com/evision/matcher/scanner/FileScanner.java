package com.evision.matcher.scanner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileScanner {
    Path getReferenceFile() throws IOException;

    List<Path> getPoolFiles() throws IOException;

}

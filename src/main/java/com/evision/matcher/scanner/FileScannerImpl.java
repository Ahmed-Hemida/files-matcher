package com.evision.matcher.scanner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component

public class FileScannerImpl implements  FileScanner {

    @Value("${reference.file.path}")
    private String referenceFile;
    @Value("${pool.files.dir}")
    private String poolFilesDir;

    // private List<String> poolFiles;


    @Override
    public Path getReferenceFile() throws IOException {
        Path reference = Paths.get(referenceFile);

        validateReferenceFile(reference);

        return reference;
    }

    @Override
    public List<Path> getPoolFiles() throws IOException {

        Path directory = Paths.get(poolFilesDir);

        validateDirectory(directory);

        try (Stream<Path> stream = Files.list(directory)) {

            Path reference = getReferenceFile().toAbsolutePath().normalize();

            return stream
                    .filter(Files::isRegularFile)
                    .map(Path::toAbsolutePath)
                    .map(Path::normalize)
                    .filter(path -> !path.equals(reference)) //for not loud the reference file in the pool files    
                    .toList();

        } catch (IOException ex) {
            throw new FileSystemNotFoundException(
                    "Failed to scan directory " + directory);
        }
    }

    private void validateReferenceFile(Path file) throws IOException {
    boolean isTxt = file.getFileName().toString().toLowerCase().endsWith(".txt");
        if(!isTxt){
            throw new IOException("Invalid directory: " + file.toString() + " Directory must be a .txt file.");
        }
        if (!Files.exists(file))
            throw new FileNotFoundException(file.toString());

        if (!Files.isRegularFile(file))
            throw new IOException(file.toString());
    }

    private void validateDirectory(Path directory) throws IOException {

        
        if (!Files.exists(directory))
            throw new IOException(directory.toString());

        if (!Files.isDirectory(directory))
            throw new IOException(directory.toString());
    }

}

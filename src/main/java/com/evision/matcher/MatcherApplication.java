package com.evision.matcher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.evision.matcher.scanner.FileScanner;
import com.evision.matcher.scanner.FileScannerImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class MatcherApplication implements CommandLineRunner {

	
	@Qualifier("fileScannerImpl")
    private final FileScanner fileScanner;

	public static void main(String[] args) {
		SpringApplication.run(MatcherApplication.class, args);

		
	}

	@Override
    public void run(String... args) throws Exception {

        log.info("Reference file: {}", fileScanner.getReferenceFile());
        log.info("Pool files: {}", fileScanner.getPoolFiles());
    }

}

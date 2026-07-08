package com.evision.matcher.model;

import java.nio.file.Path;

public record MatchResult(Path file,double score) {}

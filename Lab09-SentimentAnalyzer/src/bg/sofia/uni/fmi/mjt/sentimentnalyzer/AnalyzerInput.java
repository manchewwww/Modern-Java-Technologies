package bg.sofia.uni.fmi.mjt.sentimentnalyzer;

import java.io.Reader;

public record AnalyzerInput(String inputID, Reader inputReader) {
}
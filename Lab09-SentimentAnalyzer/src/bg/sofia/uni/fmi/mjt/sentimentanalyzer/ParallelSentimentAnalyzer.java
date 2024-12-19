package bg.sofia.uni.fmi.mjt.sentimentanalyzer;

import java.util.Map;
import java.util.Set;

public class ParallelSentimentAnalyzer implements SentimentAnalyzerAPI {

    private final int workersCount;
    private final Set<String> stopWords;
    private final Map<String, SentimentScore> sentimentLexicon;

    /**
     * @param workersCount     number of consumer workers
     * @param stopWords        set containing stop words
     * @param sentimentLexicon map containing the sentiment lexicon, where the key is the word and the value is the sentiment score
     */
    public ParallelSentimentAnalyzer(int workersCount, Set<String> stopWords, Map<String, SentimentScore> sentimentLexicon) {
        this.workersCount = workersCount;
        this.stopWords = stopWords;
        this.sentimentLexicon = sentimentLexicon;
    }

    @Override
    public Map<String, SentimentScore> analyze(AnalyzerInput... input) {
        return Map.of();
    }

}

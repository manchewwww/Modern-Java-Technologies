package bg.sofia.uni.fmi.mjt.sentimentanalyzer;

import bg.sofia.uni.fmi.mjt.sentimentanalyzer.exceptions.SentimentAnalysisException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ParallelSentimentAnalyzer implements SentimentAnalyzerAPI {

    private static final int MIN_FOR_SENTIMENT_SCORE = -5;
    private static final int MAX_FOR_SENTIMENT_SCORE = 5;

    private final int workersCount;
    private final Set<String> stopWords;
    private final Map<String, SentimentScore> sentimentLexicon;

    public ParallelSentimentAnalyzer(int workersCount, Set<String> stopWords,
                                     Map<String, SentimentScore> sentimentLexicon) {
        this.workersCount = workersCount;
        this.stopWords = stopWords;
        this.sentimentLexicon = sentimentLexicon;
    }

    @Override
    public Map<String, SentimentScore> analyze(AnalyzerInput... input) {
        return analyzeWithoutBlockingQueue(input);
    }

    public Map<String, SentimentScore> analyzeWithoutBlockingQueue(AnalyzerInput... input) {
        Queue<AnalyzerInput> taskQueue = new LinkedList<>();
        Map<String, SentimentScore> result = new HashMap<>();
        Object lock = new Object();

        Thread[] producers = createProducerThreadsWithoutBlockingQueue(taskQueue, lock, input);
        for (Thread producer : producers) {
            producer.start();
        }

        Thread[] consumers = createConsumerThreadsWithoutBlockingQueue(taskQueue, lock, result);
        for (int i = 0; i < workersCount; i++) {
            consumers[i].start();
        }

        startStopperThreadsWithoutBlockingQueue(taskQueue);

        waitingConsumerThreadsEnd(consumers);

        return result;
    }

    public Map<String, SentimentScore> analyzeWithBlockingQueue(AnalyzerInput... input) {
        Map<String, SentimentScore> result = new ConcurrentHashMap<>();
        BlockingQueue<AnalyzerInput> taskQueue = new ArrayBlockingQueue<>(input.length);

        Thread[] producers = createProducerThreadsWithBlockingQueue(taskQueue, input);
        for (Thread producer : producers) {
            producer.start();
        }

        Thread[] consumers = createConsumerThreadWithBlockingQueue(taskQueue, result);
        for (Thread consumer : consumers) {
            consumer.start();
        }

        startStopperThreadsWithBlockingQueue(taskQueue);

        waitingConsumerThreadsEnd(consumers);

        return result;
    }

    private Thread[] createProducerThreadsWithoutBlockingQueue(Queue<AnalyzerInput> taskQueue, Object lock,
                                                               AnalyzerInput... input) {
        Thread[] producers = new Thread[input.length];

        for (int i = 0; i < producers.length; i++) {
            int finalI = i;
            producers[i] = new Thread(() -> {
                synchronized (lock) {
                    taskQueue.add(input[finalI]);
                    lock.notifyAll();
                }
            });
        }

        return producers;
    }

    private Thread[] createProducerThreadsWithBlockingQueue(BlockingQueue<AnalyzerInput> taskQueue,
                                                            AnalyzerInput... input) {
        Thread[] producers = new Thread[input.length];

        for (int i = 0; i < input.length; i++) {
            AnalyzerInput task = input[i];
            producers[i] = new Thread(() -> {
                try {
                    taskQueue.put(task);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new SentimentAnalysisException("Producer interrupted while adding task to queue", e);
                }
            });
        }

        return producers;
    }

    private Thread[] createConsumerThreadsWithoutBlockingQueue(Queue<AnalyzerInput> taskQueue, Object lock,
                                                               Map<String, SentimentScore> result) {
        Thread[] consumers = new Thread[workersCount];

        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Thread(() -> {
                while (true) {
                    AnalyzerInput task;
                    synchronized (lock) {
                        while (taskQueue.isEmpty()) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                throw new SentimentAnalysisException("Error occurred while taking task from queue", e);
                            }
                        }
                        task = taskQueue.poll();
                        if (task == null) {
                            lock.notifyAll();
                            break;
                        }
                        SentimentScore sentimentScore = getSentimentScore(analyzeText(task.inputReader()));
                        result.put(task.inputID(), sentimentScore);
                    }
                }
            });
        }

        return consumers;
    }

    private Thread[] createConsumerThreadWithBlockingQueue(BlockingQueue<AnalyzerInput> taskQueue,
                                                           Map<String, SentimentScore> result) {
        Thread[] consumers = new Thread[workersCount];
        for (int i = 0; i < workersCount; i++) {
            consumers[i] = new Thread(() -> {
                try {
                    while (true) {
                        AnalyzerInput task = taskQueue.take();

                        if (task.inputID().equals("Invalid")) {
                            break;
                        }

                        List<String> words = analyzeText(task.inputReader());
                        SentimentScore sentimentScore = getSentimentScore(words);

                        result.put(task.inputID(), sentimentScore);
                    }
                } catch (InterruptedException e) {
                    throw new SentimentAnalysisException("Error occurred while taking task from queue", e);
                }
            });
        }

        return consumers;
    }

    private void startStopperThreadsWithoutBlockingQueue(Queue<AnalyzerInput> taskQueue) {
        for (int i = 0; i < workersCount; i++) {
            new Thread(() -> taskQueue.add(null)).start();
        }
    }

    private void startStopperThreadsWithBlockingQueue(BlockingQueue<AnalyzerInput> taskQueue) {
        for (int i = 0; i < workersCount; i++) {
            new Thread(() -> {
                StringReader stringReader = new StringReader("");
                try {
                    taskQueue.put(new AnalyzerInput("Invalid", stringReader));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private void waitingConsumerThreadsEnd(Thread[] consumers) {
        for (Thread t : consumers) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new SentimentAnalysisException("Error occurred while waiting task to queue", e);
            }
        }
    }

    private List<String> analyzeText(Reader textReader) {
        try (BufferedReader bufferedReader = new BufferedReader(textReader)) {
            return bufferedReader.lines()
                .map(line -> line.replaceAll("\\p{Punct}", ""))
                .flatMap(line -> Stream.of(line.split(" ")))
                .map(String::toLowerCase)
                .filter(word -> !stopWords.contains(word))
                .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SentimentScore getSentimentScore(List<String> words) {
        int score = words.stream()
            .filter(sentimentLexicon::containsKey)
            .mapToInt(word -> sentimentLexicon.get(word).getScore())
            .sum();

        return SentimentScore.fromScore(
            Math.max(MIN_FOR_SENTIMENT_SCORE, Math.min(MAX_FOR_SENTIMENT_SCORE, score)));
    }

}
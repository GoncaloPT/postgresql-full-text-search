package pt.goncalo.poc.postgresfulltext;

import org.springframework.stereotype.Component;
import pt.goncalo.poc.postgresfulltext.topic.TopicRepository;

public class Demos {

    public interface DemoRun extends Comparable<DemoRun> {

        default DemoRunBenchmark execute() {
            long start = System.currentTimeMillis();
            run();
            long end = System.currentTimeMillis();
            return new DemoRunBenchmark(this.getClass().getSimpleName(), end - start);
        }

        default void executeAndPrintReport() {
            System.out.println("=======================");
            var result = execute();
            System.out.println("Demo " + result.name() + " took " + result.time() + "ms");
            System.out.println("=======================");
        }

        /**
         * Compare by priority, higher priority first
         *
         * @param other the object to be compared.
         * @return
         */
        @Override
        default int compareTo(DemoRun other) {
            return Integer.compare(other.priority(), priority());
        }

        void run();

        /**
         * no priority by default
         *
         * @return
         */
        default int priority() {
            return Integer.MIN_VALUE;
        }
    }

    record DemoRunBenchmark(String name, long time) {
    }

    @Component
    public static class InfoDemoRun implements DemoRun {
        private final TopicRepository topicRepository;

        public InfoDemoRun(TopicRepository topicRepository) {
            this.topicRepository = topicRepository;
        }

        @Override
        public void run() {
            System.out.println("Topic table has " + topicRepository.count() + " rows");

        }

        @Override
        public int priority() {
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Using LIKE for full text search
     */
    @Component
    public static class UsingLike implements DemoRun {

        private final TopicRepository topicRepository;

        public UsingLike(TopicRepository topicRepository) {
            this.topicRepository = topicRepository;
        }

        @Override
        public void run() {
            var result = topicRepository.findByFullTextSearchUsingLike("words");
            System.out.println("UsingLike found " + result.size() + " results");
            var result2 = topicRepository.findByFullTextSearchUsingLike("music");
            System.out.println("UsingLike found " + result2.size() + " results for music");

        }
    }

    /**
     * Using full-text search with tsquery
     */
    @Component
    public static class FullTextNativeQuery implements DemoRun {

        private final TopicRepository topicRepository;

        public FullTextNativeQuery(TopicRepository topicRepository) {
            this.topicRepository = topicRepository;
        }

        @Override
        public void run() {
            var result = topicRepository.findUsingWebSearch("words");
            System.out.println("FullTextNativeQuery found " + result.size() + " results for words");
            var result2 = topicRepository.findUsingWebSearch("music");
            System.out.println("FullTextNativeQuery found " + result2.size() + " results for music");

        }
    }

    /**
     * Using full-text search with tsquery
     */
    @Component
    public static class FullTextDemoRunSpringData implements DemoRun {

        private final TopicRepository topicRepository;

        public FullTextDemoRunSpringData(TopicRepository topicRepository) {
            this.topicRepository = topicRepository;
        }

        @Override
        public void run() {
            var result = topicRepository.findAll(TopicRepository.hasText("words"));
            System.out.println("FullTextDemoRunSpringData found " + result.size() + " results for words");

            var result2 = topicRepository.findAll(TopicRepository.hasText("music"));
            System.out.println("FullTextDemoRunSpringData found " + result2.size() + " results for music");

        }
    }
}

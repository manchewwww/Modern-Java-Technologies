package bg.sofia.uni.fmi.mjt.news.builder;

import java.util.Set;

public class Arguments {

    private String keyword;
    private String category;
    private String country;
    private int page;
    private int pageSize;

    public String getKeyword() {
        return keyword;
    }

    public String getCategory() {
        return category;
    }

    public String getCountry() {
        return country;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public static ArgumentsBuilder builder(Set<String> keywords) {
        if (keywords == null) {
            throw new IllegalArgumentException("Keyword cannot be null in builder argument");
        }
        return new ArgumentsBuilder(keywords);
    }

    private Arguments(ArgumentsBuilder builder) {
        this.keyword = builder.keyword;
        this.category = builder.category;
        this.country = builder.country;
        this.page = builder.page;
        this.pageSize = builder.pageSize;
    }

    public static class ArgumentsBuilder {

        private String keyword;
        private String category;
        private String country;
        private int page;
        private int pageSize;

        private ArgumentsBuilder(Set<String> keywords) {
            this.keyword = String.join("+", keywords);
        }

        public ArgumentsBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public ArgumentsBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public ArgumentsBuilder setPage(int page) {
            this.page = page;
            return this;
        }

        public ArgumentsBuilder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Arguments build() {
            return new Arguments(this);
        }

    }

}

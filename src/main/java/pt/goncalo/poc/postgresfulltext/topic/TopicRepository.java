package pt.goncalo.poc.postgresfulltext.topic;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import pt.goncalo.poc.postgresfulltext.PostgresfulltextApplication;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Using a {@link JpaSpecificationExecutor} allow to use tsquery specific queries while still using Spring Data JPA.
 * To access those features, you need to use the methods which ask for a Specification as parameter.
 */
public interface TopicRepository extends JpaRepository<Topic, UUID>, JpaSpecificationExecutor<Topic> {

    @Query("""
                      SELECT t FROM topic t
                      WHERE t.title LIKE %?1% 
                          OR t.description LIKE %?1%
                          OR t.extraDescription LIKE %?1%
            """)
    List<Topic> findByFullTextSearchUsingLike(String text);

    @Query(value = "select * from topic where search_vector_en @@ websearch_to_tsquery(:searchVectorEn)",
            nativeQuery = true)
    List<Topic> findUsingWebSearch(String searchVectorEn);

    /**
     * Spefication to search for text using PostgreSQL full text search capabilities.
     * Please see {@link PostgresfulltextApplication.FullTextSearchAwarePostgreSQLDialect} for more details.
     *
     * @param search
     * @return
     */
    static Specification<Topic> hasText(String search) {
        Objects.requireNonNull(search);
        return (root, query, cb) ->
                cb.isTrue(
                        cb.function(
                                "tsvector_query",
                                Boolean.class,
                                root.get("searchVectorEn"),
                                cb.function("websearch_to_tsquery", String.class, cb.literal(search))
                        )
                );

    }


}

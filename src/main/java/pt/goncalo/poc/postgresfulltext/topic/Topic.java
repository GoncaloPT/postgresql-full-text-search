package pt.goncalo.poc.postgresfulltext.topic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "topic")
public class Topic {
    @Id
    @Column(name = "topic_id")
    private UUID id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "extra_description")
    private String extraDescription;

    @Column(name = "search_vector_en", columnDefinition = "tsvector", insertable = false, updatable = false)
    private String searchVectorEn;

    public Topic() {
    }

    public Topic(UUID id, String title, String description, String extraDescription, String searchVectorEn) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.extraDescription = extraDescription;
        this.searchVectorEn = searchVectorEn;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraDescription() {
        return extraDescription;
    }

    public void setExtraDescription(String extraDescription) {
        this.extraDescription = extraDescription;
    }

    public String getSearchVectorEn() {
        return searchVectorEn;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", extraDescription=" + extraDescription +
                '}';
    }
}

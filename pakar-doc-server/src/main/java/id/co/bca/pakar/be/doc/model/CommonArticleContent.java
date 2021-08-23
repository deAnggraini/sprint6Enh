package id.co.bca.pakar.be.doc.model;

import javax.persistence.*;

/**
 * structure of article template content
 * every article could have have parent, and parent could have e child
 */
@MappedSuperclass
public class CommonArticleContent extends EntityBase {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description = new String();

    @Column(name = "sort", columnDefinition = "integer DEFAULT 1")
    private Long sort;

    @Column(name = "level", columnDefinition = "integer DEFAULT 1")
    private Long level;

    @Column(name = "parent", columnDefinition = "integer DEFAULT 0")
    private Long parent;

    @Column(name = "topic_caption")
    private String topicCaption;

    @Column(name = "topic_content", columnDefinition = "TEXT", length = Integer.MAX_VALUE)
    private String topicContent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getTopicCaption() {
        return topicCaption;
    }

    public void setTopicCaption(String topicCaption) {
        this.topicCaption = topicCaption;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }
}

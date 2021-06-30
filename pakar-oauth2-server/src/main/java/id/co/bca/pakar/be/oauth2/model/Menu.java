package id.co.bca.pakar.be.oauth2.model;

import javax.persistence.*;

@Entity
@Table(name = "r_menu")
public class Menu extends EntityBase {
    @Id
    @SequenceGenerator(name = "menuSeqGen", sequenceName = "menuSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "menuSeqGen")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "sort")
    private Long sort;
    @Column(name = "level")
    private Long level;
    @Column(name = "parent")
    private Long parent;
    @Column(name = "edit")
    private Boolean edit;
    @Column(name = "uri")
    private String uri;
    @Column(name = "location")
    private String location;
    @Column(name = "location_text")
    private String location_text;
    @Column(name = "nav")
    private String navigation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation_text() {
        return location_text;
    }

    public void setLocation_text(String location_text) {
        this.location_text = location_text;
    }

    public String getNavigation() {
        return navigation;
    }

    public void setNavigation(String navigation) {
        this.navigation = navigation;
    }
}

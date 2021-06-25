package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

public class StructureDto {
    @JsonProperty("id")
    private Long id;
    @NotEmpty(message = "name is required")
    @Size(max = 50, message = "maximum length 50 characters")
//    @Pattern(regexp = "[A-Za-z\\s]+$")
    @JsonProperty("name")
    private String name;
//    @NotNull
//    @Size(max = 200)
//    @Pattern(regexp = "[A-Za-z\\s]+$")
    @JsonProperty("desc")
    private String description;

//    @NotNull
//    @Min(1)
    @JsonProperty("sort")
    private Long sort=1L;

//    @NotNull
//    @Min(1)
    @JsonProperty("level")
    private Long level=1L;
    @Min(0)
    @JsonProperty("parent")
    private Long parent = 0L;

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

}

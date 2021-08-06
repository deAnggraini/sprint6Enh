package id.co.bca.pakar.be.wf.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * container class for breadcumb in card
 */
public class BreadcumbStructureDto implements Comparable <BreadcumbStructureDto>{
    @JsonProperty("id")
    protected Long id;
    @JsonProperty("title")
    protected String name;
    @JsonIgnore
    private Long level;

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

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    @Override
    public int compareTo(@NotNull BreadcumbStructureDto o) {
        return this.id.intValue() - o.getId().intValue();
    }
}

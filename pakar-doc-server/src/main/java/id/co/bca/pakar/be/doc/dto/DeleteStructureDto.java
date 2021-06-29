package id.co.bca.pakar.be.doc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class DeleteStructureDto {
    @NotNull(message = "id is required")
    @Min(value = 0, message = "minimum value is 0")
    @JsonProperty("id")
    protected Long structureId;
    @JsonProperty("changeTo")
    private List<ChangeToStructureDto> changeTo = new ArrayList<ChangeToStructureDto>();

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public List<ChangeToStructureDto> getChangeTo() {
        return changeTo;
    }

    public void setChangeTo(List<ChangeToStructureDto> changeTo) {
        this.changeTo = changeTo;
    }
}

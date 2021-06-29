package id.co.bca.pakar.be.doc.dto;

import java.util.List;

public class MultiStructureDto {
    private List<StructureWithFileDto> structureWithFileDtoList;

    public List<StructureWithFileDto> getStructureWithFileDtoList() {
        return structureWithFileDtoList;
    }

    public void setStructureWithFileDtoList(List<StructureWithFileDto> structureWithFileDtoList) {
        this.structureWithFileDtoList = structureWithFileDtoList;
    }
}

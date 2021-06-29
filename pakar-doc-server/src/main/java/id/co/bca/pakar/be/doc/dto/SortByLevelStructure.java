package id.co.bca.pakar.be.doc.dto;

import java.util.Comparator;

public class SortByLevelStructure implements Comparator<StructureDto> {
    @Override
    public int compare(StructureDto dto1, StructureDto dto2) {
        return dto1.getLevel().intValue() - dto2.getLevel().intValue();
    }
}

package id.co.bca.pakar.be.doc.dto.comparator;

import id.co.bca.pakar.be.doc.dto.StructureWithFileDto;

import java.util.Comparator;

public class StructureComparator implements Comparator<StructureWithFileDto> {
    @Override
    public int compare(StructureWithFileDto o1, StructureWithFileDto o2) {
        int result = o1.getLevel().compareTo(o2.getLevel());
        if (result != 0) {
            return result;
        }
        return result = o1.getSort().compareTo(o2.getSort());
    }
}

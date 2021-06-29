package id.co.bca.pakar.be.doc.dto.comparator;

import id.co.bca.pakar.be.doc.dto.MenuDto;

import java.util.Comparator;

public class MenuComparator implements Comparator<MenuDto> {
    @Override
    public int compare(MenuDto o1, MenuDto o2) {
        int result = o1.getLevel().intValue() - o2.getLevel().intValue();
        if (result == 0) {
            return o1.getOrder().intValue() < o2.getOrder().intValue() ? -1
                    :  o1.getOrder().intValue() >  o2.getOrder().intValue() ? 1
                    : 0;
        }
        return result;
    }
}

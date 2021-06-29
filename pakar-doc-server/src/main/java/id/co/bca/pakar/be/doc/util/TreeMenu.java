package id.co.bca.pakar.be.doc.util;

import id.co.bca.pakar.be.doc.dto.MenuDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TreeMenu {
    public List<MenuDto> menuTree(List<MenuDto> arrMenu) {
        //Query all menus
        List<MenuDto> menus = arrMenu;
        //The returned MenuDto tree
        List<MenuDto> rootMenus = new ArrayList<>();
        for (MenuDto menuDto : menus) {
            //Pid (superior Id) is 0 is the root MenuDto
            int parentValue = menuDto.getParent() == null ? 0 : menuDto.getParent().intValue();
            if (0 == parentValue) {
                rootMenus.add(menuDto);
            }
        }

        // sorting root menu
        Collections.sort(rootMenus, new Comparator<MenuDto>() {
            @Override
            public int compare(MenuDto o1, MenuDto o2) {
                return o1.getOrder().intValue() - o2.getOrder().intValue();
            }
        });

        //Traverse to find the secondary MenuDto (the id of the root MenuDto is compared with the pid in all menus)
        for (MenuDto rootMenu : rootMenus) {
            List<MenuDto> child = getChild(rootMenu.getId(), menus);
            // sorting root menu
            Collections.sort(child, new Comparator<MenuDto>() {
                @Override
                public int compare(MenuDto o1, MenuDto o2) {
                    return o1.getOrder().intValue() - o2.getOrder().intValue();
                }
            });
            rootMenu.setMenuChilds(child);
        }
        return rootMenus;
    }


    /**
     * Recursively get the lower MenuDto
     *
     * @param pid   superior Id
     * @param menus all menus
     * @return
     */
    public List<MenuDto> getChild(Long pid, List<MenuDto> menus) {
        //Submenu list
        List<MenuDto> childList = new ArrayList<>();
        for (MenuDto menuDto : menus) {
            int parentValue = menuDto.getParent() != null ? menuDto.getParent().intValue() : 0;
            if (pid.intValue() == parentValue) {
                childList.add(menuDto);
            }
        }
        //Traverse to get the submenu of the submenu
        for (MenuDto menuDto : childList) {
            List<MenuDto> child = getChild(menuDto.getId(), menus);
            Collections.sort(child, new Comparator<MenuDto>() {
                @Override
                public int compare(MenuDto o1, MenuDto o2) {
                    return o1.getOrder().intValue() - o2.getOrder().intValue();
                }
            });
            menuDto.setMenuChilds(child);
        }
        //Recursive export childList length is 0
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }
}

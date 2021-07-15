package id.co.bca.pakar.be.doc.util;

import id.co.bca.pakar.be.doc.dto.ContentTemplateDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TreeContents {
    public List<ContentTemplateDto> menuTree(List<ContentTemplateDto> arrMenu) {
        //Query all contents
        List<ContentTemplateDto> menus = arrMenu;
        //The returned ContentTemplateDto tree
        List<ContentTemplateDto> rootMenus = new ArrayList<>();
        for (ContentTemplateDto menuDto : menus) {
            //Pid (superior Id) is 0 is the root ContentTemplateDto
            int parentValue = menuDto.getParent() == null ? 0 : menuDto.getParent().intValue();
            if (0 == parentValue) {
                rootMenus.add(menuDto);
            }
        }

        // sorting root menu
        Collections.sort(rootMenus, new Comparator<ContentTemplateDto>() {
            @Override
            public int compare(ContentTemplateDto o1, ContentTemplateDto o2) {
                return o1.getOrder().intValue() - o2.getOrder().intValue();
            }
        });

        //Traverse to find the secondary ContentTemplateDto (the id of the root ContentTemplateDto is compared with the pid in all menus)
        for (ContentTemplateDto rootMenu : rootMenus) {
            List<ContentTemplateDto> child = getChild(rootMenu.getId(), menus);
            // sorting root menu
            Collections.sort(child, new Comparator<ContentTemplateDto>() {
                @Override
                public int compare(ContentTemplateDto o1, ContentTemplateDto o2) {
                    return o1.getOrder().intValue() - o2.getOrder().intValue();
                }
            });
            rootMenu.setChilds(child);
        }
        return rootMenus;
    }


    /**
     * Recursively get the lower ContentTemplateDto
     *
     * @param pid   superior Id
     * @param menus all menus
     * @return
     */
    public List<ContentTemplateDto> getChild(Long pid, List<ContentTemplateDto> menus) {
        //Submenu list
        List<ContentTemplateDto> childList = new ArrayList<>();
        for (ContentTemplateDto menuDto : menus) {
            int parentValue = menuDto.getParent() != null ? menuDto.getParent().intValue() : 0;
            if (pid.intValue() == parentValue) {
                childList.add(menuDto);
            }
        }
        //Traverse to get the submenu of the submenu
        for (ContentTemplateDto menuDto : childList) {
            List<ContentTemplateDto> child = getChild(menuDto.getId(), menus);
            Collections.sort(child, new Comparator<ContentTemplateDto>() {
                @Override
                public int compare(ContentTemplateDto o1, ContentTemplateDto o2) {
                    return o1.getOrder().intValue() - o2.getOrder().intValue();
                }
            });
            menuDto.setChilds(child);
        }
        //Recursive export childList length is 0
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }
}

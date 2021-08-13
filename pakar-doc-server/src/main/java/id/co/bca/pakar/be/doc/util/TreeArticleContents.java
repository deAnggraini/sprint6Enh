package id.co.bca.pakar.be.doc.util;

import id.co.bca.pakar.be.doc.dto.ArticleContentDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TreeArticleContents {
    public List<ArticleContentDto> menuTree(List<ArticleContentDto> arrMenu) {
        //Query all contents
        List<ArticleContentDto> menus = arrMenu;
        //The returned ContentTemplateDto tree
        List<ArticleContentDto> rootMenus = new ArrayList<>();
        for (ArticleContentDto menuDto : menus) {
            //Pid (superior Id) is 0 is the root ContentTemplateDto
            int parentValue = menuDto.getParent() == null ? 0 : menuDto.getParent().intValue();
            if (0 == parentValue) {
                rootMenus.add(menuDto);
            }
        }

        // sorting root menu
        Collections.sort(rootMenus, new Comparator<ArticleContentDto>() {
            @Override
            public int compare(ArticleContentDto o1, ArticleContentDto o2) {
                return o1.getSort().intValue() - o2.getSort().intValue();
            }
        });

        //Traverse to find the secondary ContentTemplateDto (the id of the root ContentTemplateDto is compared with the pid in all menus)
        for (ArticleContentDto rootMenu : rootMenus) {
            List<ArticleContentDto> child = getChild(rootMenu.getId(), menus);
            // sorting root menu
            Collections.sort(child, new Comparator<ArticleContentDto>() {
                @Override
                public int compare(ArticleContentDto o1, ArticleContentDto o2) {
                    return o1.getSort().intValue() - o2.getSort().intValue();
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
    public List<ArticleContentDto> getChild(Long pid, List<ArticleContentDto> menus) {
        //Submenu list
        List<ArticleContentDto> childList = new ArrayList<>();
        for (ArticleContentDto menuDto : menus) {
            int parentValue = menuDto.getParent() != null ? menuDto.getParent().intValue() : 0;
            if (pid.intValue() == parentValue) {
                childList.add(menuDto);
            }
        }
        //Traverse to get the submenu of the submenu
        for (ArticleContentDto menuDto : childList) {
            List<ArticleContentDto> child = getChild(menuDto.getId(), menus);
            Collections.sort(child, new Comparator<ArticleContentDto>() {
                @Override
                public int compare(ArticleContentDto o1, ArticleContentDto o2) {
                    return o1.getSort().intValue() - o2.getSort().intValue();
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

package id.co.bca.pakar.be.oauth2.service.imp;

import id.co.bca.pakar.be.oauth2.common.Constant;
import id.co.bca.pakar.be.oauth2.dao.*;
import id.co.bca.pakar.be.oauth2.dto.MenuDto;
import id.co.bca.pakar.be.oauth2.model.*;
import id.co.bca.pakar.be.oauth2.service.MenuService;
import id.co.bca.pakar.be.oauth2.util.TreeMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private StructureRepository structureRepository;
    @Autowired
    private MenuImageRepository menuImageRepository;
    @Autowired
    private MenuIconRepository menuIconRepository;
    @Autowired
    private StructureImageRepository structureImageRepository;
    @Autowired
    private StructureIconRepository structureIconRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public List<MenuDto> getMenus(String token, String username) throws Exception {
        try {
            List<MenuDto> allMenus = new ArrayList<>();
            logger.info("get all top menu");
            List<Menu> topMenus = menuRepository.getAllTopMenuById(username);
            List<MenuDto> topTreeMenu = new TreeMenu().menuTree(mapToList(topMenus));
            allMenus.addAll(topTreeMenu);

            logger.info("get all structure menu");
            List<UserRole> uRoles = roleRepository.findUserRolesByUsername(username);
            for(UserRole ur : uRoles) {
                if(ur.getRole().getId().equals(Constant.Roles.ROLE_READER)){
                    Iterable<Structure> menus = structureRepository.findAllForReader();
                    List<MenuDto> treeMenu = new TreeMenu().menuTree(mapToListIterable(menus));
                    allMenus.addAll(treeMenu);
                } else {
                    Iterable<Structure> menus = structureRepository.findAll();
                    List<MenuDto> treeMenu = new TreeMenu().menuTree(mapToListIterable(menus));
                    allMenus.addAll(treeMenu);
                }
            }

            logger.info("get all bottom menu");
            List<Menu> bottomMenus = menuRepository.getAllBottomMenuById(username);
            List<MenuDto> bottomTreeMenu = new TreeMenu().menuTree(mapToList(bottomMenus));
            allMenus.addAll(bottomTreeMenu);

            return allMenus;
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("failed get menu exception", e);
        }
    }

    private List<MenuDto> mapToList(List<Menu> iterable) {
        List<MenuDto> listOfMenus = new ArrayList<>();
        for (Menu menu : iterable) {
            MenuDto menuDto = new MenuDto();
            menuDto.setId(menu.getId());
            menuDto.setLevel(menu.getLevel());
            menuDto.setOrder(menu.getSort());
            menuDto.setParent(menu.getParent());
            menuDto.setMenuName(menu.getName());
            menuDto.setMenuDescription(menu.getDescription());
            menuDto.setUri(menu.getUri());
            menuDto.setEditStatus(menu.getEdit());
            try {
                MenuIcons mic = menuIconRepository.findIconbyMenuId(menu.getId());
                menuDto.setIconUri(mic != null ? mic.getIcons().getUri() : "");
            } catch (Exception e) {
                logger.error("exception",e);
            }
            try {
                MenuImages mim = menuImageRepository.findImagebyMenuId(menu.getId());
                menuDto.setImageUri(mim != null ? mim.getImages().getUri() : "");
            } catch (Exception e) {
                logger.error("exception",e);
            }
            listOfMenus.add(menuDto);
        }
        return listOfMenus;
    }

    private List<MenuDto> mapToListIterable(Iterable<Structure> iterable) {
        List<MenuDto> listOfMenus = new ArrayList<>();
        for (Structure structure : iterable) {
            MenuDto menuDto = new MenuDto();
            menuDto.setId(structure.getId());
            menuDto.setLevel(structure.getLevel());
            menuDto.setOrder(structure.getSort());
            menuDto.setMenuName(structure.getStructureName());
            menuDto.setMenuDescription(structure.getStructureDescription());
            menuDto.setParent(structure.getParentStructure());
            menuDto.setUri(structure.getUri());
            menuDto.setEditStatus(structure.getEdit());
            menuDto.setHasArticle(structure.getHasArticle());
            try {
                StructureIcons sic = structureIconRepository.findByStructureId(structure.getId());
                menuDto.setIconUri(sic != null ? sic.getIcons().getUri() : "");
            } catch (Exception e) {
                logger.error("exception",e);
            }
            try {
                StructureImages sim = structureImageRepository.findByStructureId(structure.getId());
                menuDto.setImageUri(sim != null ? sim.getImages().getUri() : "");
            } catch (Exception e) {
                logger.error("exception",e);
            }
            listOfMenus.add(menuDto);
        }
        return listOfMenus;
    }
}

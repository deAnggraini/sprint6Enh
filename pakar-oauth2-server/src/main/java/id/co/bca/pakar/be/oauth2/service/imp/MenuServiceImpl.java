package id.co.bca.pakar.be.oauth2.service.imp;

import id.co.bca.pakar.be.oauth2.dao.MenuIconRepository;
import id.co.bca.pakar.be.oauth2.dao.MenuImageRepository;
import id.co.bca.pakar.be.oauth2.dao.MenuRepository;
import id.co.bca.pakar.be.oauth2.dao.StructureRepository;
import id.co.bca.pakar.be.oauth2.dto.MenuDto;
import id.co.bca.pakar.be.oauth2.model.Menu;
import id.co.bca.pakar.be.oauth2.model.Structure;
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

    @Override
    @Transactional
    public List<MenuDto> getMenus(String token, String username) throws Exception {
        try {
            List<MenuDto> allMenus = new ArrayList<>();
            logger.info("get all top menu");
            List<Menu> topMenus = menuRepository.getAllTopMenu();
            List<MenuDto> topTreeMenu = new TreeMenu().menuTree(mapToList(topMenus));
            allMenus.addAll(topTreeMenu);

            // TODO structure menu insert here
            logger.info("get all menu");
            Iterable<Structure> menus = structureRepository.findAll();
            List<MenuDto> treeMenu = new TreeMenu().menuTree(mapToListIterable(menus));
            allMenus.addAll(treeMenu);

            logger.info("get all bottom menu");
            List<Menu> bottomMenus = menuRepository.getAllBottomMenu();
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
            // Set iconUri
            //Set ImageUri
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
            menuDto.setParent(structure.getParentStructure());
            menuDto.setUri(structure.getUri());
            // Set iconUri
            //Set ImageUri
            listOfMenus.add(menuDto);
        }
        return listOfMenus;
    }
}

package id.co.bca.pakar.be.oauth2.service.imp;
import id.co.bca.pakar.be.oauth2.dao.MenuIconRepository;
import id.co.bca.pakar.be.oauth2.dao.MenuImageRepository;
import id.co.bca.pakar.be.oauth2.dao.MenuRepository;
import id.co.bca.pakar.be.oauth2.dto.IconDto;
import id.co.bca.pakar.be.oauth2.dto.ImageDto;
import id.co.bca.pakar.be.oauth2.model.Menu;
import id.co.bca.pakar.be.oauth2.model.MenuIcons;
import id.co.bca.pakar.be.oauth2.model.MenuImages;
import id.co.bca.pakar.be.oauth2.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import id.co.bca.pakar.be.oauth2.dto.MenuDto;
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
    private MenuImageRepository menuImageRepository;
    @Autowired
    private MenuIconRepository menuIconRepository;

    @Override
    public List<MenuDto> getMenu(String token, String username) {
        List<MenuDto> burgerMenu = new ArrayList<MenuDto>();
        logger.info("masuk sini yaa");
        MenuDto tMenu = new MenuDto();
        List<Menu> child = new ArrayList<>();
        try {
            List<Menu> menu = menuRepository.getAllMenu();
            logger.info("menu service " + menu);
            if(menu != null) {
                for(Menu menuTemp : menu) {
                    IconDto icon = new IconDto();
                    ImageDto imageDto = new ImageDto();
                    tMenu.setId(menuTemp.getId());
                    tMenu.setMenuName(menuTemp.getMenuName());
                    tMenu.setMenuDescription(menuTemp.getMenuDescription());
                    tMenu.setLevel(menuTemp.getLevel());
                    tMenu.setOrder(menuTemp.getOrder());
                    tMenu.setMenuName(menuTemp.getMenuName());
                    MenuIcons iconMenu = menuIconRepository.findIconbyMenuId(menuTemp.getId());
                    MenuImages imageMenu = menuImageRepository.findImagebyMenuId(menuTemp.getId());
                    logger.info("icon = " + iconMenu);
                    logger.info("images = " + imageMenu);

//                    tMenu.setIconUri();
//                    tMenu.setImageUri();
                }
                burgerMenu.add(tMenu);
            }

            return burgerMenu;
        } catch (Exception e) {
            logger.error("exception ", e);
//            throw new Exception(e);
        }

        return  null;
    }
}

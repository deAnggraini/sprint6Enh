package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.dao.ThemeComponentHomepageRepository;
import id.co.bca.pakar.be.doc.dao.ThemeImageRepository;
import id.co.bca.pakar.be.doc.dao.ThemeRepository;
import id.co.bca.pakar.be.doc.dto.ThemeDto;
import id.co.bca.pakar.be.doc.dto.ThemeHeaderDto;
import id.co.bca.pakar.be.doc.dto.ThemeHomepageDto;
import id.co.bca.pakar.be.doc.dto.ThemeLoginDto;
import id.co.bca.pakar.be.doc.model.Theme;
import id.co.bca.pakar.be.doc.model.ThemeComponentHomepage;
import id.co.bca.pakar.be.doc.model.ThemeImage;
import id.co.bca.pakar.be.doc.service.ThemeService;
import id.co.bca.pakar.be.doc.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final Logger logger = LoggerFactory.getLogger(ThemeServiceImpl.class);

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ThemeImageRepository themeImageRepository;

    @Autowired
    private ThemeComponentHomepageRepository themeComponentHomepageRepository;

    @Override
    public ThemeDto getThemeList() {

        ThemeHeaderDto themeHeaderDto = new ThemeHeaderDto();
        ThemeHomepageDto themeHomepageDto = new ThemeHomepageDto();
        ThemeLoginDto themeLoginDto = new ThemeLoginDto();
        ThemeDto themeDto = new ThemeDto();
        List<ThemeImage> themeImageList;
        List<ThemeComponentHomepage> themeComponentHomepageList;

        Theme theme = themeRepository.findAllTheme();
        themeImageList = themeImageRepository.findAllThemeImage();
        themeComponentHomepageList = themeComponentHomepageRepository.findAllThemeComponentHomepage();

        themeHeaderDto.setBackground(theme.getBackground());
        themeHeaderDto.setColor(theme.getColor());
        themeHeaderDto.setHover(theme.getHover());

        if (themeImageList.size() > 0){
            for(int i = 0; i < themeImageList.size(); i ++){
                if (themeImageList.get(i).getImageType().equalsIgnoreCase(Constant.IMAGE_TYPE_HEADER)){
                    themeHomepageDto.setBg_img_top(themeImageList.get(i).getImage_name());
                }else if (themeImageList.get(i).getImageType().equalsIgnoreCase(Constant.IMAGE_TYPE_LOGIN)){
                    themeLoginDto.setImage(themeImageList.get(i).getImage_name());
                }
            }
        }
        logger.info("Theme Component "+themeComponentHomepageList.size());
        logger.info("Theme Component "+themeComponentHomepageList.contains("component_name"));

        List<String> listComponent = new ArrayList<>();

        themeDto.setHeader(themeHeaderDto);
        themeDto.setHomepage(themeHomepageDto);
        themeDto.setLogin(themeLoginDto);

        return themeDto;
    }
}

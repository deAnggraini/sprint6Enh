package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.dao.ThemeImageRepository;
import id.co.bca.pakar.be.doc.dao.ThemeRepository;
import id.co.bca.pakar.be.doc.dto.ThemeDto;
import id.co.bca.pakar.be.doc.dto.ThemeHeaderDto;
import id.co.bca.pakar.be.doc.dto.ThemeHomepageDto;
import id.co.bca.pakar.be.doc.model.Theme;
import id.co.bca.pakar.be.doc.model.ThemeImage;
import id.co.bca.pakar.be.doc.service.ThemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final Logger logger = LoggerFactory.getLogger(ThemeServiceImpl.class);

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ThemeImageRepository themeImageRepository;

    @Override
    public ThemeDto getThemeList() {

        ThemeHeaderDto themeHeaderDto = new ThemeHeaderDto();
        ThemeHomepageDto themeHomepageDto = new ThemeHomepageDto();
        ThemeDto themeDto = new ThemeDto();


        Theme theme = themeRepository.findAllTheme();
        ThemeImage themeImage = themeImageRepository.findAllThemeImage();
        logger.info("theme data "+theme.toString());
        logger.info("theme data image "+themeImage.toString());


        themeHeaderDto.setBackground(theme.getBackground());
        themeHeaderDto.setColor(theme.getColor());
        themeHeaderDto.setHover(theme.getHover());
        themeDto.getHeader();
        return themeDto;
    }
}

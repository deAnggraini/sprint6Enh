package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.dao.SkReffRepository;
import id.co.bca.pakar.be.doc.dto.SkReffDto;
import id.co.bca.pakar.be.doc.exception.DataNotFoundException;
import id.co.bca.pakar.be.doc.model.SkRefference;
import id.co.bca.pakar.be.doc.service.SkRefferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkRefferenceServiceImpl implements SkRefferenceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SkReffRepository skReffRepository;

    @Override
    public List<SkReffDto> findSkReffs(String keyword) throws Exception {
        try {
            Iterable<SkRefference> skRefferences = skReffRepository.searchSkReffLike(keyword);
            return mapToListSkReffDto(skRefferences);
        } catch (Exception e) {
            logger.error("exception",e);
            throw new DataNotFoundException("exception",e);
        }
    }

    private List<SkReffDto> mapToListSkReffDto(Iterable<SkRefference> iterable) {
        List<SkReffDto> listOfDtos = new ArrayList<>();
        for (SkRefference entity : iterable) {
            SkReffDto dto = new SkReffDto();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setSkNumber(entity.getSkNumber());
            listOfDtos.add(dto);
        }
        return listOfDtos;
    }
}


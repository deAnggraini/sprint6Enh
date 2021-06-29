package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.dao.*;
import id.co.bca.pakar.be.doc.dto.*;
import id.co.bca.pakar.be.doc.exception.DataNotFoundException;
import id.co.bca.pakar.be.doc.exception.InvalidLevelException;
import id.co.bca.pakar.be.doc.model.*;
import id.co.bca.pakar.be.doc.service.StructureService;
import id.co.bca.pakar.be.doc.util.FileUploadUtil;
import id.co.bca.pakar.be.doc.util.TreeMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class StructureServiceImp implements StructureService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.security.jwt.signingkey}")
    private String signingKey;
    @Value("${upload.path.menu}")
    private String pathMenu;
    @Value("${upload.path.category}")
    private String pathCategory;

    @Autowired
    private StructureRepository structureRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private IconRepository iconRepository;
    @Autowired
    private StructureImageRepository structureImageRepository;
    @Autowired
    private StructureIconRepository structureIconRepository;

    /**
     * create structure methode
     *
     * @param dto
     * @param image
     * @param icon
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public StructureDto add(String username, StructureDto dto, MultipartFile image, MultipartFile icon) throws Exception {
        try {
            logger.info("add category");

            Images _images = null;
            if (!image.isEmpty()) {
                logger.debug("image size {}", image.getSize());
                String location = pathCategory;
                logger.debug("folder location {}", location);
                String fileName = StringUtils.cleanPath(image.getOriginalFilename());
                logger.debug("file name {}", fileName);

                logger.debug("original category file name {}", image.getOriginalFilename());
                Path path = Paths.get(location + image.getOriginalFilename());

                logger.info("saving image");
                Images images = new Images();
                images.setCreatedBy(username);
                images.setImageName(path.getFileName().toString());
                images.setUri(path.toAbsolutePath().toString());
                _images = imageRepository.save(images);

                logger.debug("iamge path file {}", path.toAbsolutePath().toString());
                // save image to folder
                logger.info("saving image to share folder");

                FileUploadUtil.saveFile(location, image);
            }

            Icons _icon = null;
            if (!icon.isEmpty()) {
                String location = pathCategory;
                logger.debug("folder location {}", location);
                logger.debug("icon file name {}", icon.getOriginalFilename());
                Path path = Paths.get(location + icon.getOriginalFilename());

                logger.info("saving icon");
                Icons icons = new Icons();
                icons.setCreatedBy(username);
                logger.debug("save file name to db {}", path.getFileName().toString());
                icons.setIconName(path.getFileName().toString());
                logger.debug("save path file to db {}", path.toAbsolutePath().toString());
                icons.setUri(path.toAbsolutePath().toString());
                _icon = iconRepository.save(icons);

                // save image to folder
                logger.info("saving icon to share folder");
                FileUploadUtil.saveFile(location, icon);
            }

            logger.info("saving structure");
            Structure structure = new Structure();
            structure.setCreatedBy(username);
            structure.setStructureName(dto.getName());
            structure.setStructureDescription(dto.getDesc());
            structure.setLevel(dto.getLevel());
            structure.setSort(dto.getSort());
            Structure _structure = structureRepository.save(structure);

            if (_images != null) {
                logger.info("saving structure image mapper");
                StructureImages sim = new StructureImages();
                sim.setCreatedBy(username);
                sim.setStructure(_structure);
                sim.setImages(_images);
                structureImageRepository.save(sim);
            }

            if (_icon != null) {
                logger.info("saving structure icon mapper");
                StructureIcons sic = new StructureIcons();
                sic.setCreatedBy(username);
                sic.setStructure(_structure);
                sic.setIcons(_icon);
                structureIconRepository.save(sic);
            }
            dto.setId(_structure.getId());
            return dto;
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * add new structure
     *
     * @param username
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public StructureResponseDto add(String username, StructureWithFileDto dto) throws Exception {
        try {
            logger.info("add category");
            StructureResponseDto _dto = new StructureResponseDto();

            /*
			get existing structure from db with param structure id
			 */
            Optional<Structure> parentOp = structureRepository.findById(dto.getParent());
            logger.debug("structure result from db {}", parentOp);
            if (parentOp.isEmpty()) {
                if (dto.getLevel() > 1) {
                    logger.info("not found structure with id {}", dto.getParent());
                    throw new DataNotFoundException("not found parent data with structure id " + dto.getParent());
                }
            }
            Structure _parentOp = parentOp.get();
			/*
			validate parent with level, if request level <
			*/
            Long parentLevel = _parentOp.getLevel();
            if (dto.getLevel().longValue() <= parentLevel.longValue()) {
                logger.info("level from request invalid, cause new level value {} < than from parent level {}", dto.getLevel(), parentLevel);
                throw new InvalidLevelException("invalid new level " + dto.getLevel());
            }

            _dto.setName(dto.getName());
            _dto.setDesc(dto.getDesc());
            Images _images = null;
            if (!dto.getImage().isEmpty()) {
                String location = pathCategory;
                logger.debug("folder location {}", location);
                logger.debug("image file name {}", dto.getImage().getOriginalFilename());

                Path path = Paths.get(location + dto.getImage().getOriginalFilename());

                logger.info("saving image");
                Images images = new Images();
                images.setCreatedBy(username);
                logger.debug("save file name to db {}", path.getFileName().toString());
                images.setImageName(path.getFileName().toString());
                logger.debug("save path file to db {}", path.toAbsolutePath().toString());
                images.setUri(path.toAbsolutePath().toString());
                _images = imageRepository.save(images);

                // save image to folder
                logger.info("saving image to share folder");
                _dto.setImage(path.toAbsolutePath().toString());
                FileUploadUtil.saveFile(location, dto.getImage());
            }

            Icons _icon = null;
            if (!dto.getIcon().isEmpty()) {
                String location = pathCategory;
                logger.debug("folder location {}", location);
                logger.debug("icon file name {}", dto.getIcon().getOriginalFilename());
                Path path = Paths.get(location + dto.getIcon().getOriginalFilename());

                logger.info("saving icon");
                Icons icons = new Icons();
                icons.setCreatedBy(username);
                logger.debug("save file name to db {}", path.getFileName().toString());
                icons.setIconName(path.getFileName().toString());
                logger.debug("save path file to db {}", path.toAbsolutePath().toString());
                icons.setUri(path.toAbsolutePath().toString());
                _icon = iconRepository.save(icons);

                // save image to folder
                logger.info("saving icon to share folder");
                _dto.setIcon(path.toAbsolutePath().toString());
                FileUploadUtil.saveFile(location, dto.getIcon());
            }

            logger.info("saving structure");
            Structure structure = new Structure();
            structure.setCreatedBy(username);
            structure.setStructureName(dto.getName());
            structure.setStructureDescription(dto.getDesc());
            structure.setLevel(dto.getLevel());
            structure.setSort(dto.getSort());
            structure.setEdit(dto.getEdit());
            structure.setUri(dto.getUri());
            structure.setParentStructure(dto.getParent());
            Structure _structure = structureRepository.save(structure);

            if (_images != null) {
                logger.info("saving structure image mapper");
                StructureImages sim = new StructureImages();
                sim.setCreatedBy(username);
                sim.setStructure(_structure);
                sim.setImages(_images);
                structureImageRepository.save(sim);
            }

            if (_icon != null) {
                logger.info("saving structure icon mapper");
                StructureIcons sic = new StructureIcons();
                sic.setCreatedBy(username);
                sic.setStructure(_structure);
                sic.setIcons(_icon);
                structureIconRepository.save(sic);
            }

            // get list parent of new structure
            Long parentId = _structure.getParentStructure();
            boolean parentStatus = Boolean.TRUE;
            do {
                Optional<Structure> parentStructure = structureRepository.findById(parentId);
                if (!parentStructure.isEmpty()) {
                    Structure _parent = parentStructure.get();
                    parentId = _parent.getParentStructure();
                    BreadcumbStructureDto bcDto = new BreadcumbStructureDto();
                    bcDto.setId(_parent.getId());
                    bcDto.setName(_parent.getStructureName());
                    bcDto.setLevel(_parent.getLevel());
                    _dto.getBreadcumbStructureDtoList().add(bcDto);
                    if (parentId == null)
                        parentStatus = Boolean.FALSE;
                    else if (parentId.longValue() == 0)
                        parentStatus = Boolean.FALSE;
                } else {
                    parentStatus = Boolean.FALSE;
                }
            } while (parentStatus);

            // sorting bread crumb
            Collections.sort(_dto.getBreadcumbStructureDtoList(), new Comparator<BreadcumbStructureDto>() {
                @Override
                public int compare(BreadcumbStructureDto o1, BreadcumbStructureDto o2) {
                    return o1.getLevel().intValue() - o2.getLevel().intValue();
                }
            });

            _dto.setId(_structure.getId());
            _dto.setEdit(_structure.getEdit());
            _dto.setParent(_structure.getParentStructure());
            _dto.setUri(_structure.getUri());
            return _dto;
        } catch (DataNotFoundException e) {
            logger.error("data not found", e);
            throw new DataNotFoundException("data not found", e);
        } catch (InvalidLevelException e) {
            logger.error("invalid level exception", e);
            throw new InvalidLevelException("invalid level exception", e);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * save multi structure
     *
     * @param username
     * @param dtoList
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public List<StructureResponseDto> saveBatchStructures(String username, List<StructureWithFileDto> dtoList) throws Exception {
        // looping save
        try {
            List<StructureResponseDto> newStructureList = new ArrayList<StructureResponseDto>();
            for (StructureWithFileDto structureDto : dtoList) {
                try {
                    logger.info("add category");
                    StructureResponseDto _dto = new StructureResponseDto();
                    logger.info("get structure id {} from database", structureDto.getId());
                    Optional<Structure> structureOp = structureRepository.findById(structureDto.getId());
                    logger.debug("structure result from db {}", structureOp);
                    if (structureOp.isEmpty()) {
                        logger.info("not found structure with id {}", structureDto.getId());
                        throw new DataNotFoundException("not found data with structure id " + structureDto.getId());
                    }
                    logger.debug("extract structure from optional");
                    Structure structure = structureOp.get();

                    _dto.setName(structureDto.getName());
                    _dto.setDesc(structureDto.getDesc());
                    Images _images = null;
                    if (!structureDto.getImage().isEmpty()) {
                        String location = pathCategory;
                        logger.debug("folder location {}", location);
                        logger.debug("image file name {}", structureDto.getImage().getOriginalFilename());

                        Path path = Paths.get(location + structureDto.getImage().getOriginalFilename());

                        logger.info("saving image");
                        Images images = new Images();
                        images.setCreatedBy(username);
                        logger.debug("save file name to db {}", path.getFileName().toString());
                        images.setImageName(path.getFileName().toString());
                        logger.debug("save path file to db {}", path.toAbsolutePath().toString());
                        images.setUri(path.toAbsolutePath().toString());
                        _images = imageRepository.save(images);

                        // save image to folder
                        logger.info("saving image to share folder");
                        FileUploadUtil.saveFile(location, structureDto.getImage());
                    }

                    Icons _icon = null;
                    if (!structureDto.getIcon().isEmpty()) {
                        String location = pathCategory;
                        logger.debug("folder location {}", location);
                        logger.debug("icon file name {}", structureDto.getIcon().getOriginalFilename());
                        Path path = Paths.get(location + structureDto.getIcon().getOriginalFilename());

                        logger.info("saving icon");
                        Icons icons = new Icons();
                        icons.setCreatedBy(username);
                        logger.debug("save file name to db {}", path.getFileName().toString());
                        icons.setIconName(path.getFileName().toString());
                        logger.debug("save path file to db {}", path.toAbsolutePath().toString());
                        icons.setUri(path.toAbsolutePath().toString());
                        _icon = iconRepository.save(icons);

                        // save image to folder
                        logger.info("saving icon to share folder");
                        FileUploadUtil.saveFile(location, structureDto.getIcon());
                    }

                    logger.info("saving structure");
                    if (structure == null) {
                        // save to new structure
                        structure.setCreatedBy(username);
                        structure.setStructureName(structureDto.getName());
                        structure.setStructureDescription(structureDto.getDesc());
                        structure.setLevel(structureDto.getLevel());
                        structure.setSort(structureDto.getSort());
                        structure.setEdit(structureDto.getEdit());
                        structure.setUri(structureDto.getUri());
                        structure.setParentStructure(structureDto.getParent());
                    } else {
                        // update new structure
                        structure.setModifyBy(username);
                        structure.setModifyDate(new Date());
                        structure.setStructureName(structureDto.getName());
                        structure.setStructureDescription(structureDto.getDesc());
                        structure.setLevel(structureDto.getLevel());
                        structure.setSort(structureDto.getSort());
                        structure.setEdit(structureDto.getEdit());
                        structure.setUri(structureDto.getUri());
                        structure.setParentStructure(structureDto.getParent());
                    }
                    Structure _structure = structureRepository.save(structure);

                    if (_images != null) {
                        logger.info("saving structure image mapper");
                        StructureImages sim = new StructureImages();
                        sim.setCreatedBy(username);
                        sim.setStructure(_structure);
                        sim.setImages(_images);
                        structureImageRepository.save(sim);
                    }

                    if (_icon != null) {
                        logger.info("saving structure icon mapper");
                        StructureIcons sic = new StructureIcons();
                        sic.setCreatedBy(username);
                        sic.setStructure(_structure);
                        sic.setIcons(_icon);
                        structureIconRepository.save(sic);
                    }
                    _dto.setId(_structure.getId());
                    newStructureList.add(_dto);
                } catch (Exception e) {

                }
            }
            return newStructureList;
        } catch (Exception e) {
            logger.error("exception", e);
            return new ArrayList<StructureResponseDto>();
        }
    }

    /**
     * update structure
     *
     * @param username
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public StructureResponseDto update(String username, StructureWithFileDto dto) throws Exception {
        try {
            StructureResponseDto _dto = new StructureResponseDto();

            logger.info("get structure by structure id {}", dto.getId());
			/*
			get existing structure from db with param structure id
			 */
            Optional<Structure> structureOp = structureRepository.findById(dto.getId());
            logger.debug("structure result from db {}", structureOp);
            if (structureOp.isEmpty()) {
                logger.info("not found structure with id {}", dto.getId());
                throw new DataNotFoundException("not found data with structure id " + dto.getId());
            }
            Structure structure = structureOp.get();

			/*
			validate parent with level, if request level <
			*/
            Long parentLevel = structure.getLevel();
            if (dto.getLevel().longValue() <= parentLevel.longValue()) {
                logger.info("level from request invalid, cause new level value {} < than from parent level {}", dto.getLevel(), parentLevel);
                throw new InvalidLevelException("invalid new level " + dto.getId());
            }

            _dto.setName(dto.getName());
            _dto.setDesc(dto.getDesc());
            Images _images = null;
            if (!dto.getImage().isEmpty()) {
                String location = pathCategory;
                logger.debug("folder location {}", location);
                logger.debug("image file name {}", dto.getImage().getOriginalFilename());

                Path path = Paths.get(location + dto.getImage().getOriginalFilename());

                logger.info("saving image");
                Images images = new Images();
                images.setCreatedBy(username);
                logger.debug("save file name to db {}", path.getFileName().toString());
                images.setImageName(path.getFileName().toString());
                logger.debug("save path file to db {}", path.toAbsolutePath().toString());
                images.setUri(path.toAbsolutePath().toString());
                _images = imageRepository.save(images);

                // save image to folder
                logger.info("saving image to share folder");
                _dto.setImage(path.toAbsolutePath().toString());
                FileUploadUtil.saveFile(location, dto.getImage());
            }

            Icons _icon = null;
            if (!dto.getIcon().isEmpty()) {
                String location = pathCategory;
                logger.debug("folder location {}", location);
                logger.debug("icon file name {}", dto.getIcon().getOriginalFilename());
                Path path = Paths.get(location + dto.getIcon().getOriginalFilename());

                logger.info("saving icon");
                Icons icons = new Icons();
                icons.setCreatedBy(username);
                logger.debug("save file name to db {}", path.getFileName().toString());
                icons.setIconName(path.getFileName().toString());
                logger.debug("save path file to db {}", path.toAbsolutePath().toString());
                icons.setUri(path.toAbsolutePath().toString());
                _icon = iconRepository.save(icons);

                // save image to folder
                logger.info("saving icon to share folder");
                _dto.setIcon(path.toAbsolutePath().toString());
                FileUploadUtil.saveFile(location, dto.getIcon());
            }

            logger.info("update structure");
            structure.setModifyBy(username);
            structure.setModifyDate(new Date());
            structure.setStructureDescription(dto.getDesc());
            structure.setLevel(dto.getLevel());
            structure.setSort(dto.getSort());
            structure.setEdit(dto.getEdit());
            structure.setUri(dto.getUri());

            // validate parent structure value from request if level > 1
            if (dto.getLevel().longValue() > 1) {
                if (structureRepository.existsById(dto.getParent())) {
                    structure.setParentStructure(dto.getParent());
                } else {
                    logger.info("parent id {} not found in database, update failed ", dto.getParent());
                    throw new DataNotFoundException("not found data with parent id " + dto.getParent());
                }
            }
            structure.setParentStructure(dto.getParent());
            Structure _structure = structureRepository.save(structure);

            if (_images != null) {
                logger.info("saving structure image mapper");
                StructureImages sim = structureImageRepository.findByStructureId(_structure.getId());
                if (sim != null) {
                    logger.info("data found from database, update structure images ");
                    // update structure image
                    sim.setModifyBy(username);
                    sim.setModifyDate(new Date());
                    sim.setStructure(_structure);
                    sim.setImages(_images);
                } else {
                    // save new image
                    logger.info("save structure images");
                    sim = new StructureImages();
                    sim.setCreatedBy(username);
                    sim.setStructure(_structure);
                    sim.setImages(_images);
                }
                structureImageRepository.save(sim);
            }

            if (_icon != null) {
                logger.info("saving structure icon mapper");
                StructureIcons sic = new StructureIcons();
                sic.setModifyBy(username);
                sic.setModifyDate(new Date());
                sic.setStructure(_structure);
                sic.setIcons(_icon);
                structureIconRepository.save(sic);
            }
            _dto.setId(_structure.getId());
            _dto.setEdit(_structure.getEdit());
            _dto.setParent(_structure.getParentStructure());
            _dto.setUri(_structure.getUri());
            return _dto;
        } catch (DataNotFoundException e) {
            logger.error("data not found", e);
            throw new DataNotFoundException("data not found", e);
        } catch (InvalidLevelException e) {
            logger.error("invalid new level", e);
            throw new InvalidLevelException("invalid new level", e);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("exception", e);
        }
    }

    /**
     * @param username
     * @param deleteStructureDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public DeleteStructureDto delete(String username, DeleteStructureDto deleteStructureDto) throws Exception {
        try {
            // get structure first from structure id
            Optional<Structure> deletedEntity = structureRepository.findById(deleteStructureDto.getStructureId());
            if (deletedEntity.isEmpty()) {
                logger.info("not found data");
                throw new DataNotFoundException("data not found");
            }
            Structure _deletedEntity = deletedEntity.get();
            // delete structure icon first
            StructureIcons sic = structureIconRepository.findByStructureId(deleteStructureDto.getStructureId());
            if (sic != null) {
                logger.info("remove structure icon with id {}", sic.getId());
                structureIconRepository.deleteById(sic.getId());
            }

            // delete structure icon first
            StructureImages sim = structureImageRepository.findByStructureId(deleteStructureDto.getStructureId());
            if (sim != null) {
                logger.info("remove structure image with id {}", sim.getId());
                structureImageRepository.deleteById(sim.getId());
            }

            // delete structure
            structureRepository.delete(_deletedEntity);

            // move child structures, to other structures
            for (ChangeToStructureDto changeTo : deleteStructureDto.getChangeTo()) {
                Optional<Structure> movedEntity = structureRepository.findById(changeTo.getStructureId());
                if (!movedEntity.isEmpty()) {
                    logger.info("move structure id {} to parent structure {}", changeTo.getStructureId(), changeTo.getChangeTo());
                    Structure _movedEntity = movedEntity.get();
                    _movedEntity.setModifyBy(username);
                    _movedEntity.setModifyDate(new Date());
                    _movedEntity.setParentStructure(changeTo.getChangeTo());
                    _movedEntity = structureRepository.save(_movedEntity);
                }
            }

            return deleteStructureDto;
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            throw new DataNotFoundException("data not found", e);
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception("failed delete structures", e);
        }
    }

    @Override
    @Transactional
    public List<MenuDto> getCategories() throws Exception {
        try {
            Iterable<Structure> findAllIterable = structureRepository.findAll();
            List<MenuDto> treeMenu = new TreeMenu().menuTree(mapToList(findAllIterable));
            return treeMenu;
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception();
        }
    }

    private List<MenuDto> mapToList(Iterable<Structure> iterable) {
        List<MenuDto> listOfMenus = new ArrayList<>();
        for (Structure structure : iterable) {
            MenuDto menuDto = new MenuDto();
            menuDto.setId(structure.getId());
            menuDto.setLevel(structure.getLevel());
            menuDto.setOrder(structure.getSort());
            menuDto.setParent(structure.getParentStructure());
            listOfMenus.add(menuDto);
        }
        return listOfMenus;
    }
}

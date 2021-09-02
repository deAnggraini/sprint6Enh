package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.client.ApiClient;
import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dao.*;
import id.co.bca.pakar.be.doc.dto.*;
import id.co.bca.pakar.be.doc.exception.*;
import id.co.bca.pakar.be.doc.model.*;
import id.co.bca.pakar.be.doc.service.StructureService;
import id.co.bca.pakar.be.doc.util.FileUploadUtil;
import id.co.bca.pakar.be.doc.util.TreeMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static id.co.bca.pakar.be.doc.common.Constant.MAX_STRUCTURE_LEVEL;

@Service
public class StructureServiceImp implements StructureService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.security.jwt.signingkey}")
    private String signingKey;
    @Value("${upload.path.menu}")
    private String pathMenu;
    @Value("${upload.path.category}")
    private String pathCategory;
    @Value("${upload.path.base}")
    private String basePath;
    @Autowired
    private ApiClient apiClient;

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
    @Transactional(rollbackFor = {Exception.class})
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
    @Transactional(rollbackFor = {DataNotFoundException.class,
            InvalidLevelException.class,
            InvalidSortException.class,
            Exception.class})
    public StructureResponseDto add(String username, StructureWithFileDto dto) throws Exception {
        try {
            logger.info("add category");
            StructureResponseDto _dto = new StructureResponseDto();

            // validate
            if (dto.getParent().intValue() == 0) {
                if (dto.getLevel().intValue() > 1) {
                    logger.info("level from request invalid, cause parent value 0 and level value must be setted to 1}");
                    throw new InvalidLevelException("invalid new level " + dto.getLevel() + "with parent value " + dto.getParent());
                }
            }

            /*
			get existing structure from db with param structure id
			 */
            if (dto.getParent() > 0) {
                Optional<Structure> parentOp = structureRepository.findById(dto.getParent());
                logger.debug("structure result from db {}", parentOp);
                if (parentOp.isEmpty()) {
                    if (dto.getLevel() > 1) {
                        logger.info("not found structure with id {}", dto.getParent());
                        throw new DataNotFoundException("not found parent data with structure id " + dto.getParent());
                    }
                }

                Structure _parentOp = parentOp.get();
                if (_parentOp.getDeleted().booleanValue()) {
                    logger.info("not active structure with id {}", dto.getParent());
                    throw new DataNotActiveException("data with id {} not active" + dto.getParent());
                }

                /*
                validate parent with level, if request level <
                */
                Long parentLevel = _parentOp.getLevel();
                if (dto.getLevel().longValue() <= parentLevel.longValue()) {
                    logger.info("level from request invalid, cause new level value {} < than from parent level {}", dto.getLevel(), parentLevel);
                    throw new InvalidLevelException("invalid new level " + dto.getLevel());
                }

                /*
                validate duplicate sorting value for same parent id
                */
                Boolean isExiststructure = structureRepository.existStructureByParentIdAndSort(dto.getParent(), dto.getSort());
                if (isExiststructure.booleanValue()) {
                    logger.info("sort value already exist, stop process {} for parent id {}", dto.getSort(), dto.getParent());
                    throw new InvalidSortException("sort value already exist " + dto.getSort());
                }
            }

            _dto.setName(dto.getName());
            _dto.setDesc(dto.getDesc());
            Images _images = null;
            if (dto.getImage() != null) {
                if (!dto.getImage().isEmpty()) {
                    String location = basePath + pathCategory;
                    logger.debug("folder location {}", location);
                    logger.debug("image file name {}", dto.getImage().getOriginalFilename());

                    Path path = Paths.get(location + dto.getImage().getOriginalFilename());

                    logger.info("saving image");
                    Images images = new Images();
                    images.setCreatedBy(username);
                    logger.debug("save file name to db {}", path.getFileName().toString());
                    images.setImageName(path.getFileName().toString());
                    logger.debug("save path file to db {}", path.toAbsolutePath().toString());

                    Path pathLocation = Paths.get(pathCategory + dto.getImage().getOriginalFilename());
                    images.setUri(pathLocation.toAbsolutePath().toString());
                    _dto.setImage(pathLocation.toAbsolutePath().toString());
                    _images = imageRepository.save(images);

                    // save image to folder
                    logger.info("saving image to share folder");
                    FileUploadUtil.saveFile(location, dto.getImage());
                }
            }

            Icons _icon = null;
            if (dto.getIcon() != null) {
                if (!dto.getIcon().isEmpty()) {
                    String location = basePath + pathCategory;
                    logger.debug("folder location {}", location);
                    logger.debug("icon file name {}", dto.getIcon().getOriginalFilename());
                    Path path = Paths.get(location + dto.getIcon().getOriginalFilename());

                    logger.info("saving icon");
                    Icons icons = new Icons();
                    icons.setCreatedBy(username);
                    logger.debug("save file name to db {}", path.getFileName().toString());
                    icons.setIconName(path.getFileName().toString());
                    logger.debug("save path file to db {}", path.toAbsolutePath().toString());
                    Path pathLocation = Paths.get(pathCategory + dto.getIcon().getOriginalFilename());
                    icons.setUri(pathLocation.toAbsolutePath().toString());
                    _dto.setIcon(pathLocation.toAbsolutePath().toString());
                    _icon = iconRepository.save(icons);

                    // save image to folder
                    logger.info("saving icon to share folder");
                    FileUploadUtil.saveFile(location, dto.getIcon());
                }
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
            structure.setLocation(dto.getLocation());
            structure.setLocation_text(dto.getLocation_text());
            Structure _structure = structureRepository.save(structure);

            // set uri value of struckture
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

            int i = 0;
            StringBuffer sbfBc = new StringBuffer();
            for (BreadcumbStructureDto dtoBc : _dto.getBreadcumbStructureDtoList()) {
                sbfBc.append(dtoBc.getName());
                if (i != _dto.getBreadcumbStructureDtoList().size() - 1)
                    sbfBc.append(" > ");
                i++;
            }

            _structure.setUri("/struktur/list/" + _structure.getId());
            _structure = structureRepository.save(_structure);

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
            _dto.setEdit(_structure.getEdit());
            _dto.setParent(_structure.getParentStructure());
            _dto.setUri(_structure.getUri());
            _dto.setLocation(_structure.getLocation());
            _dto.setLocation_text(_structure.getLocation_text());
            _dto.setSort(_structure.getSort());
            return _dto;
        } catch (DataNotFoundException e) {
            logger.error("data not found", e);
            throw new DataNotFoundException("data not found", e);
        } catch (InvalidLevelException e) {
            logger.error("invalid level exception", e);
            throw new InvalidLevelException("invalid level exception", e);
        } catch (InvalidSortException e) {
            logger.error("invalid sort exception", e);
            throw new InvalidSortException("invalid sort exception", e);
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
    @Transactional(rollbackFor = {Exception.class, InvalidLevelException.class, DataNotFoundException.class})
    public List<StructureResponseDto> saveBatchStructures(String username, List<StructureWithFileDto> dtoList) throws Exception {
        // looping save
        try {
            List<StructureResponseDto> newStructureList = new ArrayList<StructureResponseDto>();
            for (StructureWithFileDto structureDto : dtoList) {
                try {
                    logger.info("update category");
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

                    // validate data level
                    /*
                    if parent lavel == 0
                        if level not 1 then process failed
                     */
                    Long currentLevel = structureDto.getLevel();
                    Long currentPid = structureDto.getParent();
                    // validate structure exist in database base on current pid if current pid != 0
                    if (currentPid.intValue() > 0) {
                        boolean isExist = structureRepository.existsById(currentPid);
                        if (!isExist) {
                            logger.info("no structure found in database, process stopped");
                            throw new DataNotFoundException("no structure found in database");
                        }
                    }

                    if (currentPid.intValue() == 0) {
                        if (currentLevel.intValue() != 1) {
                            logger.info("invalid level, cause structure has parent value 0 but level != 1, process stopped");
                            throw new InvalidLevelException("invalid level, cause structure has parent value 0 but level != 1");
                        }
                    }

                    /**
                     * if current parent id != 0 then
                     *   get structure with parent id = compared structure id
                     *   if child
                     */
                    logger.info("structure will be validated {}", structureDto.toString());
                    for (StructureWithFileDto _structureDto : dtoList) {
                        logger.info("structure compared to {}", _structureDto.toString());
                        if (_structureDto.getId().intValue() == currentPid.intValue()) {
                            Long parentLevel = _structureDto.getLevel();
                            if (currentLevel.intValue() <= parentLevel.intValue()) {
                                logger.info("child level has value smaller than parent level, process stopped");
                                throw new InvalidLevelException("child level has value smaller than parent level");
                            }
                        }
                    }

                    // cek if has same sort value in one level for one
                    logger.info("saving structure");
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
                    structure.setLocation(structureDto.getLocation());
                    structure.setLocation_text(structureDto.getLocation_text());
                    structure.setHasArticle(structureDto.getHasArticle());
                    Structure _structure = structureRepository.save(structure);

                    // populate data
                    _dto.setName(structureDto.getName());
                    _dto.setDesc(structureDto.getDesc());
                    _dto.setId(structureDto.getId());
                    _dto.setLevel(structureDto.getLevel());
                    _dto.setParent(structureDto.getParent());
                    _dto.setLocation(_structure.getLocation());
                    _dto.setLocation_text(_structure.getLocation_text());
                    _dto.setSort(_structure.getSort());
                    _dto.setHasArticle(_structure.getHasArticle());
                    newStructureList.add(_dto);
                } catch (DataNotFoundException e) {
                    logger.error("there is data not found in database, stop process update");
                    throw new DataNotFoundException("there is data not found in database");
                } catch (InvalidLevelException e) {
                    logger.error("invalid setting level, stop process update");
                    throw new InvalidLevelException("invalid setting level");
                } catch (Exception e) {
                    logger.error("general exception appear");
                    throw new Exception(e);
                }
            }
            return newStructureList;
        } catch (DataNotFoundException e) {
            logger.error("there is data not found in database, stop process update");
            throw new DataNotFoundException("there is data not found in database");
        } catch (InvalidLevelException e) {
            logger.error("invalid setting level, stop process update");
            throw new InvalidLevelException("invalid setting level");
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception(e);
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
    @Transactional(rollbackFor = {DataNotFoundException.class,
            UndefinedStructureException.class,
            InvalidLevelException.class,
            InvalidSortException.class,
            Exception.class})
    public StructureResponseDto update(String username, StructureWithFileDto dto) throws Exception {
        try {
            StructureResponseDto _dto = new StructureResponseDto();

            logger.info("get current structure by structure id {}", dto.getId());
			/*
			get existing structure from db with param structure id
			 */
            Optional<Structure> structureOp = structureRepository.findById(dto.getId());
            logger.debug("structure result from db {}", structureOp);
            if (structureOp.isEmpty()) {
                logger.info("not found structure with id {}", dto.getId());
                throw new UndefinedStructureException("not found data with structure id " + dto.getId());
            }
            Structure structure = structureOp.get();
            Long existingLevel = structure.getLevel();
            /*
             * get current all child structures
             */
            List<Structure> childStructures = structureRepository.findChildsById(structure.getId());
            Long maxChildsLevel = structureRepository.totalChildsLevel(structure.getId());

            /*
            get destination structure
             */
            Long destinationlevel = 0L;
            if(dto.getParent().longValue() != 0 ) {
                Optional<Structure> destinationParentStructureOp = structureRepository.findById(dto.getParent());
                if (destinationParentStructureOp.isEmpty()) {
                    logger.info("undefined parent id {}", dto.getParent());
                    throw new UndefinedStructureException("undefined parent id " + dto.getParent());
                }
                Structure destinationParentStructure = destinationParentStructureOp.get();

                /*
                validate if request level smaller then destination parent level
                */
                destinationlevel = destinationParentStructure.getLevel();
                if (dto.getLevel().longValue() < destinationlevel.longValue()) {
                    logger.info("level from request invalid, cause new level value {} < than from parent level {}", dto.getLevel(), destinationlevel);
                    throw new InvalidLevelException("level from request invalid, cause new level value "+ dto.getLevel() + " < than from parent level "+ destinationlevel);
                }

                /*
                validate maximum level after add childs, if maximum reached then
                 */
                Long levelAfterAddChilds = destinationlevel.longValue() + maxChildsLevel.longValue();
                if(levelAfterAddChilds > MAX_STRUCTURE_LEVEL) {
                    logger.info("maximum level after add childs exceeded {}", levelAfterAddChilds);
                    throw new InvalidLevelException("maximum level after add childs exceeded " + levelAfterAddChilds);
                }

                // validate parent structure value from request if level > 1
                if (dto.getLevel().longValue() > 1) {
                    if (structureRepository.existsById(dto.getParent())) {
                        structure.setParentStructure(dto.getParent());
                    } else {
                        logger.info("parent id {} not found in database, update failed ", dto.getParent());
                        throw new DataNotFoundException("not found data with parent id " + dto.getParent());
                    }
                }
            }

            structure.setParentStructure(dto.getParent());

            _dto.setName(dto.getName());
            _dto.setDesc(dto.getDesc());
            Images _images = null;
            if (dto.getImage() != null) {
                if (!dto.getImage().isEmpty()) {
                    String location = basePath + pathCategory;
                    logger.debug("folder location {}", location);
                    logger.debug("image file name {}", dto.getImage().getOriginalFilename());

                    Path path = Paths.get(location + dto.getImage().getOriginalFilename());

                    logger.info("saving image");
                    Images images = new Images();
                    images.setCreatedBy(username);
                    logger.debug("save file name to db {}", path.getFileName().toString());
                    images.setImageName(path.getFileName().toString());
                    logger.debug("save path file to db {}", path.toAbsolutePath().toString());
                    Path pathLocation = Paths.get(pathCategory + dto.getImage().getOriginalFilename());
                    images.setUri(pathLocation.toAbsolutePath().toString());
                    _images = imageRepository.save(images);

                    // save image to folder
                    logger.info("saving image to share folder");
                    FileUploadUtil.saveFile(location, dto.getImage());

                    _dto.setImage(pathLocation.toAbsolutePath().toString());
                }
            }

            Icons _icon = null;
            if (dto.getIcon() != null) {
                if (!dto.getIcon().isEmpty()) {
                    String location = basePath + pathCategory;
                    logger.debug("folder location {}", location);
                    logger.debug("icon file name {}", dto.getIcon().getOriginalFilename());
                    Path path = Paths.get(location + dto.getIcon().getOriginalFilename());

                    logger.info("saving icon");
                    Icons icons = new Icons();
                    icons.setCreatedBy(username);
                    logger.debug("save file name to db {}", path.getFileName().toString());
                    icons.setIconName(path.getFileName().toString());
                    logger.debug("save path file to db {}", path.toAbsolutePath().toString());
                    Path pathLocation = Paths.get(pathCategory + dto.getIcon().getOriginalFilename());
                    icons.setUri(pathLocation.toAbsolutePath().toString());
                    _icon = iconRepository.save(icons);

                    // save image to folder
                    logger.info("saving icon to share folder");
                    FileUploadUtil.saveFile(location, dto.getIcon());

                    _dto.setIcon(pathLocation.toAbsolutePath().toString());
                }
            }

            logger.info("update structure");
            structure.setModifyBy(username);
            structure.setModifyDate(new Date());
            structure.setStructureDescription(dto.getDesc());
            structure.setLevel(dto.getLevel());
            structure.setSort(dto.getSort());
            structure.setEdit(dto.getEdit());
            structure.setUri(dto.getUri());
            structure.setStructureName(dto.getName());
            structure.setStructureDescription(dto.getDesc());
            structure.setLocation(dto.getLocation());
            structure.setLocation_text(dto.getLocation_text());
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
                StructureIcons sic = structureIconRepository.findByStructureId(_structure.getId());
                if (sic != null) {
                    logger.info("data found from database, update structure icon mapper");
                    sic.setModifyBy(username);
                    sic.setModifyDate(new Date());
                    sic.setStructure(_structure);
                    sic.setIcons(_icon);
                } else {
                    logger.info("saving structure icon mapper");
                    sic = new StructureIcons();
                    sic.setCreatedBy(username);
                    sic.setStructure(_structure);
                    sic.setIcons(_icon);
                }
                structureIconRepository.save(sic);
            }

            /*
            verify child structures
             */
            if(dto.getParent().longValue() != 0) {
                Long deltaLevel = destinationlevel.longValue() - existingLevel + 1;
                verifyAndUpdateLevelChildStructures(childStructures, deltaLevel, username);
            }

            _dto.setId(_structure.getId());
            _dto.setEdit(_structure.getEdit());
            _dto.setParent(_structure.getParentStructure());
            _dto.setUri(_structure.getUri());
            _dto.setLocation(_structure.getLocation());
            _dto.setLocation_text(_structure.getLocation_text());
            _dto.setSort(_structure.getSort());
            return _dto;
        } catch (DataNotFoundException e) {
            logger.error("data not found", e);
            throw new DataNotFoundException("data not found", e);
        } catch (UndefinedStructureException e) {
            logger.error("Undefined structure", e);
            throw new UndefinedStructureException("Undefined structure", e);
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
//                structureIconRepository.deleteById(sic.getId());
                // soft delete
                sic.setModifyBy(username);
                sic.setModifyDate(new Date());
                sic.setDeleted(Boolean.TRUE);
                structureIconRepository.save(sic);
            }

            // delete structure icon first
            StructureImages sim = structureImageRepository.findByStructureId(deleteStructureDto.getStructureId());
            if (sim != null) {
                logger.info("remove structure image with id {}", sim.getId());
//                structureImageRepository.deleteById(sim.getId());
                // soft delete
                sim.setModifyBy(username);
                sim.setModifyDate(new Date());
                sim.setDeleted(Boolean.TRUE);
                structureImageRepository.save(sim);
            }

            // delete structure
//            structureRepository.delete(_deletedEntity);
            _deletedEntity.setModifyDate(new Date());
            _deletedEntity.setModifyBy(username);
            _deletedEntity.setDeleted(Boolean.TRUE);
            structureRepository.save(_deletedEntity);

            // move child structures, to other structures
            logger.info("moved to new parent");
            for (ChangeToStructureDto changeTo : deleteStructureDto.getChangeTo()) {
                Long maxSort = structureRepository.maxSort(changeTo.getChangeTo());
                Optional<Structure> movedEntity = structureRepository.findById(changeTo.getStructureId());
                if (!movedEntity.isEmpty()) {
                    logger.info("move structure id {} to parent structure {}", changeTo.getStructureId(), changeTo.getChangeTo());
                    Structure _movedEntity = movedEntity.get();
                    _movedEntity.setSort(maxSort + 1);
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
    public List<MenuDto> getCategories(String username, String tokenValue) throws Exception {
        try {
            logger.info("get categories process");
            // get roles of user
            String role = apiClient.getRoles(username, tokenValue);
            logger.debug("roles from from username {} ---> {}", username, role);
            Iterable<Structure> findAllIterable = null;
            if (role.equals(Constant.Roles.ROLE_READER)) {
                findAllIterable = structureRepository.findAllForReader();
            } else {
                findAllIterable = structureRepository.findAll();
            }
            List<MenuDto> treeMenu = new TreeMenu().menuTree(mapToList(findAllIterable));
            return treeMenu;
        } catch (Exception e) {
            logger.error("exception", e);
            throw new Exception();
        }
    }

    /**
     * map of structure to menu dto
     *
     * @param iterable
     * @return
     */
    private List<MenuDto> mapToList(Iterable<Structure> iterable) {
        List<MenuDto> listOfMenus = new ArrayList<>();
        for (Structure structure : iterable) {
            MenuDto menuDto = new MenuDto();
            menuDto.setId(structure.getId());
            menuDto.setLevel(structure.getLevel());
            menuDto.setOrder(structure.getSort());
            menuDto.setParent(structure.getParentStructure());
            menuDto.setMenuName(structure.getStructureName());
            menuDto.setMenuDescription(structure.getStructureDescription());
            menuDto.setLocation(structureRepository.getLocationId(structure.getParentStructure()));
            menuDto.setLocation_text(structureRepository.getLocationText(structure.getParentStructure()));
//            menuDto.setLocation_text(structure.getLocation_text());
            menuDto.setHasArticle(structure.getHasArticle());
            try {
                StructureIcons sic = structureIconRepository.findByStructureId(structure.getId());
                menuDto.setIconUri(sic != null ? sic.getIcons().getUri() : "");
            } catch (Exception e) {

            }
            try {
                StructureImages sim = structureImageRepository.findByStructureId(structure.getId());
                menuDto.setImageUri(sim != null ? sim.getImages().getUri() : "");
            } catch (Exception e) {

            }
            menuDto.setBreadcumbMenuDtoList(getParent(structure));
            menuDto.setUri(structure.getUri());
            listOfMenus.add(menuDto);
        }
        return listOfMenus;
    }

    /**
     * @param _structure
     * @return
     */
    private List<BreadcumbMenuDto> getParentBc(Structure _structure) {
        // get list parent of new structure
        List<BreadcumbMenuDto> bcmDtoList = new ArrayList<>();
        Long parentId = _structure.getParentStructure();
        boolean parentStatus = Boolean.TRUE;
        logger.debug("generate breadcumb of parent id -------------------------->>>>>>>> {}", parentId);
        do {
            Optional<Structure> parentStructure = structureRepository.findById(parentId);
            if (!parentStructure.isEmpty()) {
                Structure _parent = parentStructure.get();
                parentId = _parent.getParentStructure();
                BreadcumbMenuDto bcDto = new BreadcumbMenuDto();
                bcDto.setId(_parent.getId());
                bcDto.setName(_parent.getStructureName());
                bcDto.setLevel(_parent.getLevel());
                bcmDtoList.add(bcDto);
                if (parentId == null)
                    parentStatus = Boolean.FALSE;
                else if (parentId.longValue() == 0)
                    parentStatus = Boolean.FALSE;
            } else {
                parentStatus = Boolean.FALSE;
            }
        } while (parentStatus);

        // sorting bread crumb
        Collections.sort(bcmDtoList, new Comparator<BreadcumbMenuDto>() {
            @Override
            public int compare(BreadcumbMenuDto o1, BreadcumbMenuDto o2) {
                logger.debug("compare leveling object 1 {} : object {}", o1.getLevel(), o2.getLevel());
                return o1.getLevel().intValue() - o2.getLevel().intValue();
            }
        });

        return bcmDtoList;
    }

    /**
     * @param _structure
     * @return
     */
    private List<BreadcumbMenuDto> getParent(Structure _structure) {
        // get list parent of structure
        List<BreadcumbMenuDto> bcmDtoList = new ArrayList<>();
        Long parentId = _structure.getParentStructure();
        List<Structure> structures = structureRepository.findBreadcumbById(parentId);
        structures.forEach(e -> {
            BreadcumbMenuDto bcDto = new BreadcumbMenuDto();
            bcDto.setId(e.getId());
            bcDto.setName(e.getStructureName());
            bcDto.setLevel(e.getLevel());
            bcmDtoList.add(bcDto);
        });
        return bcmDtoList;
    }

    /**
     *
     * @param childs
     * @param deltaLevel
     * @param loginName
     */
    private void verifyAndUpdateLevelChildStructures(List<Structure> childs, Long deltaLevel, String loginName) {
        childs.forEach(e->{
            Long newLevel = e.getLevel() + deltaLevel;
            e.setLevel(newLevel);
            e.setModifyBy(loginName);
            e.setModifyDate(new Date());
            structureRepository.save(e);
        });
    }
}

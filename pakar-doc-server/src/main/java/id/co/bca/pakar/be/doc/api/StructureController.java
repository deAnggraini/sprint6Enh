package id.co.bca.pakar.be.doc.api;

import id.co.bca.pakar.be.doc.api.validator.MultiStructureValidator;
import id.co.bca.pakar.be.doc.api.validator.StructureValidator;
import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.DeleteStructureDto;
import id.co.bca.pakar.be.doc.dto.MenuDto;
import id.co.bca.pakar.be.doc.dto.StructureResponseDto;
import id.co.bca.pakar.be.doc.dto.StructureWithFileDto;
import id.co.bca.pakar.be.doc.exception.DataNotFoundException;
import id.co.bca.pakar.be.doc.exception.InvalidLevelException;
import id.co.bca.pakar.be.doc.exception.InvalidSortException;
import id.co.bca.pakar.be.doc.exception.UndefinedStructureException;
import id.co.bca.pakar.be.doc.service.StructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
public class StructureController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(StructureController.class);

    @Autowired
    private StructureService structureService;

    @Autowired
    private StructureValidator structureValidator;

    @Autowired
    private MultiStructureValidator multiStructureValidator;

    private Locale DEFAULT_LOCALE = null;

    private Locale locale = DEFAULT_LOCALE;

    public Locale getLocale() {
        return locale;
    }

    /**
     * add method add structure menu
     * for mutilpart/form-data, validator must using custom validator
     *
     * @return
     */
    @PostMapping(value = "/api/doc/saveStructure", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<StructureResponseDto>> saveStructure(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @ModelAttribute StructureWithFileDto structure, BindingResult bindingResult) {
        try {
            logger.info("add structure process");
            logger.info("validate request input");
            structureValidator.validate(structure, bindingResult);
            if (bindingResult.hasErrors()) {
                logger.info("binding result " + bindingResult.getAllErrors());
                String errorMessage = "";
                for (Object object : bindingResult.getAllErrors()) {
                    if (object instanceof FieldError) {
                        FieldError fieldError = (FieldError) object;
                        errorMessage = messageSource.getMessage(fieldError.getCode(), null, getLocale());
                    }
                }
                return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], errorMessage);
            }
            logger.info("name structure >> {}", structure.getName());
            StructureResponseDto responseDto = structureService.add(username, structure);
            return createResponse(responseDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (InvalidLevelException e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("level.structure.invalid", null, getLocale()));
        } catch (InvalidSortException e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("sort.same.parent", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * add method update structure menu
     *
     * @return
     */
    @PostMapping(value = "/api/doc/updateStructure", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<StructureResponseDto>> updateStructure(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @ModelAttribute StructureWithFileDto structure, BindingResult bindingResult) {
        try {
            structureValidator.validate(structure, bindingResult);
            if (bindingResult.hasErrors()) {
                logger.info("binding result " + bindingResult.getAllErrors());
                String errorMessage = "";
                for (Object object : bindingResult.getAllErrors()) {
                    if (object instanceof FieldError) {
                        FieldError fieldError = (FieldError) object;
                        errorMessage = messageSource.getMessage(fieldError.getCode(), null, getLocale());
                    }
                }
                return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], errorMessage);
            }
            StructureResponseDto responseDto = structureService.update(username, structure);
            return createResponse(responseDto, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (UndefinedStructureException e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("undefined.structure", null, getLocale()));
        } catch (InvalidLevelException e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("level.structure.invalid", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * add method edit structure menu
     *
     * @return
     */
    @PostMapping(value = "/api/doc/deleteStructure", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<DeleteStructureDto>> deleteStructure(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @Valid @RequestBody DeleteStructureDto structures) {
        try {
            logger.info("deleting structure process");
            DeleteStructureDto response = structureService.delete(username, structures);
            return createResponse(response, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new DeleteStructureDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new DeleteStructureDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * saveing batch structures
     *
     * @param authorization
     * @param username
     * @param structures
     * @return
     */
    @PostMapping(value = "/api/doc/saveBatchStructure", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<List<StructureResponseDto>>> saveBatchStructure(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username, @RequestBody List<StructureWithFileDto> structures, BindingResult bindingResult) {
        try {
            multiStructureValidator.validate(structures, bindingResult);
            if (bindingResult.hasErrors()) {
                logger.info("binding result " + bindingResult.getAllErrors());
                String errorMessage = "";
                for (Object object : bindingResult.getAllErrors()) {
                    if (object instanceof FieldError) {
                        FieldError fieldError = (FieldError) object;
                        errorMessage = messageSource.getMessage(fieldError.getCode(), null, getLocale());
                    }
                }
                return createResponse(new ArrayList<StructureResponseDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], errorMessage);
            }
            List<StructureResponseDto> response = structureService.saveBatchStructures(username, structures);
            return createResponse(response, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<StructureResponseDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("data.not.found", null, getLocale()));
        } catch (InvalidLevelException e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<StructureResponseDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("level.structure.invalid", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<StructureResponseDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }

    /**
     * get all structure
     *
     * @return
     */
    @GetMapping("/api/doc/category")
    public ResponseEntity<RestResponse<List<MenuDto>>> getCategories(@RequestHeader("Authorization") String authorization, @RequestHeader(name = "X-USERNAME") String username) {
        logger.info("get all structure/category");
        try {
            List<MenuDto> menus = structureService.getCategories(username, getTokenFromHeader(authorization));
            return createResponse(menus, Constant.ApiResponseCode.OK.getAction()[0], messageSource.getMessage("success.response", null, getLocale()));
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<MenuDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], messageSource.getMessage("general.error", null, getLocale()));
        }
    }
}

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
import id.co.bca.pakar.be.doc.service.StructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
public class StructureController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(StructureController.class);

    @Autowired
    private StructureService structureService;

    @Autowired
    private StructureValidator structureValidator;

    @Autowired
    private MultiStructureValidator multiStructureValidator;

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
//                List<String> errors = bindingResult
//                        .getFieldErrors()
//                        .stream()
//                        .map(x -> x.getDefaultMessage())
//                        .collect(Collectors.toList());
//                return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], errors.get(0));

                String errorMessage = "";
                for (Object object : bindingResult.getAllErrors()) {
                    if(object instanceof FieldError) {
                        FieldError fieldError = (FieldError) object;
                        errorMessage = messageSource.getMessage(fieldError.getCode(),null, new Locale("el"));
                    }
                }
                return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], errorMessage);
            }
            logger.info("name structure >> {}", structure.getName());
            StructureResponseDto responseDto = structureService.add(username, structure);
            return createResponse(responseDto, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.DATA_NOT_FOUND.getAction()[0], Constant.ApiResponseCode.DATA_NOT_FOUND.getAction()[1]);
        } catch (InvalidLevelException e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.INVALID_STRUCTURE_LEVEL.getAction()[0], Constant.ApiResponseCode.INVALID_STRUCTURE_LEVEL.getAction()[1]);
        } catch (InvalidSortException e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.INVALID_SORT_STRUCTURE.getAction()[0], Constant.ApiResponseCode.INVALID_SORT_STRUCTURE.getAction()[1]);
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
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
                    if(object instanceof FieldError) {
                        FieldError fieldError = (FieldError) object;
                        errorMessage = messageSource.getMessage(fieldError.getCode(),null, new Locale("el"));
                    }
                }
                return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], errorMessage);
            }
            StructureResponseDto response = structureService.update(username, structure);
            return createResponse(response, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.DATA_NOT_FOUND.getAction()[0], Constant.ApiResponseCode.DATA_NOT_FOUND.getAction()[1]);
        } catch (InvalidLevelException e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.INVALID_STRUCTURE_LEVEL.getAction()[0], Constant.ApiResponseCode.INVALID_STRUCTURE_LEVEL.getAction()[1]);
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
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
            return createResponse(response, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new DeleteStructureDto(), Constant.ApiResponseCode.DATA_NOT_FOUND.getAction()[0], Constant.ApiResponseCode.DATA_NOT_FOUND.getAction()[1]);
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new DeleteStructureDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
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
//				return createResponse(new ArrayList<StructureResponseDto>(), Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[0], Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[1]);
                String errorMessage = "";
                for (Object object : bindingResult.getAllErrors()) {
                    if(object instanceof FieldError) {
                        FieldError fieldError = (FieldError) object;
                        errorMessage = messageSource.getMessage(fieldError.getCode(),null, new Locale("el"));
                    }
                }
                return createResponse(new ArrayList<StructureResponseDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], errorMessage);
            }
            List<StructureResponseDto> response = structureService.saveBatchStructures(username, structures);
            return createResponse(response, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
        } catch (DataNotFoundException e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<StructureResponseDto>(), Constant.ApiResponseCode.DATA_NOT_FOUND.getAction()[0], Constant.ApiResponseCode.DATA_NOT_FOUND.getAction()[1]);
        } catch (InvalidLevelException e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<StructureResponseDto>(), Constant.ApiResponseCode.INVALID_STRUCTURE_LEVEL.getAction()[0], Constant.ApiResponseCode.INVALID_STRUCTURE_LEVEL.getAction()[1]);
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<StructureResponseDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
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
            return createResponse(menus, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
        } catch (Exception e) {
            logger.error("exception", e);
            return createResponse(new ArrayList<MenuDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
        }
    }
}

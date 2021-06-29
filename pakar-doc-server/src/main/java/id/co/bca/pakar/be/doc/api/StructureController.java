package id.co.bca.pakar.be.doc.api;

import id.co.bca.pakar.be.doc.api.validator.StructureValidator;
import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.*;
import id.co.bca.pakar.be.doc.exception.DataNotFoundException;
import id.co.bca.pakar.be.doc.exception.InvalidLevelException;
import id.co.bca.pakar.be.doc.service.StructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StructureController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(StructureController.class);

	@Autowired
	private StructureService structureService;

	@Autowired
	private StructureValidator structureValidator;

	/**
	 * add method add structure menu
	 * for mutilpart/form-data, validator must using custom validator
	 * @return
	 */
	@PostMapping(value = "/api/doc/saveStructure", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<StructureResponseDto>> saveStructure(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, StructureWithFileDto structure, BindingResult bindingResult) {
		try {
			logger.info("add structure process");
			logger.info("validate request input");
			structureValidator.validate(structure, bindingResult);
			if (bindingResult.hasErrors()) {
				logger.info("binding result "+bindingResult.getAllErrors());
				return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[0], Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[1]);
			}
			logger.info("name structure >> {}", structure.getName());
			logger.info("size image "+structure.getImage().getSize());
			StructureResponseDto responseDto = structureService.add(username, structure);
			return createResponse(responseDto, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
		} catch (Exception e) {
			logger.error("exception", e);
			return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
		}
	}

	/**
	 * add method update structure menu
	 * @return
	 */
	@PostMapping(value = "/api/doc/updateStructure", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<StructureResponseDto>> updateStructure(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, StructureWithFileDto structure, BindingResult bindingResult) {
		try {
			structureValidator.validate(structure, bindingResult);
			if(bindingResult.hasErrors()) {
				logger.info("binding result "+bindingResult.getAllErrors());
				return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[0], Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[1]);
			}
			StructureResponseDto response = structureService.update(username, structure);
			return createResponse(response, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
		} catch (DataNotFoundException e) {
			logger.error("exception", e);
			return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.DATA_NOT_FOUND.getAction()[0], Constant.ApiResponseCode.DATA_NOT_FOUND.getAction()[1]);
		} catch (InvalidLevelException e) {
			logger.error("exception", e);
			return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.INVALID_STRUCUTURE_LEVEL.getAction()[0], Constant.ApiResponseCode.INVALID_STRUCUTURE_LEVEL.getAction()[1]);
		} catch (Exception e) {
			logger.error("exception", e);
			return createResponse(new StructureResponseDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
		}
	}

	/**
	 * add method edit structure menu
	 * @return
	 */
	@PostMapping(value = "/api/doc/deleteStructure", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<List<StructureDto>>> deleteStructure(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, MultiStructureDto structures, BindingResult bindingResult) {
		try {
			structureValidator.validate(structures, bindingResult);
			if(bindingResult.hasErrors()) {
				logger.info("binding result "+bindingResult.getAllErrors());
				return createResponse(new ArrayList<StructureDto>(), Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[0], Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[1]);
			}
			List<StructureDto> response = structureService.add(username, structures.getStructureWithFileDtoList());
			return createResponse(response, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
		} catch (Exception e) {
			logger.error("exception", e);
			return createResponse(new ArrayList<StructureDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
		}
	}

	@PostMapping(value = "/api/doc/saveBatchStructure", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RestResponse<List<StructureDto>>> saveBatchStructure(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, MultiStructureDto structures, BindingResult bindingResult) {
		try {
			structureValidator.validate(structures, bindingResult);
			if(bindingResult.hasErrors()) {
				logger.info("binding result "+bindingResult.getAllErrors());
				return createResponse(new ArrayList<StructureDto>(), Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[0], Constant.ApiResponseCode.REQUEST_PARAM_INVALID.getAction()[1]);
			}
			List<StructureDto> response = structureService.add(username, structures.getStructureWithFileDtoList());
			return createResponse(response, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
		} catch (Exception e) {
			logger.error("exception", e);
			return createResponse(new ArrayList<StructureDto>(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
		}
	}

	public static void main(String args[]) {
		List<MenuDto> menus = new ArrayList<MenuDto>();
		MenuDto menuLevel = new MenuDto();
		menuLevel.setId(1L);
		menuLevel.setMenuName("Home PAKAR");
		menuLevel.setLevel(1L);
		menuLevel.setOrder(1L);
		menuLevel.setParent(0L);
		menuLevel.setCode("1.1.0");
		menus.add(menuLevel);
		menuLevel = new MenuDto();
		menuLevel.setId(2L);
		menuLevel.setMenuName("Aktivitas Cabang");
		menuLevel.setLevel(1L);
		menuLevel.setOrder(3L);
		menuLevel.setParent(0L);
		menuLevel.setCode("1.3.0");
		menus.add(menuLevel);
		menuLevel = new MenuDto();
		menuLevel.setId(3L);
		menuLevel.setLevel(1L);
		menuLevel.setOrder(2L);
		menuLevel.setParent(0L);
		menuLevel.setCode("1.2.0");
		menuLevel.setMenuName("Produk Untuk Nasabah");		
		menus.add(menuLevel);
		menuLevel = new MenuDto();
		menuLevel.setId(7L);
		menuLevel.setMenuName("PRODUK DANA");
		menuLevel.setLevel(2L);
		menuLevel.setOrder(1L);
		menuLevel.setParent(7L);
		menuLevel.setCode("2.1.7");
		menus.add(menuLevel);
		menuLevel = new MenuDto();
		menuLevel.setId(9L);
		menuLevel.setLevel(2L);
		menuLevel.setOrder(2L);
		menuLevel.setParent(7L);
		menuLevel.setMenuName("Produk Kredit Produktif");
		menus.add(menuLevel);
		menuLevel = new MenuDto();
		menuLevel.setId(4L);
		menuLevel.setLevel(1L);
		menuLevel.setOrder(4L);
		menuLevel.setParent(0L);
		menuLevel.setMenuName("Aplikasi Mesin");
		menus.add(menuLevel);
		menuLevel = new MenuDto();
		menuLevel.setId(6L);
		menuLevel.setLevel(1L);
		menuLevel.setOrder(6L);
		menuLevel.setParent(0L);
		menuLevel.setMenuName("FAQ");
		menus.add(menuLevel);
		menuLevel = new MenuDto();
		menuLevel.setId(5L);
		menuLevel.setLevel(1L);
		menuLevel.setOrder(5L);
		menuLevel.setParent(0L);
		menuLevel.setMenuName("Pakar PDF");
		menus.add(menuLevel);
		
//		Comparator<MenuDto> comparator = Comparator.comparingLong(MenuDto::getLevel)
//                .thenComparingLong(MenuDto::getOrder);
//		menus.sort(comparator);
////		Collections.sort(menus, new MenuComparator<MenuDto>());
//		for (int i=0; i<menus.size(); i++)
//            System.out.println(menus.get(i));
//		
//		String json = JSONMapperAdapter.objectToJson(menus);
//		System.out.println(json);
		
		Comparator<MenuDto> compareByLevel = Comparator
                .comparingLong(MenuDto::getLevel)
                .thenComparingLong(MenuDto::getOrder)
                .thenComparingLong(MenuDto::getParent);

		List<MenuDto> sortedMenu = menus.stream()
		        .sorted(compareByLevel)
		        .collect(Collectors.toList());
		for (int i=0; i<sortedMenu.size(); i++)
          System.out.println(sortedMenu.get(i));
		
		System.out.println(sortedMenu);
	}
}

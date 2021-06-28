package id.co.bca.pakar.be.doc.api;

import id.co.bca.pakar.be.doc.common.Constant;
import id.co.bca.pakar.be.doc.dto.StructureDto;
import id.co.bca.pakar.be.doc.service.StructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StructureController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StructureService structureService;
	/**
	 * add method add structure menu
	 * @return
	 */
	@PostMapping("/api/doc/addStructure")
	public ResponseEntity<RestResponse<StructureDto>> addStructure(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, @RequestPart("icon") MultipartFile icon, @RequestPart("image") MultipartFile image, StructureDto structure) {
		try {
			logger.info("add structure process");
			logger.info("received token bearer --- " + authorization);
			StructureDto response = structureService.add(getOriginalToken(authorization), username, structure, image, icon);
			return createResponse(response, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
		} catch (Exception e) {
			logger.error("exception", e);
			return createResponse(new StructureDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
		}
	}

//	@PostMapping("/api/doc/addStructure/v2")
//	public ResponseEntity<RestResponse<StructureDto>> addStructureV2(@RequestHeader("Authorization") String authorization, @RequestHeader (name="X-USERNAME") String username, StructureDto structure) {
//		try {
//			logger.info("add structure process");
//			logger.info("received token bearer --- " + authorization);
//			StructureDto response = structureService.add(getOriginalToken(authorization), username, structure, structure.getImage(), structure.getIcon());
//			return createResponse(response, Constant.ApiResponseCode.OK.getAction()[0], Constant.ApiResponseCode.OK.getAction()[1]);
//		} catch (Exception e) {
//			logger.error("exception", e);
//			return createResponse(new StructureDto(), Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0], Constant.ApiResponseCode.GENERAL_ERROR.getAction()[1]);
//		}
//	}
}

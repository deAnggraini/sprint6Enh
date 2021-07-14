package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StructureService {
	StructureDto add(String username, StructureDto dto, MultipartFile image, MultipartFile icon) throws Exception;
	StructureResponseDto add(String username, StructureWithFileDto dto) throws Exception;
	StructureResponseDto update(String username, StructureWithFileDto dto) throws Exception;
	DeleteStructureDto delete(String username, DeleteStructureDto deleteStructureDto) throws Exception;
	List<StructureResponseDto> saveBatchStructures(String username, List<StructureWithFileDto> dtoList) throws Exception;
	List<MenuDto> getCategories(String username, String tokenValue) throws Exception;
}

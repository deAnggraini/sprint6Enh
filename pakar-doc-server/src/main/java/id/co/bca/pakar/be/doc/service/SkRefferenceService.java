package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.SkReffDto;

import java.util.List;

public interface SkRefferenceService {
	List<SkReffDto> findSkReffs(String keyword) throws Exception;
}

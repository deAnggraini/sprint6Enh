package id.co.bca.pakar.be.oauth2.service;

import id.co.bca.pakar.be.oauth2.dto.MenuDto;

import java.util.List;

public interface MenuService {
	List<MenuDto> getMenus(String token, String username) throws Exception;
}

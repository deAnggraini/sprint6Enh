//package id.co.bca.pakar.controller;
//
//import java.net.URI;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import id.co.bca.pakar.dto.RoleDto;
//import id.co.bca.pakar.manager.UserManager;
//
//@RestController
//public class UserProfileController {
//	@Autowired
//	private UserManager userManager;
//	
//	@PostMapping(
//			value = "/roles/{username}",
//			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
//			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//	public ResponseEntity findRoles(@PathVariable String username) {		
//		List<RoleDto> roles = userManager.findRoleByUsername(username);
//		return ResponseEntity
//				.created(URI
//						.create(String.format("/login/%s", "")))
//				.body(roles);
//	}
//}

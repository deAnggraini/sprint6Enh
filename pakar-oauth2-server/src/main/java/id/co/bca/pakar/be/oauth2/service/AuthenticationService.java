package id.co.bca.pakar.be.oauth2.service;

import id.co.bca.pakar.be.oauth2.dto.CredentialDto;
import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;

public interface AuthenticationService {
	LoggedinDto authenticate(CredentialDto dto) throws Exception;
}

package id.co.bca.pakar.be.oauth2.service;

import id.co.bca.pakar.be.oauth2.dto.CredentialDto;
import id.co.bca.pakar.be.oauth2.dto.OAuthTokenDto;

public interface AuthenticationService {
	OAuthTokenDto authenticate(CredentialDto dto);
}

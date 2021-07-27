package id.co.bca.pakar.be.oauth2.service;

import id.co.bca.pakar.be.oauth2.dto.CredentialDto;
import id.co.bca.pakar.be.oauth2.dto.EaiErrorSchemaLoginResponse;
import id.co.bca.pakar.be.oauth2.dto.LoggedinDto;
import id.co.bca.pakar.be.oauth2.dto.RefreshTokenResponseDto;

public interface AuthenticationService {
	LoggedinDto authenticate(CredentialDto dto) throws Exception;
	Boolean logout(String tokenValue) throws Exception;
	RefreshTokenResponseDto generateNewAccessToken(String refreshToken) throws Exception;
}

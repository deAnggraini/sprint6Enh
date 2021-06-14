package id.co.bca.pakar.be.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EaiLoginResponse {
	@JsonProperty("ErrorSchema")
	private EaiErrorSchemaLoginResponse errorSchema = new EaiErrorSchemaLoginResponse();
	@JsonProperty("OutputSchema")
	private EaiOutputSchemaLoginResponse outputSchema = new EaiOutputSchemaLoginResponse();
	public EaiErrorSchemaLoginResponse getErrorSchema() {
		return errorSchema;
	}
	public void setErrorSchema(EaiErrorSchemaLoginResponse errorSchema) {
		this.errorSchema = errorSchema;
	}
	public EaiOutputSchemaLoginResponse getOutputSchema() {
		return outputSchema;
	}
	public void setOutputSchema(EaiOutputSchemaLoginResponse outputSchema) {
		this.outputSchema = outputSchema;
	}
}

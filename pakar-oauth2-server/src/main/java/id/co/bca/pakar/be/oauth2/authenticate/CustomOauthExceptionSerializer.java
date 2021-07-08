package id.co.bca.pakar.be.oauth2.authenticate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import id.co.bca.pakar.be.oauth2.common.Constant;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {
    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        String code = Constant.ApiResponseCode.GENERAL_ERROR.getAction()[0];
        String message = value.getMessage();
        if (message.toLowerCase().contains(Constant.ApiResponseCode.ACCESS_TOKEN_EXPIRED.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.ACCESS_TOKEN_EXPIRED.getAction()[0];
            message = Constant.ApiResponseCode.ACCESS_TOKEN_EXPIRED.getAction()[1];
        } else if (message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_TOKEN.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.INVALID_TOKEN.getAction()[0];
            message = Constant.ApiResponseCode.INVALID_TOKEN.getAction()[1];
        } else if (message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_ACCESS_TOKEN.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.INVALID_ACCESS_TOKEN.getAction()[0];
            message = Constant.ApiResponseCode.INVALID_ACCESS_TOKEN.getAction()[1];
        } else if (message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_REFRESH_TOKEN.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.INVALID_REFRESH_TOKEN.getAction()[0];
            message = Constant.ApiResponseCode.INVALID_REFRESH_TOKEN.getAction()[1];
        } else if (message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_GRANT.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.INVALID_GRANT.getAction()[0];
            message = Constant.ApiResponseCode.INVALID_GRANT.getAction()[1];
        } else if (message.toLowerCase().contains(Constant.ApiResponseCode.REFRESH_TOKEN_EXPIRED.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.REFRESH_TOKEN_EXPIRED.getAction()[0];
            message = Constant.ApiResponseCode.REFRESH_TOKEN_EXPIRED.getAction()[1];
        } else if (message.toLowerCase().contains(Constant.ApiResponseCode.INVALID_CLIENT_ID.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.INVALID_CLIENT_ID.getAction()[0];
            message = Constant.ApiResponseCode.INVALID_CLIENT_ID.getAction()[1];
        } else if (message.toLowerCase().contains(Constant.ApiResponseCode.TOKEN_HAS_EXPIRED.getAction()[1].toLowerCase())) {
            code = Constant.ApiResponseCode.TOKEN_HAS_EXPIRED.getAction()[0];
            message = Constant.ApiResponseCode.TOKEN_HAS_EXPIRED.getAction()[1];
        }

        HashMap<String, String> apiStatus = new HashMap<>();
        apiStatus.put("code", code);
        apiStatus.put("message", message);
        jsonGenerator.writeObjectField("data", null);
        jsonGenerator.writeObjectField("status", apiStatus);
        if (value.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jsonGenerator.writeStringField(key, add);
            }
        }
        jsonGenerator.writeEndObject();
    }
}

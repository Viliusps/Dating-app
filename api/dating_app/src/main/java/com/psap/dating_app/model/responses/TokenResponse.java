package com.psap.dating_app.model.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private String access_token;
    private String token_type;
    private long expires_in;
}

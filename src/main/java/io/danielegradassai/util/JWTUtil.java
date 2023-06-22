package io.danielegradassai.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Component
public class JWTUtil {
    @Value("${security.issuer}")
    private String issuer;
    @Value("${security.duration}")
    private int duration;
    @Value("${security.key}")
    private String key;
    @Value("${security.prefix}")
    private String prefix;

    public String generate(String subject, Map<String, String> privateClaims) {

        DateTime currentDate = new DateTime();
        JWTCreator.Builder builder = JWT.create()
                .withIssuer(issuer)
                .withSubject(subject)
                .withIssuedAt(currentDate.toDate())
                .withExpiresAt(currentDate.plusMinutes(duration).toDate());
        for (String k : privateClaims.keySet()){
            builder.withClaim(k, privateClaims.get(k));
        }
        return builder.sign(Algorithm.HMAC256(key));
    }

    public DecodedJWT decode(String jwt) {
        return JWT.require(Algorithm.HMAC256(key)).withIssuer(issuer).build().verify(jwt);
    }

}

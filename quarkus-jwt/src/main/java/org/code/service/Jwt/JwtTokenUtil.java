package org.code.service.Jwt;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.*;
import org.code.model.User;
import org.code.model.UserBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class JwtTokenUtil implements  IJwtTokenUtil {
    public static final String  ID ="id";
    public static final String NAME ="name";
    public static final String ROLES ="roles";

    private static  SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final  Key key;
    private final String issuer;
    private final String secret;

    public JwtTokenUtil(@ConfigProperty(name="jwt-token-key") String key, @ConfigProperty(name="jwt-token-issuer") String issuer){
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(key);
        this.key = new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());
        this.issuer= issuer;
        this.secret = key;
    }

    public String generateToken(User user){
        LocalDateTime now = LocalDateTime.now();
        JwtBuilder builder = Jwts.builder()
                .claim(ID, user.getId())
                .claim(NAME, user.getName())
                .claim(ROLES, user.getRoles())
                .setIssuedAt( convertLocalDateTimeToDate(now))
                .signWith(signatureAlgorithm, key);
        builder.setExpiration(convertLocalDateTimeToDate(now.plusMinutes(30)));
        return builder.compact();
    }

    public User verifyToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(token).getBody();
        return  new UserBuilder()
                .setId(claims.get(ID).toString())
                .setRoles(claims.get(ROLES).toString())
                .setName(claims.get(NAME).toString()).createUser();
    }

    private Date convertLocalDateTimeToDate(LocalDateTime now){
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }
}

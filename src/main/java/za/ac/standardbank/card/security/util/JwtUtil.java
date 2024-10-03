package za.ac.standardbank.card.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtUtil {

    private static final String SECRET_KEY = "Pa55w0rd12345678"; // Use a strong secret key
    private static final String BEARER_PREFIX = "Bearer ";

    public static String generateToken(String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, generateSHA256Hash())
                .compact();
    }

    public static String generateSHA256Hash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(SECRET_KEY.getBytes());


            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing password: " + e.getMessage());
        }
    }

    public static Claims extractClaims(String token) {

        if(StringUtils.isBlank(token)){
            return null;
        }

        if (token.startsWith(BEARER_PREFIX)) {
            token = token.substring(BEARER_PREFIX.length());
        }

        return Jwts.parser()
                .setSigningKey(generateSHA256Hash())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public static boolean validateToken(String token) {
        boolean isValidClaim = extractClaims(token) != null;
        return (isValidClaim && !isTokenExpired(token));
    }

    public static boolean hasRole(String token, String role) {
        Claims claims = extractClaims(token);
        List<String> roles = claims.get("roles", List.class);
        return roles != null && roles.contains(role);
    }
}

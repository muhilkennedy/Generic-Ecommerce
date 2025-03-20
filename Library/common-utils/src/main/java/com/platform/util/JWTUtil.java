package com.platform.util;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.platform.security.config.JwtSecretConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	// 2hours validity
	public static final long JWT_TOKEN_VALIDITY = 2 * 60 * 60;
	// 1month validity incase of remember me action
	public static final long JWT_TOKEN_VALIDITY_REMEMBER_ME = 744 * 60 * 60;

	private static final String CLAIM_USER_TYPE = "UserType";
	private static final String IP_ADDRESS = "IPAddress";
	private static final String UNIQUE_NAME = "UniqueName";
	public static final String USER_TYPE_EMPLOYEE = "Employee";
	public static final String USER_TYPE_CUSTOMER = "Customer";
	
	private static JwtSecretConfiguration jwtSecret;
	
	@Autowired
	public void setJetSecret(JwtSecretConfiguration jwtSecret) {
		JWTUtil.jwtSecret = jwtSecret;
	}

	// retrieve email from jwt token
	public static String getUserIdFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public static String getUserUniqueNameFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		String uniqueName = (String) claims.get(UNIQUE_NAME);
		return uniqueName;
	}

	public static String getIpAddressFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		String uniqueName = (String) claims.get(IP_ADDRESS);
		return uniqueName;
	}

	public static boolean isEmployeeUser(String token) {
		Claims claims = getAllClaimsFromToken(token);
		String type = (String) claims.get(CLAIM_USER_TYPE);
		if (type.equalsIgnoreCase(USER_TYPE_EMPLOYEE)) {
			return true;
		}
		return false;
	}

	// retrieve expiration date from jwt token
	public static Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws ExpiredJwtException {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private static Claims getAllClaimsFromToken(String token) {
		JwtParser parser = Jwts.parser().setSigningKey(jwtSecret.getSecret()).build();
		return parser.parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private static Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * @param userDetails user object
	 * @return JWT token for users
	 */
	// implement later when user entity is created.
	public static String generateToken(String uniqueName, String rootId, String userType, String ipAddress,
			boolean rememberMe) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_USER_TYPE, userType);
		claims.put(IP_ADDRESS, ipAddress);
		claims.put(UNIQUE_NAME, uniqueName);
		// we can add more details about user in future in Claims map if needed.
		return doGenerateToken(claims, rootId, rememberMe);
	}
	
	public static String generateEmployeeToken(String uniqueName, String rootId, String ipAddress,
			boolean rememberMe) {
		return generateToken(uniqueName, rootId, USER_TYPE_EMPLOYEE, ipAddress, rememberMe);
	}
	
	public static String generateCustomerToken(String uniqueName, String rootId, String ipAddress,
			boolean rememberMe) {
		return generateToken(uniqueName, rootId, USER_TYPE_CUSTOMER, ipAddress, rememberMe);
	}
	
	/**
	 * @return token 
	 * NOTE: since we are not using oauth between apps, we need to
	 * make sure same jwt secret is used across services to communicate.
	 */
	public static String generateSystemUserToken() {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_USER_TYPE, USER_TYPE_EMPLOYEE);
		claims.put(UNIQUE_NAME, PlatformUtil.SYSTEM_USER_ROOTID);
		return doGenerateToken(claims, String.valueOf(PlatformUtil.SYSTEM_USER_ROOTID), false);
	}

	/**
	 * @param claims  map for additional data
	 * @param subject user email
	 * @return JWT
	 * @throws InvalidKeyException
	 */
	private static String doGenerateToken(Map<String, Object> claims, String subject, boolean rememberMe)
			throws InvalidKeyException {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()
						+ (rememberMe ? JWT_TOKEN_VALIDITY_REMEMBER_ME : JWT_TOKEN_VALIDITY) * 1000))
				.signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
	}

	private static Key getSigningKey() {
		byte[] keyBytes = Base64.getDecoder().decode(jwtSecret.getSecret());
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * @param token
	 * @return true if token is valid.
	 */
	public static Boolean validateToken(String token) throws ExpiredJwtException {
		String rootId = getUserIdFromToken(token);
		return (!(StringUtils.isEmpty(rootId)) && !(isTokenExpired(token)));
	}

	public static String extractToken(String token) {
		return token.replace("Bearer", "").trim();
	}

}

package za.ac.standardbank.card.resource;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import za.ac.standardbank.generated.LoginRequest;
import za.ac.standardbank.generated.LoginResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthResourceTest {

    private AuthResource authResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authResource = new AuthResource();
    }

    @Test
    void testLogin_User_Success() {
        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("password");

        Response response = authResource.login(request);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        LoginResponse loginResponse = (LoginResponse) response.getEntity();
        assertNotNull(loginResponse);

    }

    @Test
    void testLogin_Admin_Success() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("password");

        Response response = authResource.login(request);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        LoginResponse loginResponse = (LoginResponse) response.getEntity();
        assertNotNull(loginResponse);
    }

    @Test
    void testLogin_InvalidCredentials() {
        LoginRequest request = new LoginRequest();
        request.setUsername("invalidUser");
        request.setPassword("wrongPassword");
        Response response = authResource.login(request);
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}

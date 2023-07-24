import io.restassured.RestAssured;
import org.junit.Before;

public class BaseTest {

    protected static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";
    protected static final String COURIER_ENDPOINT = "/api/v1/courier";
    protected static final String COURIER_LOGIN_ENDPOINT = "/api/v1/courier/login";
    protected static final String ORDER_ENDPOINT = "/api/v1/orders";


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

}
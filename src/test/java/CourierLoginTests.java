
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTests extends BaseTest {
    CreateCourierRequest courierCreate = new CreateCourierRequest("parrot123", "1234", "Иннокентий");
    CreateCourierRequest courierLogin = new CreateCourierRequest("parrot123", "1234");
    CreateCourierRequest courierWithNoLogin = new CreateCourierRequest("", "1234");
    CreateCourierRequest courierWithNoPassword = new CreateCourierRequest("parrot123", "");
    CreateCourierRequest courierWithInvalidLogin = new CreateCourierRequest("NA123", "1234");
    CourierIdResponse courierIdResponse;
    BaseHttpClient baseHttpClient = new BaseHttpClient();


    @Test
    @DisplayName("успешная авторизация курьера с уникальным логином")
    public void testCourierLoginWithValidDataIsSuccessful() {
        //создать курьера
        baseHttpClient.postRequest(COURIER_ENDPOINT, courierCreate);
        //проверить логин курьера
        courierIdResponse = baseHttpClient.postRequest(COURIER_LOGIN_ENDPOINT, courierLogin)
                .assertThat().body("id", notNullValue()).and().statusCode(200)
                .extract().as(CourierIdResponse.class);
    }


    @Test
    @DisplayName("авторизация курьера без логина")
    public void testCourierLoginWithoutLoginReturnsError() {

        baseHttpClient.postRequest(COURIER_LOGIN_ENDPOINT, courierWithNoLogin)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(400);
    }

    @Test
    @DisplayName("авторизация курьера без пароля")
    public void testCourierLoginWithoutPasswordReturnsError() {

        baseHttpClient.postRequest(COURIER_LOGIN_ENDPOINT, courierWithNoPassword)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(400);
    }

    @Test
    @DisplayName("авторизация курьера с неверным логином")
    public void testCourierLoginWithInvalidLoginReturnsError() {
        baseHttpClient.postRequest(COURIER_LOGIN_ENDPOINT, courierWithInvalidLogin)
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and().statusCode(404);
    }

    @After
    public void deleteCourier() {
        if (courierIdResponse != null && courierIdResponse.getId() != null) {
            //удалить курьера
            baseHttpClient.doDeleteRequest(COURIER_ENDPOINT + "/" + courierIdResponse.getId());
            System.out.println("Courier ID "+ courierIdResponse.getId() + " was successfully deleted.");

        } else {
            System.out.println("Courier ID is empty or null. Skipping deletion.");
        }
    }
}
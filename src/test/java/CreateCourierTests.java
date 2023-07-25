
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;


public class CreateCourierTests extends BaseTest{

    BaseHttpClient baseHttpClient = new BaseHttpClient();
    CreateCourierRequest courierRequest = new CreateCourierRequest("parrot12345", "1234", "Иннокентий");
    CourierIdResponse courierIdResponse;

    @Test
    @DisplayName("создать курьера  с уникальным логином и паролем")
    public void testCreateUniqueCourierIsSuccessful() {
        ValidatableResponse response = baseHttpClient.postRequest(COURIER_ENDPOINT, courierRequest);
        response.assertThat().body("ok", equalTo(true)).and().statusCode(201);
        //получить id курьера через логин в системе
        courierIdResponse = baseHttpClient.postRequest(COURIER_LOGIN_ENDPOINT, courierRequest).extract().as(CourierIdResponse.class);
    }

    @Test
    @DisplayName("нельзя создать курьера с повторяющимся логином")
    public void testCreateDuplicateCourierReturnsError() {
        //создать курьера с уникальным логином и паролем
        baseHttpClient.postRequest(COURIER_ENDPOINT, courierRequest);
        //получить id курьера через логин в системе
        courierIdResponse = baseHttpClient.postRequest(COURIER_LOGIN_ENDPOINT, courierRequest).extract().as(CourierIdResponse.class);
        //повторно создать курьера с теми же данными
        ValidatableResponse response = baseHttpClient.postRequest(COURIER_ENDPOINT, courierRequest);
        response.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and().statusCode(409);
    }

    @Test
    @DisplayName("создать курьера без логина")
    public void testCreateCourierWithoutLoginReturnsError() {
        CreateCourierRequest courier = new CreateCourierRequest("", "1234", "Иннокентий");
        ValidatableResponse response = baseHttpClient.postRequest(COURIER_ENDPOINT, courier);
        response.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }

    @Test
    @DisplayName("создать курьера без пароля")
    public void testCreateCourierWithoutPasswordReturnsError(){
        CreateCourierRequest courier = new CreateCourierRequest("parrot123", "", "Иннокентий");
        ValidatableResponse response = baseHttpClient.postRequest(COURIER_ENDPOINT, courier);
        response.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }

    @After
    public void cleanUp() {
        if (courierIdResponse != null && courierIdResponse.getId() != null){
            //удалить курьера
            baseHttpClient.doDeleteRequest(COURIER_ENDPOINT + "/" + courierIdResponse.getId());
            System.out.println("Courier ID "+courierIdResponse.getId() +" was successfully deleted.");
        }else{
            System.out.println("Courier ID is empty or null. Skipping deletion.");
        }
    }
}
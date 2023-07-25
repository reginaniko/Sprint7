
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest {
    BaseHttpClient baseHttpClient = new BaseHttpClient();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;



    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, String deliveryDate, String comment, String[] color){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    @Parameterized.Parameters
    public static Object[][] getTestData() {
        Faker faker = new Faker();
        return new Object[][]{
                {"Попугай", "Кеша", "Строителей, 35", "4", "+7 800 355 35 35", 5, "2020-06-06", "Свободу попугаям!", new String[]{"BLACK"}},
                //other parameters are generated using Faker
                {faker.name().firstName(),
                        faker.name().lastName(),
                        faker.address().fullAddress(),
                        faker.number().digits(1),
                        faker.phoneNumber().phoneNumber(),
                        new Random().nextInt(10) + 1,
                        sdf.format(faker.date().future(3, TimeUnit.DAYS)),
                        faker.lebowski().quote(),
                        new String[]{"GREY"}},
                {faker.name().firstName(),
                        faker.name().lastName(),
                        faker.address().fullAddress(),
                        faker.number().digits(1),
                        faker.phoneNumber().phoneNumber(),
                        new Random().nextInt(10) + 1,
                        sdf.format(faker.date().future(3, TimeUnit.DAYS)),
                        faker.gameOfThrones().quote(),
                        new String[]{"BLACK", "GREY"}},
                {faker.name().firstName(),
                        faker.name().lastName(),
                        faker.address().fullAddress(),
                        faker.number().digits(1),
                        faker.phoneNumber().phoneNumber(),
                        new Random().nextInt(10) + 1,
                        sdf.format(faker.date().future(3, TimeUnit.DAYS)),
                        faker.lorem().sentence(), new String[]{""}}
        };
    }

    @Test
    @DisplayName("успешное создание заказа")
    public void testCreateOrderIsSuccessful(){
        CreateOrderRequest order = new CreateOrderRequest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        baseHttpClient.postRequest(ORDER_ENDPOINT, order).assertThat().body("track", notNullValue())
                .and().statusCode(201);
    }
}
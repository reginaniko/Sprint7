import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;


public class GetOrderListTest extends BaseTest{
    BaseHttpClient baseHttpClient = new BaseHttpClient();

    @Test
    @DisplayName("получить общший список всех заказов")
    public void testGetAllOrdersIsSuccessful(){
        OrderListRequest orderListRequest = new OrderListRequest();
        baseHttpClient.getRequest(ORDER_ENDPOINT, orderListRequest).assertThat().body("orders", notNullValue())
                .and().statusCode(200);
    }
}
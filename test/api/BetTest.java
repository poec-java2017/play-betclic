package api;

import com.google.gson.Gson;
import controllers.service.OperationTypeService;
import models.Operation;
import models.OperationType;
import models.User;
import org.junit.Test;
import play.Logger;
import play.mvc.Http;
import play.test.FunctionalTest;
import services.UserService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class BetTest extends FunctionalTest {
    @Test
    public void betNoParams(){
        // Given
        // When
        Http.Response response = POST("/api/bet");
        HashMap jsonResponse = new Gson().fromJson(response.out.toString(), HashMap.class);

        // Then
        assertStatus(401, response);
        assertTrue(jsonResponse.containsKey("code"));
        assertEquals("406", jsonResponse.get("code"));
        assertTrue(jsonResponse.containsKey("errors"));
        HashMap errors = new Gson().fromJson(jsonResponse.get("errors").toString(), HashMap.class);
        for (Object value : errors.values()) {
            assertEquals("Required", value);
        }
    }

    @Test
    public void betWorks(){
        // Given
        User user = User.findById(1L);
        Operation operation = new Operation();
        operation.amount = BigDecimal.valueOf(50);
        operation.user = user;
        operation.date = new Date();
        OperationType type = OperationTypeService.findOrCreate("operation.type.bet");
        operation.operationType = type;
        operation.save();

        Http.Response response = POST("/api/bet?userToken=1&amount20&idEvent=1&betChoice=1");
        Logger.info(response.status.toString());
        assertEquals("30.00", UserService.account(user.id).toString());


    }
}

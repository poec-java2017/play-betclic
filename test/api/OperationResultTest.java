package api;

import com.google.gson.Gson;
import models.User;
import models.api.OperationResult;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.mvc.Http;
import play.test.FunctionalTest;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;

public class OperationResultTest extends FunctionalTest{

    @Test
    public void refillNoParams() {
        // Given
        // No arguments

        // When
        Http.Response response = POST("/api/operation/refill");

        // Then
        assertStatus(406, response);
    }

    @Test
    public void refillNoAmount() {
        // Given
        User user = User.find("byEmail", "geromino@delaunay.com").first();

        // When
        Http.Response response = POST("/api/operation/refill?userToken=" + user.uniq + "&amount=");

        // Then
        assertStatus(406, response);
    }

    @Test
    public void refillUnknownUser() {
        // Given
        String userToken = "azertyuio";
        BigDecimal amount = new BigDecimal(500);

        // When
        Http.Response response = POST("/api/operation/refill?userToken=" + userToken + "&amount=" + amount);

        // Then
        assertStatus(401, response);
    }

    @Test
    public void refillNoToken() {
        // Given
        BigDecimal amount = new BigDecimal(500);

        // When
        Http.Response response = POST("/api/operation/refill?userToken=&amount=" + amount);

        // Then
        assertStatus(406, response);
    }

    @Test
    public void refillIsOk() {
        // Given
        User user = User.find("byEmail", "geromino@delaunay.com").first();
        BigDecimal amount = new BigDecimal(500);

        // When
        Http.Response response = POST("/api/operation/refill?userToken=" + user.uniq + "&amount=" + amount);
        Logger.info(response.out.toString());

        // Then
        // Type type = new TypeToken<ArrayList<Operation>>(){}.getType();
        OperationResult operation = new Gson().fromJson(response.out.toString(), OperationResult.class);
        assertStatus(201, response);
        assertNotNull(operation.uniq);
        assertThat(operation.amount.toString(), is("500"));

        /**
        Logger.info(operation.uniq);
        List<models.Operation> ops = models.Operation.findAll();
        for (models.Operation op : ops) {
            if (op == null) {
                Logger.info("op not found");
            }else {
                Logger.info("%s", op.uniq);
            }
        }
        Operation operationAfter = models.Operation.find("uniq = ?1", operation.uniq).first();
        assertNotNull(operationAfter);
        **/
    }
}

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Booking;
import entities.BookingDetails;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class Tests {
    CloseableHttpClient client;
    CloseableHttpResponse response;

    @BeforeMethod
    public void setUp(){
        client = HttpClientBuilder.create().build();
    }
    @AfterMethod
    public void closeResources() throws IOException {
        client.close();
        response.close();
    }

    @Test
    public void getBookingByIdTest() throws IOException {
        HttpGet request = new HttpGet("https://restful-booker.herokuapp.com/booking/1090");
        request.addHeader("Accept","*/*");
        response = client.execute(request);
        //String json1 = EntityUtils.toString(response.getEntity());
        //System.out.println(json1);

        System.out.println("Status kode responsa je " + response.getStatusLine().getStatusCode());
        //String json = EntityUtils.toString(response.getEntity());
       // JSONObject jsonObject = new JSONObject(json);
        //System.out.println("Ime u responsu je " + jsonObject.get("firstname"));
        //Assert.assertEquals(jsonObject.get("lastname"), "sunil");
        BookingDetails x;
        x = unmarshellGeneric(response, BookingDetails.class);
        System.out.println("Ime u responsu je " + x.getBookingdates().getCheckin());



    }

    @Test
    public void getAllBookings() throws IOException {
        HttpGet request = new HttpGet("https://restful-booker.herokuapp.com/booking/");
        request.addHeader("Accept","*/*");
        response = client.execute(request);

        System.out.println("Status kode responsa je " + response.getStatusLine().getStatusCode());
    }

    @Test
    public void getBookingByIdTest2() throws IOException {
        HttpGet request = new HttpGet("https://restful-booker.herokuapp.com/booking/4398");
        request.addHeader("Accept","*/*");
        response = client.execute(request);

        System.out.println("Status kode responsa je " + response.getStatusLine().getStatusCode());
    }

    private Object getValueFor (JSONObject jsonObject, String key){
        return jsonObject.get(key);
    }

    private Booking unmarshell(CloseableHttpResponse e, Class<Booking> bookingClass) throws IOException {
        String jsonBody = EntityUtils.toString(e.getEntity());
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonBody, bookingClass);
    }

    private <T> T unmarshellGeneric(CloseableHttpResponse e, Class<T> clazz) throws IOException {
        String jsonBody = EntityUtils.toString(e.getEntity());
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(jsonBody, clazz);
    }
}

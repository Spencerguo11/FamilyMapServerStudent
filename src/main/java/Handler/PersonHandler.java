package Handler;

import Request.LoginRequest;
import Result.PersonResult;
import Service.LoginService;
import Service.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Vector;

public class PersonHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        PersonService service = new PersonService();
        PersonResult result = new PersonResult();
        try {

            if (exchange.getRequestMethod().toLowerCase().equals("get")) {


                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {

                    String authToken = reqHeaders.getFirst("Authorization");

                    if (authToken.equals("") || !service.validateToken(authToken)) {
                        result.setSuccess(false);
                        result.setMessage("error : Invalid AuthToken");

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + "error : Invalid AuthToken" + "\"}");
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();
                    } else {
                        URI requestURI = exchange.getRequestURI();
                        String path = requestURI.getPath();
                        String[] parts = path.split("/");
                        Vector<String> params = new Vector<String>();
                        for (String part : parts) {
                            params.add(part);
                        }

                        // need to use an if else statement to figure if there is a personID
                        InputStream reqBody = exchange.getRequestBody();

                        String reqData = readString(reqBody);

                        System.out.println(reqData);

                        // TODO: Claim a route based on the request data
                        Gson gson = new Gson();

                        if(params.size() == 2){
                            // return all persons
                            result = service.getAllPersonInfo(authToken);
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            OutputStream resBody = exchange.getResponseBody();
                            String jsonString = gson.toJson(result);
                            writeString(jsonString, resBody);
                            resBody.close();

                        }

                        if(params.size() == 3){
                            // return a certain person
                            String personID = params.get(2);

                            result = service.getAPersonbyPersonID(personID, authToken);
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            OutputStream resBody = exchange.getResponseBody();
                            String jsonString = gson.toJson(result);
                            writeString(jsonString, resBody);
                            resBody.close();

                        }
                    }

                }
            }
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}

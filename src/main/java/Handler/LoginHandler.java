package Handler;

import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import dao.DataAccessException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoginHandler extends Handler {

    public void handle(HttpExchange exchange) throws IOException{

        try {
            // Determine the HTTP request type (GET, POST, etc.).
            // Only allow POST requests for this operation.
            // This operation requires a POST request, because the
            // client is "posting" information to the server for processing.
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {


                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println(reqData);

                // TODO: Claim a route based on the request data

                Gson gson = new Gson();
                LoginRequest request = (LoginRequest)gson.fromJson(reqData, LoginRequest.class);

                LoginService service = new LoginService();
                LoginResult result = service.login(request);

                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream resBody = exchange.getResponseBody();
                    String jsonString = gson.toJson(result);
                    writeString(jsonString, resBody);
                    resBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream resBody = exchange.getResponseBody();
                    String jsonString = "{\"message\" : \"" + result.getMessage() + "\"}";
                    writeString(jsonString, resBody);
                    exchange.getResponseBody().close();
                }

            }

        }
        catch (IOException | DataAccessException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            String jsonString = "{\"message\" : \"" + "\"Internal server error\"";

            OutputStream resBody = exchange.getResponseBody();
            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();

            writeString(jsonString, resBody);
            // Display/log the stack trace
            e.printStackTrace();
        }
    }



}

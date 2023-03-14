package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import request.LoginRequest;
import result.LoginResult;
import service.UserLoginService;

import java.io.*;
import java.net.*;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoginResult myLoginResult = new LoginResult();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                UserLoginService myLoginService = new UserLoginService();
                Reader reader = new InputStreamReader(exchange.getRequestBody());
                Gson gson = new Gson();
                LoginRequest myLoginRequest = gson.fromJson(reader, LoginRequest.class);
                myLoginResult = myLoginService.login(myLoginRequest);

                if (myLoginResult.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Gson gson2 = new Gson();
                    String jsonStr = gson2.toJson(myLoginResult);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    respBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String jsonStr = new String("{\"message\" : \"" + myLoginResult.getMessage() + "\"}");
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    exchange.getResponseBody().close();
                }
            }
        }
        catch (IOException e){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                String jsonStr = new String("{\"message\": \"Internal server error\"");
                OutputStream respBody = exchange.getResponseBody();
                writeString(jsonStr, respBody);
                respBody.close();

        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
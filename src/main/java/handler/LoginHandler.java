package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import dataaccess.DataAccessException;
import request.LoginRequest;
import result.LoadResult;
import result.LoginResult;
import service.UserLoginService;

import java.io.*;
import java.net.*;

public class LoginHandler extends RootHandler  {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoginResult loginResult = new LoginResult();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                UserLoginService myLoginService = new UserLoginService();
                Reader reader = new InputStreamReader(exchange.getRequestBody());
                Gson gson = new Gson();
                LoginRequest myLoginRequest = gson.fromJson(reader, LoginRequest.class);
                loginResult = myLoginService.login(myLoginRequest);

                checkSuccess(exchange, loginResult);
            }
        }
        catch (IOException e){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                String jsonStr = new String("{\"message\": \"Internal server error\"");
                OutputStream respBody = exchange.getResponseBody();
                writeString(jsonStr, respBody);
                respBody.close();

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkSuccess(HttpExchange exchange, LoginResult loginResult) throws IOException{
        if (loginResult.isSuccess()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            Gson gson2 = new Gson();
            String jsonStr = gson2.toJson(loginResult);
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String jsonStr = new String("{\"message\" : \"" + loginResult.getMessage() + "\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            exchange.getResponseBody().close();
        }
    }

}
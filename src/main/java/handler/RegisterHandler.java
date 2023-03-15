package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataaccess.DataAccessException;
import request.RegisterRequest;
import result.LoginResult;
import result.RegisterResult;
import service.UserRegisterService;


import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler extends RootHandler  {

    public RegisterHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        RegisterResult registerResult = new RegisterResult();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                UserRegisterService myRegisterService = new UserRegisterService();

                Reader reader = new InputStreamReader(exchange.getRequestBody());

                Gson gson = new Gson();
                RegisterRequest myRegisterRequest = gson.fromJson(reader, RegisterRequest.class);

                registerResult = myRegisterService.register(myRegisterRequest);

                checkSuccess(exchange, registerResult);
            }
        }
        catch (IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String jsonStr = new String("{\"message\": \"Internal server error\"");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();

            e.printStackTrace();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkSuccess(HttpExchange exchange, RegisterResult registerResult) throws IOException{
        if (registerResult.isSuccess()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            Gson gson2 = new Gson();
            String jsonString = gson2.toJson(registerResult);
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonString, respBody);
            respBody.close();
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String jsonStr = new String("{\"message\" : \"" + registerResult.getMessage() + "\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            exchange.getResponseBody().close();
        }
    }

}


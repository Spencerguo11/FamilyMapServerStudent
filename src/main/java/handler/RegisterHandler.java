package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.RegisterRequest;
import result.RegisterResult;
import service.UserRegisterService;


import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {

    public RegisterHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        RegisterResult myRegisterResult = new RegisterResult();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                UserRegisterService myRegisterService = new UserRegisterService();

                Reader reader = new InputStreamReader(exchange.getRequestBody());

                Gson gson = new Gson();
                RegisterRequest myRegisterRequest = gson.fromJson(reader, RegisterRequest.class);

                myRegisterResult = myRegisterService.register(myRegisterRequest);

                if (myRegisterResult.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Gson gson2 = new Gson();
                    String jsonString = gson2.toJson(myRegisterResult);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonString, respBody);
                    respBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String jsonStr = new String("{\"message\" : \"" + myRegisterResult.getMessage() + "\"}");
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

            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}


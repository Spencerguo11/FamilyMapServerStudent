package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dataaccess.DataAccessException;
import result.OnePersonResult;
import result.PersonResult;
import service.OnePersonService;
import service.PersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Arrays;

public class PersonHandler extends RootHandler {

    public PersonHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OnePersonResult myOnePersonResult = new OnePersonResult();
        PersonResult personResult = new PersonResult();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");

                    URI requestURI = exchange.getRequestURI();
                    String path = requestURI.getPath();
                    String[] parts = path.split("/");
                    String[] modifiedArray = Arrays.copyOfRange(parts, 1, parts.length);
                    parts = modifiedArray;

                    if(parts.length < 1 || parts.length > 2) {
                        myOnePersonResult.setSuccess(false);
                        myOnePersonResult.setMessage("Invalid number of arguments");

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + myOnePersonResult.getMessage() + "\"}");
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();
                    }

                    else if(parts.length == 2) {
                        OnePersonService myIDService = new OnePersonService();
                        myOnePersonResult = myIDService.getOnePerson(parts[1], authToken);

                        personResult.setSuccess(myOnePersonResult.isSuccess());
                        personResult.setMessage(myOnePersonResult.getMessage());

                        if(myOnePersonResult.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                            Gson gson = new Gson();
                            String jsonStr = gson.toJson(myOnePersonResult);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"internal server error\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    }

                    else if(parts.length == 1) {
                        PersonService myPersonService = new PersonService();
                        PersonResult result = myPersonService.person(authToken);

                        if(!result.isSuccess()) {
                            throw new DataAccessException(result.getMessage());
                        }

                        personResult.setSuccess(result.isSuccess());
                        personResult.setMessage(result.getMessage());
                        personResult.setData(result.getData());

                        checkSuccess(exchange, personResult);
                    }
                }
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String jsonStr =  new String("{\"message\": \"Internal server error\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();

            e.printStackTrace();
        } catch (DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String jsonStr = new String("{\"message\" : \"" + e.getMessage() + "\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();

            e.printStackTrace();
        }
    }

    private void checkSuccess(HttpExchange exchange, PersonResult personResult) throws IOException{
        if(personResult.isSuccess()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
            Gson gson = new Gson();
            String jsonStr = gson.toJson(personResult);
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String jsonStr = new String("{\"message\" : \"" + personResult.getMessage() + "\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();
        }
    }

}

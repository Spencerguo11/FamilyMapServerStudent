package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dataaccess.DataAccessException;
import result.OnePersonResult;
import result.PersonResult;
import service.PersonIDService;
import service.PersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PersonHandler extends RootHandler {

    public PersonHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OnePersonResult myOnePersonResult = new OnePersonResult();
        PersonResult myPersonResult = new PersonResult();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");

                    String requestedURL = exchange.getRequestURI().toString();
                    StringBuilder url = new StringBuilder(requestedURL);
                    url.deleteCharAt(0);

                    String[] arguments = url.toString().split("/");

                    if(arguments.length < 1 || arguments.length > 2) {
                        myOnePersonResult.setSuccess(false);
                        myOnePersonResult.setMessage("Invalid number of arguments");

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + myOnePersonResult.getMessage() + "\"}");
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();
                    }

                    else if(arguments.length == 2) {
                        PersonIDService myIDService = new PersonIDService();
                        myOnePersonResult = myIDService.personID(arguments[1], authToken);

                        myPersonResult.setSuccess(myOnePersonResult.isSuccess());
                        myPersonResult.setMessage(myOnePersonResult.getMessage());

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

                    else if(arguments.length == 1) {
                        PersonService myPersonService = new PersonService();
                        PersonResult out = myPersonService.person(authToken);

                        if(!out.isSuccess()) {
                            throw new DataAccessException(out.getMessage());
                        }

                        myPersonResult.setSuccess(out.isSuccess());
                        myPersonResult.setMessage(out.getMessage());
                        myPersonResult.setData(out.getData());

                        if(myPersonResult.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                            Gson gson = new Gson();
                            String jsonStr = gson.toJson(myPersonResult);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myPersonResult.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
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

}

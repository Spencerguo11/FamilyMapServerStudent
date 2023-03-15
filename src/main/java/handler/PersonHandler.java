package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataaccess.DataAccessException;
import dataaccess.Database;
import result.PersonIDResult;
import result.PersonResult;
import service.PersonIDService;
import service.PersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonHandler extends RootHandler {

    public PersonHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        PersonIDResult myPersonIDResult = new PersonIDResult();
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
                        myPersonIDResult.setSuccess(false);
                        myPersonIDResult.setMessage("Invalid number of arguments");

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + myPersonIDResult.getMessage() + "\"}");
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();
                    }

                    else if(arguments.length == 2) {
                        PersonIDService myIDService = new PersonIDService();
                        myPersonIDResult = myIDService.personID(arguments[1], authToken);

                        myPersonResult.setSuccess(myPersonIDResult.getSuccess());
                        myPersonResult.setMessage(myPersonIDResult.getMessage());

                        if(myPersonIDResult.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                            Gson gson = new Gson();
                            String jsonStr = gson.toJson(myPersonIDResult);
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

                        if(!out.getSuccess()) {
                            throw new DataAccessException(out.getMessage());
                        }

                        myPersonResult.setSuccess(out.getSuccess());
                        myPersonResult.setMessage(out.getMessage());
                        myPersonResult.setData(out.getData());

                        if(myPersonResult.getSuccess()) {
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

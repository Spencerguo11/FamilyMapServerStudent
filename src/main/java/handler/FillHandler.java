package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataaccess.DataAccessException;
import result.FillResult;
import service.FillService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Arrays;

public class FillHandler extends RootHandler {

    public FillHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        FillResult fillResult = new FillResult();
        int num = 0;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                FillService fillService = new FillService();

                URI requestURI = exchange.getRequestURI();
                String path = requestURI.getPath();
                String[] parts = path.split("/");
                String[] modifiedArray = Arrays.copyOfRange(parts, 1, parts.length);
                parts = modifiedArray;


                String userName = parts[1];
                fillResult.setSuccess(true);

                if(parts.length == 3) {
                    num = Integer.parseInt(parts[2]);
                    fillResult.setSuccess(true);
                }
                if(fillResult.isSuccess()) {
                    fillResult = fillService.fill(userName, num);
                }

                checkSuccess(exchange, fillResult);
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

    private void checkSuccess(HttpExchange exchange, FillResult fillResult) throws IOException {
        if (fillResult.isSuccess()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            String jsonStr = "{\"message\" :\"Successfully added " + fillResult.getPersons() +
                    " persons and " + fillResult.getEvents() + " events to the database.\"}";
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String jsonStr = new String("{\"message\" : \"" + fillResult.getMessage() + "\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            exchange.getResponseBody().close();
        }
    }

}
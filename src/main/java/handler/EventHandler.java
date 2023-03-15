package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dataaccess.DataAccessException;
import result.OneEventResult;
import result.EventResult;
import service.OneEventService;
import service.EventService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Arrays;

public class EventHandler extends RootHandler  {

    public EventHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OneEventResult myOneEventResult = new OneEventResult();
        EventResult myEventResult = new EventResult();
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

                    if(parts.length == 2) {
                        OneEventService myIDService = new OneEventService();
                        myOneEventResult = myIDService.eventID(parts[1], authToken);

                        if(myOneEventResult.isSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            Gson gson = new Gson();
                            String jsonStr = gson.toJson(myOneEventResult);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myOneEventResult.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    } else  {
                        EventService myEventService = new EventService();
                        EventResult eventResult = myEventService.event(authToken);
                        myEventResult.setData(eventResult.getData());
                        myEventResult.setSuccess(eventResult.isSuccess());
                        myEventResult.setMessage(eventResult.getMessage());

                        if(myEventResult.isSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            Gson gson = new Gson();
                            String jsonStr = gson.toJson(myEventResult);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myEventResult.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String jsonStr = new String("{\"message\" : \"Server ERROR\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();

            e.printStackTrace();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

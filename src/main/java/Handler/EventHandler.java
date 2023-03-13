package Handler;

import java.io.*;
import java.net.*;
import Result.EventResult;
import Service.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import com.sun.net.httpserver.HttpExchange;
import dao.DataAccessException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class EventHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        EventResult result = new EventResult();
        EventService service = new EventService();
        try {

            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {

                    String authToken = reqHeaders.getFirst("Authorization");

                    if (authToken.equals("") || !service.validateToken(authToken)) {
                        result.setSuccess(false);
                        result.setMessage("error: Invalid AuthToken");

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + result.getMessage() + "\"}");
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();
                    } else {
                        URI requestURI = exchange.getRequestURI();
                        String path = requestURI.getPath();
                        String[] parts = path.split("/");
                        String[] modifiedArray = Arrays.copyOfRange(parts, 1, parts.length);
                        parts = modifiedArray;
//                    Vector<String> params = new Vector<String>();
//                    for (String part : parts) {
//                        params.add(part);
//                    }

                        // need to use an if else statement to figure if there is a personID
                        InputStream reqBody = exchange.getRequestBody();

                        String reqData = readString(reqBody);

                        System.out.println("hi" + reqData);

                        // TODO: Claim a route based on the request data


                        if (parts.length > 2 || parts.length < 1) {
                            result.setSuccess(false);
                            result.setMessage("Invalid number of arguments");

                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String res = new String("{\"message\" : \"" + result.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(res, respBody);
                            respBody.close();
                        } else if (parts.length == 2) {
                            // return all events
                            Gson gson = new Gson();
                            // return a certain event
                            String eventID = parts[1];
                            result = service.getAEventbyEventID(eventID, authToken);

                            if(result.isSuccess()) {
                                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                                OutputStream resBody = exchange.getResponseBody();
                                String jsonString = gson.toJson(result);
                                writeString(jsonString, resBody);
                                resBody.close();
                            }
                            else{
                                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                                String jsonStr = new String("{\"message\" : \"" + result.getMessage() + "\"}");
                                OutputStream respBody = exchange.getResponseBody();
                                writeString(jsonStr, respBody);
                                respBody.close();
                            }
                        }
                        else if (parts.length == 1) {
                            result = service.getAllEventInfo(authToken);


                            if (result.isSuccess()) {
                                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                                Gson gson = new Gson();
                                String jsonStr = gson.toJson(result);
                                OutputStream respBody = exchange.getResponseBody();
                                writeString(jsonStr, respBody);
                                respBody.close();
                            } else {
                                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                                String jsonStr = new String("{\"message\" : \"" + result.getMessage() + "\"}");
                                OutputStream respBody = exchange.getResponseBody();
                                writeString(jsonStr, respBody);
                                respBody.close();
                            }

                        }
                    }
                }
            }
        }


        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }

    }
}

//package Handler;
//
//import java.io.*;
//import java.net.*;
//import Result.EventResult;
//import Service.EventService;
//import com.google.gson.Gson;
//import com.sun.net.httpserver.*;
//import com.sun.net.httpserver.HttpExchange;
//import dao.DataAccessException;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Vector;
//
//public class EventHandler extends Handler {
//    @Override
//    public void handle(HttpExchange exchange) throws IOException {
//        boolean success = false;
//        EventResult result = new EventResult();
//        EventService service = new EventService();
//        try {
//
//            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
//
//                Headers reqHeaders = exchange.getRequestHeaders();
//
//                if (reqHeaders.containsKey("Authorization")) {
//
//                    String authToken = reqHeaders.getFirst("Authorization");
//
//                    if (authToken.equals("") || !service.validateToken(authToken)) {
//                        result.setSuccess(false);
//                        result.setMessage("error: Invalid AuthToken");
//
//                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                        String jsonStr = new String("{\"message\" : \"" + result.getMessage() + "\"}");
//                        OutputStream respBody = exchange.getResponseBody();
//                        writeString(jsonStr, respBody);
//                        respBody.close();
//                    } else {
//                        URI requestURI = exchange.getRequestURI();
//                        String path = requestURI.getPath();
//                        String[] parts = path.split("/");
//                        String[] modifiedArray = Arrays.copyOfRange(parts, 1, parts.length);
//                        parts = modifiedArray;
////                    Vector<String> params = new Vector<String>();
////                    for (String part : parts) {
////                        params.add(part);
////                    }
//
//                        // need to use an if else statement to figure if there is a personID
//                        InputStream reqBody = exchange.getRequestBody();
//
//                        String reqData = readString(reqBody);
//
//                        System.out.println("hi" + reqData);
//
//                        // TODO: Claim a route based on the request data
//
//
//                        if (parts.length > 2 || parts.length < 1) {
//                            result.setSuccess(false);
//                            result.setMessage("Invalid number of arguments");
//
//                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                            String res = new String("{\"message\" : \"" + result.getMessage() + "\"}");
//                            OutputStream respBody = exchange.getResponseBody();
//                            writeString(res, respBody);
//                            respBody.close();
//                        } else if (parts.length == 2) {
//                            // return all events
//                            Gson gson = new Gson();
//                            // return a certain event
//                            String eventID = parts[1];
//                            result = service.getAEventbyEventID(eventID, authToken);
//
//                            if(result.isSuccess()) {
//                                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                                OutputStream resBody = exchange.getResponseBody();
//                                String jsonString = gson.toJson(result);
//                                writeString(jsonString, resBody);
//                                resBody.close();
//                            }
//                            else{
//                                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                                String jsonStr = new String("{\"message\" : \"" + result.getMessage() + "\"}");
//                                OutputStream respBody = exchange.getResponseBody();
//                                writeString(jsonStr, respBody);
//                                respBody.close();
//                            }
//                        }
//                        else if (parts.length == 1) {
//                            result = service.getAllEventInfo(authToken);
//
//
//                            if (result.isSuccess()) {
//                                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                                Gson gson = new Gson();
//                                String jsonStr = gson.toJson(result);
//                                OutputStream respBody = exchange.getResponseBody();
//                                writeString(jsonStr, respBody);
//                                respBody.close();
//                            } else {
//                                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                                String jsonStr = new String("{\"message\" : \"" + result.getMessage() + "\"}");
//                                OutputStream respBody = exchange.getResponseBody();
//                                writeString(jsonStr, respBody);
//                                respBody.close();
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//
//
//        catch (IOException | DataAccessException e) {
//            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
//            exchange.getResponseBody().close();
//            e.printStackTrace();
//        }
//
//    }
//}

package Handler;

import Result.EventIDResult;
import Result.EventResult;
import Service.EventIDService;
import Service.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {

    public EventHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        EventIDResult myEventIDResult = new EventIDResult();
        EventResult myEventResult = new EventResult();

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {

                    String authToken = reqHeaders.getFirst("Authorization");

                    String requestedURL = exchange.getRequestURI().toString();
                    StringBuilder url = new StringBuilder(requestedURL);
                    url.deleteCharAt(0);

                    String [] arguments = url.toString().split("/");

                    if(arguments.length > 2 || arguments.length <1){
                        myEventIDResult.setSuccess(false);
                        myEventIDResult.setMessage("Invalid number of arguments");
                        myEventResult.setSuccess(false);
                        myEventResult.setMessage("Bad Request");

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + myEventIDResult.getMessage() + "\"}");
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();
                    }

                    else if(arguments.length == 2) {
                        EventIDService myIDService = new EventIDService();
                        myEventIDResult = myIDService.eventID(arguments[1], authToken);

                        if(myEventIDResult.getSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            Gson gson = new Gson();
                            String jsonStr = gson.toJson(myEventIDResult);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myEventIDResult.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    }

                    else if(arguments.length == 1) {
                        EventService myEventService = new EventService();
                        EventResult out = myEventService.event(authToken);
                        myEventResult.setData(out.getData());
                        myEventResult.setSuccess(out.getSuccess());
                        myEventResult.setMessage(out.getMessage());

                        if(myEventResult.getSuccess()){
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
            String jsonStr = new String("{\"message\" : \"Internal server error\"}");
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
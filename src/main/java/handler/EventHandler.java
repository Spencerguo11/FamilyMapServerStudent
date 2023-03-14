package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import result.EventIDResult;
import result.EventResult;
import service.EventIDService;
import service.EventService;

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

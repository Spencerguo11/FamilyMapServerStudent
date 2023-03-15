package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import result.FillResult;
import service.FillService;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler extends RootHandler {

    public FillHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        FillResult myFillResult = new FillResult();

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                FillService myFillService = new FillService();
                String requestedURL = exchange.getRequestURI().toString();
                StringBuilder url = new StringBuilder(requestedURL);
                url.deleteCharAt(0);

                String[] arguments = url.toString().split("/");

                if (arguments.length <= 1 || arguments.length > 3 ) {
                    myFillResult.setSuccess(false);
                    myFillResult.setMessage("Invalid number of arguments");
                } else {
                    String userName = arguments[1];
                    int numGenerations = -1;
                    myFillResult.setSuccess(true);

                    if(arguments.length == 3) {
                        try {
                            numGenerations = Integer.parseInt(arguments[2]);
                            myFillResult.setSuccess(true);
                        }
                        catch (NumberFormatException n) {
                            myFillResult.setMessage("Invalid number of generations");
                            myFillResult.setSuccess(false);
                        }
                    }
                    if(myFillResult.getSuccess()) {
                        myFillResult = myFillService.fill(userName, numGenerations);
                    }
                }

                if (myFillResult.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonStr = "{\"message\" :\"Successfully added " + myFillResult.getPersons() +
                            " persons and " + myFillResult.getEvents() + " events to the database.\"}";
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    respBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String jsonStr = new String("{\"message\" : \"" + myFillResult.getMessage() + "\"}");
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

        }
    }

}
//package Handler;
//
//import Result.FillResult;
//import Service.FillService;
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//import dao.DataAccessException;
//
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URI;
//import java.util.Arrays;
//
//public class FillHandler extends Handler{
//
//    public FillHandler(){}
//
//    @Override
//    public void handle(HttpExchange exchange) throws IOException {
//        FillResult result = new FillResult();
//
//        boolean success = false;
//
//        try {
//            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
//
//                FillService service = new FillService();
//                URI requestURI = exchange.getRequestURI();
//                String path = requestURI.getPath();
//                String[] parts = path.split("/");
//                String[] modifiedArray = Arrays.copyOfRange(parts, 1, parts.length);
//                parts = modifiedArray;
//
//                if (parts.length <= 1 || parts.length > 3 ) {
//                    result.setSuccess(false);
//                    result.setMessage("Invalid number of arguments");
//                } else {
//                    String userName = parts[1];
//                    int numGenerations = -1;
//                    result.setSuccess(true);
//
//                    if(parts.length == 3) {
//                        try {
//                            numGenerations = Integer.parseInt(parts[2]);
//                            result.setSuccess(true);
//                        }
//                        catch (NumberFormatException n) {
//                            result.setMessage("Invalid number of generations");
//                            result.setSuccess(false);
//                        }
//                    }
//                    if(result.isSuccess()) {
//                        result = service.fill(userName, numGenerations);
//                    }
//                }
//
//                if (result.isSuccess()) {
//                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                    String jsonStr = "{\"message\" : \"Successfully added " + result.getNumPersons() +
//                    " persons and " + result.getNumEvents() + " events to the database.\"}";
//                    OutputStream respBody = exchange.getResponseBody();
//                    writeString(jsonStr, respBody);
//                    respBody.close();
//                } else {
//                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                    String jsonStr = new String("{\"message\" : \"" + result.getMessage() + "\"}");
//                    OutputStream respBody = exchange.getResponseBody();
//                    writeString(jsonStr, respBody);
//                    exchange.getResponseBody().close();
//                }
//            }
//        }
//        catch (IOException | DataAccessException e){
//            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
//            String jsonStr = new String("{\"message\": \"Internal server error\"");
//            OutputStream respBody = exchange.getResponseBody();
//            writeString(jsonStr, respBody);
//            respBody.close();
//
//        }
//    }
//
//}


package Handler;

import Result.FillResult;
import Service.FillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {

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

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
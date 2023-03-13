package Handler;

import Result.FillResult;
import Service.FillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Arrays;

public class FillHandler extends Handler{

    public FillHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        FillResult result = new FillResult();

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                FillService service = new FillService();
                URI requestURI = exchange.getRequestURI();
                String path = requestURI.getPath();
                String[] parts = path.split("/");
                String[] modifiedArray = Arrays.copyOfRange(parts, 1, parts.length);
                parts = modifiedArray;

                if (parts.length <= 1 || parts.length > 3 ) {
                    result.setSuccess(false);
                    result.setMessage("Invalid number of arguments");
                } else {
                    String userName = parts[1];
                    int numGenerations = -1;
                    result.setSuccess(true);

                    if(parts.length == 3) {
                        try {
                            numGenerations = Integer.parseInt(parts[2]);
                            result.setSuccess(true);
                        }
                        catch (NumberFormatException n) {
                            result.setMessage("Invalid number of generations");
                            result.setSuccess(false);
                        }
                    }
                    if(result.isSuccess()) {
                        result = service.fill(userName, numGenerations);
                    }
                }

                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonStr = "{\"message\" : \"Successfully added " + result.getNumPersons() +
                    " persons and " + result.getNumEvents() + " events to the database.\"}";
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    respBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String jsonStr = new String("{\"message\" : \"" + result.getMessage() + "\"}");
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    exchange.getResponseBody().close();
                }
            }
        }
        catch (IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String jsonStr = new String("{\"message\": \"Internal server error\"");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();

        }
    }

}

//package Handler;
//
//import Request.LoginRequest;
//import Result.PersonResult;
//import Service.LoginService;
//import Service.PersonService;
//import com.google.gson.Gson;
//import com.sun.net.httpserver.Headers;
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//import dao.DataAccessException;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URI;
//import java.util.Vector;
//
//public class PersonHandler extends Handler {
//    @Override
//    public void handle(HttpExchange exchange) throws IOException {
//        boolean success = false;
//        PersonService service = new PersonService();
//        PersonResult result = new PersonResult();
//        try {
//
//            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
//
//
//                Headers reqHeaders = exchange.getRequestHeaders();
//
//                if (reqHeaders.containsKey("Authorization")) {
//
//                    String authToken = reqHeaders.getFirst("Authorization");
//
//                    if (authToken.equals("") || !service(authToken)) {
//                        result.setSuccess(false);
//                        result.setMessage("error : Invalid AuthToken");
//
//                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                        String jsonStr = new String("{\"message\" : \"" + "error : Invalid AuthToken" + "\"}");
//                        OutputStream respBody = exchange.getResponseBody();
//                        writeString(jsonStr, respBody);
//                        respBody.close();
//                    } else {
//                        URI requestURI = exchange.getRequestURI();
//                        String path = requestURI.getPath();
//                        String[] parts = path.split("/");
//                        Vector<String> params = new Vector<String>();
//                        for (String part : parts) {
//                            params.add(part);
//                        }
//
//                        // need to use an if else statement to figure if there is a personID
//                        InputStream reqBody = exchange.getRequestBody();
//
//                        String reqData = readString(reqBody);
//
//                        System.out.println(reqData);
//
//                        // TODO: Claim a route based on the request data
//                        Gson gson = new Gson();
//
//                        if(params.size() == 2){
//                            // return all persons
//                            result = service.getAllPersonInfo(authToken);
//                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                            OutputStream resBody = exchange.getResponseBody();
//                            String jsonString = gson.toJson(result);
//                            writeString(jsonString, resBody);
//                            resBody.close();
//
//                        }
//
//                        if(params.size() == 3){
//                            // return a certain person
//                            String personID = params.get(2);
//
//                            result = service.getAPersonbyPersonID(personID, authToken);
//                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                            OutputStream resBody = exchange.getResponseBody();
//                            String jsonString = gson.toJson(result);
//                            writeString(jsonString, resBody);
//                            resBody.close();
//
//                        }
//                    }
//
//                }
//            }
//        }
//        catch (IOException | DataAccessException e) {
//            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
//            exchange.getResponseBody().close();
//            e.printStackTrace();
//        }
//    }
//}


package Handler;

import Result.PersonIDResult;
import Result.PersonResult;
import Service.PersonIDService;
import Service.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {

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

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
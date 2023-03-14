//package Handler;
//
//import Result.ClearResult;
//import Result.LoginResult;
//import Service.ClearService;
//import Service.LoginService;
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
//
//public class ClearHandler extends Handler {
//    @Override
//    public void handle(HttpExchange exchange) throws IOException {
//        boolean success = false;
//
//        try {
//
//            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
//
//
////                InputStream reqBody = exchange.getRequestBody();
////
////                String reqData = readString(reqBody);
////
////                System.out.println(reqData);
//
//                // TODO: Claim a route based on the request data
//
//                ClearService service = new ClearService();
//                ClearResult result = service.clear();
//
//                if (!result.isSuccess()) {
//
//                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//
//                    exchange.getResponseBody().close();
//                }
//                else {
//                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                }
//                Gson gson = new Gson();
////                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                OutputStream resBody = exchange.getResponseBody();
//                writeString(gson.toJson(result), resBody);
//                resBody.close();
//
//                success = true;
//
//            }
////            if (!success) {
////
////                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
////
////                exchange.getResponseBody().close();
////            }
//        }
//        catch (IOException e) {
//
//            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
//            exchange.getResponseBody().close();
//            e.printStackTrace();
//        }
//    }
//}


package Handler;

import Result.ClearResult;
import Service.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {

    public ClearHandler(){}

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                ClearService myClearService = new ClearService();

                ClearResult myClearResult = myClearService.clear();

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                Gson gson = new Gson();

                String jsonStr = gson.toJson(myClearResult);
                OutputStream respBody = exchange.getResponseBody();
                writeString(jsonStr, respBody);

                respBody.close();


                success = true;


                if(!success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
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
package com.arshiya.newsapp.common.restclient;

import com.arshiya.newsapp.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class RestClient {

  private static final String TAG = "RestClient";

  private Request request;

  public RestClient(Request request) {
    this.request = request;
  }


  public Response execute() throws IOException {
    String urlString = request.getUrl().toString();
    URL url = new URL(urlString);
    Logger.v(TAG ," execute(): Request url: " + url.toString());
    HttpURLConnection urlConnection;
    if (RestConstants.SCHEME_HTTPS.equals(request.getUrl().getScheme())) {
      urlConnection = (HttpsURLConnection) url.openConnection();
    } else {
      urlConnection = (HttpURLConnection) url.openConnection();
    }
    addHeaders(urlConnection, request.getHeadersMap());
    setContentType(urlConnection, request.getContentType());
    setRequestType(urlConnection, request.getRequestType());
    if (request.getRequestType() == RequestBuilder.RequestType.POST
        || request.getRequestType() == RequestBuilder.RequestType.PUT) {
      addBody(urlConnection, request.getRequestBody());
    }
    Response response = new Response();
    int responseCode = urlConnection.getResponseCode();
    Logger.v(TAG, " execute(): Response Code: " + responseCode);
    response.setResponseCode(responseCode);
    if (responseCode == 200) {
      String responseBody = convertStreamToString(urlConnection.getInputStream());
      Logger.v(TAG, " execute(): Response Body: " + responseBody);
      response.setResponseBody(responseBody);
    } else {
      String errorResponse = convertStreamToString(urlConnection.getErrorStream());
      response.setErrorMessage(errorResponse);
      Logger.e(TAG, " Response: API Failed: "
          + url
          + " response code :"
          + responseCode
          + "reason : "
          + errorResponse);
    }
    urlConnection.disconnect();
    return response;
  }

  private void setContentType(HttpURLConnection urlConnection, String contentType) {
    urlConnection.setRequestProperty(CONTENT_TYPE, contentType);
  }

  private void setRequestType(HttpURLConnection urlConnection,
      RequestBuilder.RequestType requestType) throws ProtocolException {
    urlConnection.setRequestMethod(requestType.toString());
  }

  private void addBody(HttpURLConnection urlConnection, String requestBody) throws IOException {
    urlConnection.setDoOutput(true);
    urlConnection.setRequestProperty(CHARACTER_SET, ENCODING_CHARSET_UTF8);
    urlConnection.setRequestProperty(CONTENT_TYPE, RestConstants.DEFAULT_CONTENT_TYPE);
    OutputStream output = urlConnection.getOutputStream();
    if (requestBody != null) {
      Logger.v(TAG, " addBody(): Request Body: " + requestBody);
      output.write(requestBody.getBytes(ENCODING_CHARSET_UTF8));
    }
    output.close();
  }

  private void addHeaders(HttpURLConnection urlConnection, Map<String, String> headers) {
    Set<Entry<String, String>> headerMap = headers.entrySet();
    for (Entry<String, String> header : headerMap) {
      Logger.v(TAG, " addHeaders(): " + header.getKey() + " : " + header.getValue());
      urlConnection.addRequestProperty(header.getKey(), header.getValue());
    }
  }

  private String convertStreamToString(InputStream inputStream) throws IOException {
    if (inputStream == null) return null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder stringBuilder = new StringBuilder();

    String line;
    try {
      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line);
      }
    } finally {
      inputStream.close();
    }
    return stringBuilder.toString();
  }

  private static final String ENCODING_CHARSET_UTF8 = "UTF-8";
  private static final String CHARACTER_SET = "Accept-Charset";
  private static final String CONTENT_TYPE = "Content-type";
}

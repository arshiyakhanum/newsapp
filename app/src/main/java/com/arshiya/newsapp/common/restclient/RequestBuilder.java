package com.arshiya.newsapp.common.restclient;

import android.net.Uri;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {

  private RequestType requestType;
  private Map<String, String> headersMap;
  private String bodyString;
  private String contentType;
  private Uri uri;


  /**
   * Supported Request types.
   */
  public enum RequestType {GET, POST, PUT, DELETE}

  /**
   * Builder to build a request to make an API call
   *
   * @param requestType One of the supported HTTP request types, one of {@link RequestType}
   */
  public RequestBuilder(Uri uri, RequestType requestType) {
    this.uri = uri;
    this.requestType = requestType;
    headersMap = new HashMap<>();
    contentType = RestConstants.DEFAULT_CONTENT_TYPE;
  }

  /**
   * Key value pair to be added as a header.
   *
   * @param headerKey key name for the header.
   * @param headerValue value for the header.
   * @return instance of {@link RequestBuilder}
   */
  public RequestBuilder addHeader(String headerKey, String headerValue) {
    headersMap.put(headerKey, headerValue);
    return this;
  }

  /**
   * Set of headers to be added to the headers.
   *
   * @param headersMap headers to be added to the request.
   * @return instance of {@link RequestBuilder}
   */
  public RequestBuilder addHeaders(Map<String, String> headersMap) {
    this.headersMap.putAll(headersMap);
    return this;
  }

  /**
   * Add body to the request.
   *
   * @param jsonBody instance of {@link JSONObject}
   * @return instance of {@link RequestBuilder}
   */
  public RequestBuilder addBody(JSONObject jsonBody) {
    this.bodyString = jsonBody.toString();
    return this;
  }

  public RequestBuilder addBody(String bodyString){
    this.bodyString = bodyString;
    return this;
  }

  /**
   * Set the content type of the request. Defaults to <i>application/json</i>
   *
   * @param contentType content type of the request.
   * @return instance of {@link RequestBuilder}
   */
  public RequestBuilder setContentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  public Request build() throws IllegalStateException, UnsupportedEncodingException {
    if (requestType == RequestType.GET && bodyString != null) {
      throw new IllegalStateException("GET request cannot have a body.");
    }
    return new Request(uri, requestType, headersMap,
        bodyString, contentType);
  }
}
package com.arshiya.newsapp.articlelist.common.restclient;

import android.net.Uri;

import java.util.Map;

public class Request {

  private RequestBuilder.RequestType requestType;
  private Map<String, String> headersMap;
  private String requestBody;
  private String contentType;
  private Uri uri;

  Request(Uri uri, RequestBuilder.RequestType requestType, Map<String, String> headersMap, String requestBody,
          String contentType){
    this.uri = uri;
    this.requestType = requestType;
    this.headersMap = headersMap;
    this.requestBody = requestBody;
    this.contentType = contentType;
  }

  public Uri getUrl() {
    return uri;
  }

  RequestBuilder.RequestType getRequestType() {
    return requestType;
  }

  Map<String, String> getHeadersMap() {
    return headersMap;
  }

  String getRequestBody() {
    return requestBody;
  }

  String getContentType() {
    return contentType;
  }

}
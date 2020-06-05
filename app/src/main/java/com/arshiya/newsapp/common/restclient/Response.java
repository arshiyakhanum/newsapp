package com.arshiya.newsapp.common.restclient;

import androidx.annotation.Nullable;

public class Response {

  private int responseCode;
  @Nullable private String responseBody;
  @Nullable private String errorMessage;

  public Response(int responseCode, @Nullable String responseBody) {
    this.responseCode = responseCode;
    this.responseBody = responseBody;
  }

  public Response() {

  }

  @Nullable
  public String getErrorMessage() {
    return errorMessage;
  }

  void setErrorMessage(@Nullable String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public int getResponseCode() {
    return responseCode;
  }

  void setResponseCode(int responseCode) {
    this.responseCode = responseCode;
  }

  @Nullable public String getResponseBody() {
    return responseBody;
  }

  void setResponseBody(@Nullable String responseBody) {
    this.responseBody = responseBody;
  }

  @Override public String toString() {
    return "Response{"
        + "responseCode="
        + responseCode
        + ", responseBody='"
        + responseBody
        + '\''
        + ", errorMessage='"
        + errorMessage
        + '\''
        + '}';
  }
}

package com.arshiya.newsapp.common.restclient.exceptions;

public class InvalidRequestException extends IllegalStateException {
  public InvalidRequestException(String exception){
    super(exception);
  }
}
package com.arshiya.newsapp.articlelist.common.restclient.exceptions;

public class InvalidRequestException extends IllegalStateException {
  public InvalidRequestException(String exception){
    super(exception);
  }
}
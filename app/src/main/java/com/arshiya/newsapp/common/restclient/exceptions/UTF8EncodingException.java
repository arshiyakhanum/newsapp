package com.arshiya.newsapp.common.restclient.exceptions;

import java.io.UnsupportedEncodingException;

public class UTF8EncodingException extends UnsupportedEncodingException {
  public UTF8EncodingException(String message){
    super(message);
  }
}
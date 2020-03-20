package com.arshiya.newsapp.articlelist.common.restclient.exceptions;

import java.io.UnsupportedEncodingException;

public class UTF8EncodingException extends UnsupportedEncodingException {
  public UTF8EncodingException(String message){
    super(message);
  }
}
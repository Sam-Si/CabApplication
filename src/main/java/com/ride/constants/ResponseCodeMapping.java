package com.ride.constants;

public enum ResponseCodeMapping {


    NOT_FOUND("1001","Status not found"),
    SUCCESS("2001","Success"),
    DETAILS_NOT_FOUND("4002","Driver details not found"),
    TOKEN_NOT_FOUND("4001","No token found in request headers"),
    ON_BOARDING_INCOMPLETE("1003","Please complete on-boarding process first"),
    SOMETHING_WENT_WRONG("5001","Something went wrong");

  private String code;
  private String message;

   ResponseCodeMapping(String errorCode, String errorMessage){
      this.code =errorCode;
      this.message = errorMessage;
  }

  public String getCode(){
       return code;
  }

  public  String getMessage(){
       return message;
  }
}

package com.librabry.Library.exception;

public class TxnException extends Exception{
    //Checked Exception so extending Exception
    public TxnException(String msg){
        super(msg);
    }
}

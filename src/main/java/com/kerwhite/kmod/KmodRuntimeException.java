package com.kerwhite.kmod;

public class KmodRuntimeException extends RuntimeException
{
    public KmodRuntimeException()
    {
        super();
    }
    public KmodRuntimeException(String message)
    {
        super(message);
    }
    public KmodRuntimeException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public KmodRuntimeException(Throwable cause)
    {
        super(cause);
    }
    protected KmodRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

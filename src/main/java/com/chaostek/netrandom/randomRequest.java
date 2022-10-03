package com.chaostek.netrandom;

/**
 * Class to hold request values and generated number
 * 
 * @author Chris Carson
 */
public class randomRequest
{
    private String requestString;
    private String resultString;

    randomRequest(String requestString, String resultString)
    {
        this.requestString = requestString;
        this.resultString = resultString;
        
    }
    public String getRequestString() { return requestString; }

    public void setRequestString(String requestString) { this.requestString = requestString; }

    public String getResultString() { return resultString; }

    public void setResultString(String resultString) { this.resultString = resultString; }
    
}

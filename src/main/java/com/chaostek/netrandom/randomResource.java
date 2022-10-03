package com.chaostek.netrandom;

import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Webservice to generate random numbers based on string input.
 * IT361 Computer Organization and Operating Systems
 * @author Chris Carson
 * @version 1.0, 09/16/22
 * 
 */
@Path("/random")
public class randomResource
{
    private static Random secRand = new Random();
    private ArrayDeque<Integer> values;
    private ArrayDeque<String> ops;
    
    private final HashMap<String, Integer> OPERATIONS = new HashMap()
    {
        {
            put("+", 1);
            put("-", 1);
            put("*", 2);
            put("/", 2);
        }
    };
    
    private static volatile ArrayList<randomRequest> RequestList = new ArrayList();
    
    public randomResource()
    {
        
    }
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getFromString(String input)
    {
        setupRandom();
        String result = Integer.toString(rollFormula(input));
        RequestList.add(new randomRequest(input, result));
        return result;
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String testRoll()
    {
        Gson gson = new Gson();
        return gson.toJson(RequestList);
        //return Integer.toString(d20());
        //return Integer.toString(rollDice(1, 10));
        
    }
    
    @DELETE
    public Response clearList()
    {
        RequestList.clear();
        return Response.ok().build();
        
    }
    
    private void setupRandom()
    {
        //secRand = new SecureRandom();
        //secRand.reseed();
        
        values = new ArrayDeque<>();
        ops = new ArrayDeque<>();
        
    }
    
    private int d20()
    {
        return secRand.nextInt(19)+1;
        
    }
    
    private int d12()
    {
        return secRand.nextInt(11)+1;
        
    }
    
    private int d10()
    {
        return secRand.nextInt(9)+1;
        
    }
    
    private int d8()
    {
        return secRand.nextInt(7)+1;
        
    }
    
    private int d6()
    {
        return secRand.nextInt(5)+1;
        
    }
    
    private int d4()
    {
        return secRand.nextInt(3)+1;
        
    }
    
    public int rollFormula(String rollString)
    {
        String token;
        String[] splitArray;
        
        rollString = rollString.toUpperCase();
        splitArray = rollString.split(" ");
        
        for (int i = 0; i < splitArray.length; i++)
        {
            token = splitArray[i];
            if (OPERATIONS.containsKey(token))
            {
                int peekPrec, tokenPrec;
                
                if (ops.isEmpty())
                {
                    ops.push(token);
                }
                else
                {
                    peekPrec = OPERATIONS.get(ops.peek());
                    tokenPrec = OPERATIONS.get(token);
                    
                    if (peekPrec < tokenPrec)
                    {
                        ops.push(token);
                    }
                    else if (peekPrec >= tokenPrec)
                    {
                        processOp();
                    }
                }
            }
            else if (token.matches("\\("))
            {
                ops.push(token);
                
            }
            else if (token.matches("\\)"))
            {
                String newOp;
                
                newOp = ops.peek();
                
                while (!newOp.matches("\\("))
                {

                    processOp();
                    newOp = ops.peek();
                    
                }
                ops.pop();
                
            }
            else if (token.contains("D"))
            {
                i++;
                values.push(rollDice(values.pop(), Integer.parseInt(splitArray[i])));
                
            }
            else
            {
                values.push(Integer.parseInt(token));
                
            }
        }
        
        while (!ops.isEmpty())
        {
            processOp();
            
        }
        
        return values.pop();
    }

    private int rollDice(int quantity, int die)
    {
        int total;

        //die = Integer.getInteger(dieSize);
        //die = Integer.getInteger(vals[1]);
        
        total = 0;

        switch(die)
        {
            case 4:
                for (int j = 1; j <= quantity; j++)
                { total = total + d4(); }
                break;

            case 6:
                for (int j = 1; j <= quantity; j++)
                { total = total + d6(); }
                break;

            case 8:
                for (int j = 1; j <= quantity; j++)
                { total = total + d8(); }
                break;

            case 10:
                for (int j = 1; j <= quantity; j++)
                { total = total + d10(); }
                break;

            case 12:
                for (int j = 1; j <= quantity; j++)
                { total = total + d12(); }
                break;

            case 20:
                for (int j = 1; j <= quantity; j++)
                { total = total + d20(); }
                break;
        }
        return total;
        
    }
    
    private void processOp()
    {
        int val1, val2;
        String newOp;
        
        newOp = ops.pop();
        val1 = values.pop();
        val2 = values.pop();
        // Do + - * /
        switch(newOp)
        {
            case "+":
                values.push(val1 + val2);
                break;
            case "-":
                values.push(val1 - val2);
                break;
            case "*":
                values.push(val1 * val2);
                break;
            case "/":
                values.push(val1 / val2);
                break;

        }
    }
}

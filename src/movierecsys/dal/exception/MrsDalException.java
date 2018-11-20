/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal.exception;

import java.io.IOException;

/**
 *
 * @author pgn
 */
public class MrsDalException extends Exception
{

    public MrsDalException()
    {
        super();
    }

    public MrsDalException(String message)
    {
        super(message);
    }

    public MrsDalException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MrsDalException(String message, Exception ex)
    {
        super(message, ex);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.be;

/**
 *
 * @author pgn
 */
public class Category
{

    private final int id;
    private final String name;

    /**
     * Creates an immutable category with a name and an id.
     *
     * @param id The
     * @param name
     */
    public Category(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Get the id of the category
     *
     * @return the value of id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Get the name of the category
     *
     * @return the value of name
     */
    public String getName()
    {
        return name;
    }

}

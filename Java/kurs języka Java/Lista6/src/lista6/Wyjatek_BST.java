/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista6;

/**
 *
 * @author stachu
 */
public class Wyjatek_BST extends Exception {

    /**
     * Creates a new instance of <code>Wyjatek_BST</code> without detail
     * message.
     */
    public Wyjatek_BST() 
    {
    }

    /**
     * Constructs an instance of <code>Wyjatek_BST</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public Wyjatek_BST(String msg) 
    {
        super(msg);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista6;

/**
 *
 * @author stachu
 * @param <T>
 * drzewo binarne przechowujace wartosci typu T
 */
public class BST< T extends Comparable<T>> implements Dict<T> 
{
    public BST(T val)
    {
        root = new Node(val);
    }
    private BST(Node<T> korzen) 
    {
        root = korzen;
    }
    private class Node<T extends Comparable<T> > implements Dict<T> 
    {    
        Node<T> left, right, parent;
        T data;

        public Node(T val) 
        {
            data = val;
        }
        public Node(T val, Node<T> p) 
        {
            data = val;
            parent = p;
        }
        @Override
        public boolean search(T val) 
        {
            if (val == null) throw new NullPointerException();
            if (this.data.equals(val)) return true;
            else 
            {
                if (this.left != null) 
                {
                    if (left.search(val))return true;
                }
                if (this.right != null) 
                {
                    if (right.search(val)) return true;
                }
            }
            return false;
        }
        
        
        @Override
        public void insert(T val) 
        {
            if(this.data.compareTo(val) == 0) return;
            if (this.data.compareTo(val) < 0) 
            {
                if (right == null) 
                {
                    Node<T> n = new Node(val, this);
                    right = n;
                } 
                else right.insert(val);
                
            } 
            else 
            {
                if (left == null) 
                {
                    Node<T> n = new Node(val, this);
                    left = n;
                } 
                else left.insert(val);
            }
        }
        @Override
        public void remove(T val) 
        {
            if(val.compareTo(data) == 0)
            {
                if(left == null)
                {
                    if(val.compareTo(parent.data) > 0) 
                    {
                        parent.right = this.right;
                        if (this.right != null) this.right.parent = parent;
                        return;
                    }
                    else
                    {
                        parent.left = this.right;
                        if (this.right != null) this.right.parent = parent;
                        return;
                    }
                }
                if(right == null)
                {
                    if(val.compareTo(parent.data) > 0) 
                    {
                        parent.right = this.left;
                        if (this.left != null)this.left.parent = parent;
                        return;
                    }
                    else
                    {
                        parent.left = this.left;
                        if (this.left != null) this.left.parent = parent;
                        return;
                    }
                }
                T w = left.max();
                left.remove(w);
                Node<T> n = new Node(w);
                n.right = this.right;
                if (this.right != null) this.right.parent = n;
                n.left = this.left;
                if (this.left != null)this.left.parent = n;
                n.parent = this.parent;
                if(w.compareTo(parent.data) > 0) parent.right = n;
                else parent.left = n; 
                return;
            }
            if(val.compareTo(data) > 0)
            {
                if( right != null ) right.remove(val); 
            }
            else
            {
                if( left != null ) left.remove(val);
            }
            
        }
        @Override
        public T max() 
        {
            if(right != null) return right.max();
            else return data;
        }
        @Override
        public T min() 
        {
            if( left != null ) return left.min();
            else return data;
        }
        @Override
        public String toString() 
        {
            String l,r;
            if( left == null ) l = "leaf";
            else l = left.toString();
            if( right == null ) r = "leaf";
            else r = right.toString();
            return "N( " + l + ", " + data.toString() + ", " + r + " )";
        }

        private int size() 
        {
            int res = 1;
            if(left != null) res += left.size();
            if(right != null) res += right.size();
            return res;
        }
    }
    private Node<T> root;
    @Override
    public boolean search(T val) 
    {
        if(root == null) throw new NullPointerException();
        return root.search(val);
    }
    @Override
    public void insert(T val) throws Wyjatek_BST
    {
        if(root == null) throw new NullPointerException();
        if(root.search(val)) throw new Wyjatek_BST("Taki element juz jest w drzewie");
        root.insert(val);
    }
    @Override
    public void remove(T val) throws Wyjatek_BST
    {
        if(root == null) throw new NullPointerException();
        if( !root.search(val) ) throw new Wyjatek_BST("Takiego elementu nie ma w drzewie");
        if( root.data.equals(val))
        {
            if(root.left == null) 
            {
                root = root.right;
                root.parent = null;
                return;
            }
            if(root.right == null)
            {
                root = root.left;
                root.parent = null;
                return;
            }
            T w = root.left.max();
            Node<T> n = new Node(w);
            root.left.remove(w);
            n.left = root.left;
            if(root.left != null) root.left.parent = n;
            n.right = root.right;
            root.right.parent = n;
            root = n;
            return;
        }
        root.remove(val);
    }
    @Override
    public T max() 
    {
        if(root == null) throw new NullPointerException();
        return root.max();  
    }
    @Override
    public T min() 
    {
        if(root == null) throw new NullPointerException();
        return root.min();
    }
    /**
     * metoda usuwa wszytskie elementy z drzewa
     */
    public void clear()
    {
        root = null;
    }
    /**
     * metoda zwraca liczbe elemntow w drzewie
     * @return 
     */
    public int size()
    {
        if(root == null) return 0;
        return root.size();
    }
    
    @Override
    public String toString()
    {
        if(root == null) return "drzewo jest puste";
        return root.data + "\n" + root.left.toString() + "\n" + root.right.toString();
    }
}

import java.util.LinkedList;

public class Fifo
{
    public LinkedList<Integer> fila;
    
    public Fifo()
    {
        fila = new LinkedList<Integer>();      
    }

    public void enfileira(int x)
    {
        fila.addLast(x);   
    }
    
    public Object desenfileira()
    {
        if(fila.isEmpty() != true)
          return fila.removeFirst();
        else
          return "A fila está vazia";      
    }
    
    public int pegaPrimeiro()
    {
          return ((int) fila.getFirst());
    }
    
    public boolean isEmpty()
    {
        return fila.isEmpty();   
    }
    
    public boolean contém(int x)
    {
        return (fila.contains(x));
    }
    
    public void clear()
    {
        fila.clear();
    }   
}

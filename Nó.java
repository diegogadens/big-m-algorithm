
public class Nó
{
    private int id_no;
    private int demanda;
    private int potencial;
    private boolean potencial_calculado = false;

    public Nó(int id_no, int demanda)
    {
        this.id_no = id_no;
        this.demanda = demanda;
        potencial = 0;
    }

    public int getId()
    {
        return id_no;
    }
    
    public int getDemanda()
    {
        return demanda;
    }
    
    public int getPotencial()
    {
        return potencial;
    }
    
    public void setPotencial(int x)
    {
        potencial = x;
    }
    
    public boolean potencialCalculado()
    {
        return potencial_calculado;
    }
    
    public void setPotencialCalculado(boolean x)
    {
        potencial_calculado = x;
    } 
}

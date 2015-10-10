public class Arco
{

    private int nó_origem, nó_destino, lim_sup, custo, custo_relativo, fluxo;
    private static final int lim_inf = 0; 
    private boolean básico, artificial, estáNoCiclo;
    private int sentido;
    private boolean visitado;
    
    public Arco(int nó_origem, int nó_destino, int lim_sup, int custo, boolean básico, boolean artificial )
    {
        this.nó_origem = nó_origem;
        this.nó_destino = nó_destino;
        this.lim_sup = lim_sup;
        this.custo = custo;
        custo_relativo = 0;
        fluxo = 0;
        this.básico = básico;
        this.artificial = artificial;
    }

    public int getOrigem()
    {
        return nó_origem;
    }
    
    public int getDestino()
    {
        return nó_destino;
    }
    
    public int getLimInf()
    {
        return lim_inf;
    }
    
    public int getLimSup()
    {
        return lim_sup;
    }
    
    public int getCusto()
    {
        return custo;
    }
    
    public int getCustoRelativo()
    {
        return custo_relativo;
    }
    
    public void setCustoRelativo(int x)
    {
        custo_relativo = x;
    }
    
    public int getFluxo()
    {
        return fluxo;
    }
    
    public void setFluxo(int x)
    {
        fluxo = x;
    }
    
    public boolean ehBásico()
    {
        if(básico == true)
            return true;
        else
            return false;
    }
    
    public void setBásico(boolean x)
    {
        básico = x;
    }
    
    public boolean ehArtificial()
    {
        if(artificial == true)
            return true;
        else
            return false;
    }
    
    public int getSentido()
    {
        return sentido;
    }
    
    public boolean estáNoCiclo()
    {
        return estáNoCiclo;
    }
    
    public void setEstáNoCiclo(boolean x)
    {
        estáNoCiclo = x;
    }
    
    public void setSentido(int x)
    {
        sentido = x;
    }
    
    public boolean visitado()
    {
        return visitado;
    }
    
    public void setVisitado(boolean x)
    {
        visitado = x;
    }
    
    
    public String toString()
    {
        return ("(" + nó_origem + "," + nó_destino + ") ");
    }
    
    public void imprimeArco()
    {
        System.out.println("\n("+nó_origem+","+nó_destino+") -> "+"["+lim_inf+","+lim_sup+","+custo+"]\nFluxo atual: "+fluxo);
    }
    
}

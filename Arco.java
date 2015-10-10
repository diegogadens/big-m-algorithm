public class Arco
{

    private int n�_origem, n�_destino, lim_sup, custo, custo_relativo, fluxo;
    private static final int lim_inf = 0; 
    private boolean b�sico, artificial, est�NoCiclo;
    private int sentido;
    private boolean visitado;
    
    public Arco(int n�_origem, int n�_destino, int lim_sup, int custo, boolean b�sico, boolean artificial )
    {
        this.n�_origem = n�_origem;
        this.n�_destino = n�_destino;
        this.lim_sup = lim_sup;
        this.custo = custo;
        custo_relativo = 0;
        fluxo = 0;
        this.b�sico = b�sico;
        this.artificial = artificial;
    }

    public int getOrigem()
    {
        return n�_origem;
    }
    
    public int getDestino()
    {
        return n�_destino;
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
    
    public boolean ehB�sico()
    {
        if(b�sico == true)
            return true;
        else
            return false;
    }
    
    public void setB�sico(boolean x)
    {
        b�sico = x;
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
    
    public boolean est�NoCiclo()
    {
        return est�NoCiclo;
    }
    
    public void setEst�NoCiclo(boolean x)
    {
        est�NoCiclo = x;
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
        return ("(" + n�_origem + "," + n�_destino + ") ");
    }
    
    public void imprimeArco()
    {
        System.out.println("\n("+n�_origem+","+n�_destino+") -> "+"["+lim_inf+","+lim_sup+","+custo+"]\nFluxo atual: "+fluxo);
    }
    
}

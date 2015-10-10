import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BigM
{
    private ArrayList<Nó> nós = new ArrayList<Nó>();
    private ArrayList<Arco> grafo_original = new ArrayList<Arco>();
    private ArrayList<Arco> ciclo = new ArrayList<Arco>();
    private ArrayList<Arco> saiDoCiclo = new ArrayList<Arco>();
    private Arco candidatoSair = new Arco(0,0,0,0,false,false);
    private Arco candidatoEntrar = new Arco(0,0,0,0,false,false);
    private int num_nós;
    private int num_arestas;
    private int nó_artificial;
    private Scanner entrada = new Scanner(System.in);
    public static final int MGRANDE = 10000;
    public static final int INFINITO = 10000;
    private Fifo fila = new Fifo();
    int delta = 10000;

       
    public BigM(int num_nós, int num_arestas)
    {
        this.num_nós = num_nós;
        this.num_arestas = num_arestas;
        nó_artificial = (num_nós + 1);
                
        /*nós.add(new Nó(1, 6));
        nós.add(new Nó(2, -3));
        nós.add(new Nó(3, -4));
        nós.add(new Nó(4, -2));
        nós.add(new Nó(5, 3));
        nós.add(new Nó(nó_artificial, 0));*/
        
          /*nós.add(new Nó(1, 1));
          nós.add(new Nó(2, 0));
          nós.add(new Nó(3, 2));
          nós.add(new Nó(4, -3));
          nós.add(new Nó(nó_artificial, 0));*/
          
                /*nós.add(new Nó(1, 8));
                nós.add(new Nó(2, 0));
                nós.add(new Nó(3, -4));
                nós.add(new Nó(4, 0));
                nós.add(new Nó(5, 0));
                nós.add(new Nó(6, 0));
                nós.add(new Nó(7, 4));
                nós.add(new Nó(8, -8));
                nós.add(new Nó(nó_artificial, 0));*/

              //nós.get(nó_artificial-1).setPotencialCalculado(true);
        
        /*grafo_original.add(new Arco(1, 2, 3, 5, false, false));
        grafo_original.add(new Arco(1, 3, 5, 3, false, false));
        grafo_original.add(new Arco(2, 3, 3, 1, false, false));
        grafo_original.add(new Arco(2, 4, 2, 0, false, false));
        grafo_original.add(new Arco(3, 5, 5, 0, false, false));
        grafo_original.add(new Arco(4, 5, 2, 1, false, false));
        grafo_original.add(new Arco(5, 2, 4, 1, false, false));*/
        
          /*grafo_original.add(new Arco(1, 2, 1, 5, false, false)); 
          grafo_original.add(new Arco(1, 4, 2, 3, false, false));
          grafo_original.add(new Arco(2, 4, 1, 2, false, false));
          grafo_original.add(new Arco(3, 1, 5, 5, false, false));
          grafo_original.add(new Arco(3, 2, 2, 2, false, false));
          grafo_original.add(new Arco(4, 3, 3, 5, false, false));*/
   
                /*grafo_original.add(new Arco(1, 2, 7, 5, false, false));
                grafo_original.add(new Arco(1, 4, 3, 3, false, false));
                grafo_original.add(new Arco(1, 3, 1, 0, false, false));
                grafo_original.add(new Arco(3, 2, 2, 2, false, false));
                grafo_original.add(new Arco(4, 3, 8, 5, false, false));
                grafo_original.add(new Arco(2, 6, 7, 7, false, false));
                grafo_original.add(new Arco(3, 6, 9, 7, false, false));
                grafo_original.add(new Arco(6, 4, 6, 6, false, false));
                grafo_original.add(new Arco(4, 7, 5, 4, false, false));
                grafo_original.add(new Arco(5, 8, 5, 5, false, false));
                grafo_original.add(new Arco(6, 5, 6, 5, false, false));
                grafo_original.add(new Arco(6, 8, 3, 0, false, false));
                grafo_original.add(new Arco(6, 7, 3, 3, false, false));
                grafo_original.add(new Arco(7, 8, 4, 3, false, false));
                grafo_original.add(new Arco(2, 5, 9, 0, false, false));*/
                
          
          
        lê_entrada();
        
        cria_arcos_articiais();
               
        int i = 1;
        while (contaArcosArtificiais()>1)
        {
           System.out.println("\n"+i+"ª ITERAÇÃO:");
            
            try 
            {    
                Thread.sleep(10); 
            }
            catch (Exception e) 
            {     
            }
            
            calculaPotenciais(nó_artificial);
            calculaCustosRelativos();
            candidatoAEntrar();
            encontraCiclo(candidatoEntrar);
            calculaBloqueio();
            redefineFluxos();
            resetaVariáveis();
            i++;
        }
        
        soluçãoFactível();
        
        //fase2();        
    }

    public void lê_entrada()
    {
        int id, demanda;  
        
        System.out.println("Insira os dados dos nós separados por um espaço");

        for(int i=0; i<num_nós; i++)
        {
            System.out.print("Insira o id do nó, seguido de sua demanda ");
            String linha_digitada = entrada.nextLine();    
        
            StringTokenizer tokenizer = new StringTokenizer(linha_digitada, " ");
              id = Integer.parseInt(tokenizer.nextToken());
              demanda = Integer.parseInt(tokenizer.nextToken());
            
            nós.add(new Nó(id, demanda));
        }
        nós.add(new Nó(nó_artificial, 0));
        nós.get(nó_artificial-1).setPotencialCalculado(true);
        
        for(int i=0; i<num_arestas; i++)
        {
            int nó_origem, nó_destino, lim_sup, custo, básico, artificial;
            System.out.print("Insira os dados: origem, destino, lim_sup, custo: ");
            String linha_digitada = entrada.nextLine();    
        
            StringTokenizer tokenizer = new StringTokenizer(linha_digitada, " ");
              nó_origem = Integer.parseInt(tokenizer.nextToken());
              nó_destino = Integer.parseInt(tokenizer.nextToken());
              lim_sup = Integer.parseInt(tokenizer.nextToken());
              custo = Integer.parseInt(tokenizer.nextToken());
              básico = 0;
              artificial = 0;
            
            grafo_original.add(new Arco(nó_origem, nó_destino, lim_sup, custo, ((básico==1)? true : false), ((artificial==1)? true : false)));
        }        
    }
    
    
    private void cria_arcos_articiais()
    {
        Arco aux;
       
            for(Nó x : nós)
            {
                if((x.getId() < nó_artificial) && (x.getDemanda()<0))
                {
                    aux = (new Arco(nó_artificial, x.getId(), INFINITO, MGRANDE , true, true));
                    aux.setFluxo(x.getDemanda() * -1);
                    grafo_original.add(aux);
                }
                else
                if((x.getId()<nó_artificial) && (x.getDemanda()>=0))
                {
                    aux = (new Arco(x.getId(), nó_artificial, INFINITO, MGRANDE , true, true));
                    aux.setFluxo(x.getDemanda());
                    grafo_original.add(aux);
                }
            }
            
            for(Arco x : grafo_original)
              x.imprimeArco();
    }
    
    
    private void calculaPotenciais(int nó_raiz)
    {
        int raiz = nó_raiz;
        Nó i, j;
        int wi, wj;
        fila.enfileira(raiz);
        do
        {
              for(Arco x : grafo_original)
              {
                 i = nós.get(x.getOrigem()-1);
                 j = nós.get(x.getDestino()-1);
                 wi = (0 + i.getPotencial());
                 wj = (0 + j.getPotencial());
                 
                 if ((x.ehBásico()) && (x.getDestino() == fila.pegaPrimeiro()) && (i.potencialCalculado()==false))
                 {
                    i.setPotencial( wj + x.getCusto());
                    i.setPotencialCalculado(true);
                    fila.enfileira(i.getId());
                 }
                 else
                 if ((x.ehBásico()) && (x.getOrigem() == fila.pegaPrimeiro()) && (j.potencialCalculado()==false))
                 {
                    j.setPotencialCalculado(true);
                    j.setPotencial(wi + (-1 * x.getCusto()));
                    fila.enfileira(j.getId());
                 }
              }
              
              fila.desenfileira();
              if(fila.isEmpty()==false)
                raiz = fila.pegaPrimeiro();
        }
        while(fila.isEmpty()==false);
        
        for(Nó x : nós)
        {
            System.out.print("Potencial de " + x.getId() + " -> ");
            System.out.println(x.getPotencial());
            x.setPotencialCalculado(false);
        }
    }
    
    
    private void calculaCustosRelativos()
    {
        int wi, wj, cij;
        for(Arco x : grafo_original)
        {
            if (!x.ehBásico())
            {
                wi = nós.get(x.getOrigem()-1).getPotencial();
                wj = nós.get(x.getDestino()-1).getPotencial();
                cij = x.getCusto();
                x.setCustoRelativo(wi - wj - cij);
            }
        }
        
        System.out.println("\n");
        for(Arco x : grafo_original)
        {
            if(!x.ehBásico())
              System.out.println("Custo Relativo de ("+x.getOrigem() +", "+ x.getDestino() + "): " + x.getCustoRelativo() );
        }
    }
    
    
    
    private void candidatoAEntrar()
    {
        Arco candidato = new Arco(0,0,0,0,false,false);;
        int maiorCusto = 0;
        
        for(Arco x : grafo_original)
        {
            if ((!x.ehBásico()) && (x.getCustoRelativo() > maiorCusto) && (x.getFluxo() < x.getLimSup()))
            {
                candidato = x;
                maiorCusto = candidato.getCustoRelativo();
            }
        }
        
        System.out.println("\nCandidato a entrar na base:");
        candidato.imprimeArco();
        System.out.println("\n");
        candidatoEntrar = candidato;
        
    }
    

    private void encontraCiclo(Arco arco)
    {
        ciclo.add(arco);
        candidatoEntrar.setBásico(true);
        
        for(Arco x : grafo_original)
        {
            if((x.ehBásico())&&(x != arco))
            {
                ciclo.add(x);
            }
        }
        
        while(temFolha())
        {
            removeFolhas();
        }
        
        System.out.println("Ciclo:");
        
        defineSentido(arco);
     }
     
     private void defineSentido(Arco sentido)
     {
        sentido.setSentido(1);
        int origem = sentido.getOrigem();
        int destino = sentido.getDestino();
        
        do
        {
            for(Arco x : ciclo)
            {
              if( x != sentido)  
              {
                if(x.ehBásico() && (origem != nó_artificial))
                {
                    if((x.getOrigem() == origem))
                    {
                        x.setSentido(-1);
                        origem = x.getDestino();
                    }
                    else
                    if((x.getDestino() == origem))
                    {
                        x.setSentido(1);
                        origem = x.getOrigem();
                    }
                }
              }
            }
        }while (origem != nó_artificial);
        
        do
        {
            for(Arco x : ciclo)
            {
              if(x != sentido)
              {
                if((x.ehBásico()) && (destino != nó_artificial))
                {
                    if((x.getOrigem() == destino) && (!x.estáNoCiclo()))
                    {
                        x.setSentido(1);
                        destino = x.getDestino();
                    }
                    else
                    if((x.getDestino() == destino) && (!x.estáNoCiclo()))
                    {
                        x.setSentido(-1);
                        destino = x.getOrigem();
                    }
                }
              }
            }
        }while (destino != nó_artificial);
     }
     
     
     
     private boolean temFolha()
     {
        for(Arco x : ciclo)
        {
            if(((getAdjacência(x.getOrigem())<2) || (getAdjacência(x.getDestino())<2)) && (x != candidatoEntrar))
            {
                saiDoCiclo.add(x);
                return true;
            }
        }    
        return false;
     }

     private void removeFolhas()
     {
         Arco aRemover = new Arco(0,0,0,0,false, false);
         for(Arco x : saiDoCiclo)
            for(Arco y : ciclo)
            if(x == y)
                aRemover = y;
                
         ciclo.remove(aRemover);
     }
     
     
     private int getAdjacência(int adj)
     {
       int total = 0;
       for(Arco x : ciclo)
       {
           if((x.getOrigem() == adj) || (x.getDestino() == adj))
           total++;
       }
       return total;
     }
           
    private void calculaBloqueio()
    {
        int bloqueio;

        for(Arco x : ciclo)
        {
                if(x.getSentido() == 1)
                {
                  
                    bloqueio = (x.getLimSup() - x.getFluxo());
                    if(bloqueio < delta)
                    {
                        delta = bloqueio;
                        candidatoSair = x;
                        x.imprimeArco();
                    }
                    else
                    if(bloqueio == delta)
                    {
                        if(x.ehArtificial())
                        {
                            candidatoSair = x;
                            x.imprimeArco();
                        }
                    }
                }
                else      
                if(x.getSentido() == -1)
                {
                    bloqueio = (x.getFluxo() - x.getLimInf());
                    if(bloqueio < delta)
                    {
                        delta = bloqueio;
                        candidatoSair = x;
                        x.imprimeArco();
                    }
                    else
                    if(bloqueio == delta)
                    {
                        if(x.ehArtificial())
                        {
                            candidatoSair = x;
                            x.imprimeArco();
                        }
                    }
                  }
       }
    
        System.out.println("\nDelta: "+delta);
        System.out.println("Candidato a sair da base:");
        candidatoSair.imprimeArco();
    }
    
    private void redefineFluxos()
    {      
        for (Arco x : ciclo)
        {
             if(x.getSentido() == 1)
             {
                 x.setFluxo(x.getFluxo() + delta);
             }
             else
             if(x.getSentido() == -1)
             {
                 x.setFluxo(x.getFluxo() - delta);
             }
        }
        
        System.out.println("\n\nFluxos atualizados:");
        for (Arco x : ciclo)
        {
            x.imprimeArco();
        }
        
        if(candidatoSair.ehArtificial())
          grafo_original.remove(candidatoSair);
        else
          candidatoSair.setBásico(false);
        
        System.out.println("\nSai da base o arco:");
        candidatoSair.imprimeArco();
          ciclo.remove(candidatoSair); 
          System.out.println("------------------------------------------------------------");
        
        
    }
    
    
    private void resetaVariáveis()
    {
        for (Arco x : grafo_original)
        {
            x.setEstáNoCiclo(false);
        }    
        
        for (Nó x : nós)
        {
            if(x.getId() != nó_artificial)
            {
                x.setPotencial(0);
                x.setPotencialCalculado(false);
            }
        }
        
        saiDoCiclo.clear();
        ciclo.clear();
        fila.clear();
        delta = 25000;
        candidatoSair = new Arco(0,0,0,0,false,false);
    
    }
    
    private int contaArcosArtificiais()
    {
        int contador = 0;
            for(Arco x : grafo_original)
            {
                if(x.ehArtificial())
                contador++;
            }
            return contador;
    }
    
    
    private void soluçãoFactível()
    {
        Arco removeÚltimo = new Arco(0,0,0,0,false,false);
        for(Arco x : grafo_original)
        {
            if((x.getOrigem()== nó_artificial) || (x.getDestino()== nó_artificial)) 
            {
                if(x.getFluxo() > 0)
                    System.out.println("PROBLEMA SEM SOLUÇÃO - FASE I");
                else
                    removeÚltimo = x;
            }
        }
        
        if(removeÚltimo.getOrigem() != 0)
        {
            grafo_original.remove(removeÚltimo);
       
            System.out.println("\nSOLUÇÃO FACTÍVEL: ");
            for(Arco x : grafo_original)
            {
                x.imprimeArco();
            }
            System.out.println("\nFIM DA FASE 1");
            System.out.println("------------------------------------------------------------");
        }
   }
   
        
   public void fase2()
   {
   int raiz;
       
       System.out.println("INÍCIO DA FASE 2");
       System.out.println("Insira um nó raiz para iniciar a fase 2: ");

       raiz = entrada.nextInt();
            
       for(Nó x : nós )
       {
            if(x.getId() == raiz)
            x.setPotencial(0);
       }
            
      calculaPotenciais(raiz);
      calculaCustosRelativos();
            
      while(condiçãoDeParada()==false)
      {
        candidatoAEntrar();
        encontraCiclo(candidatoEntrar);
        calculaBloqueio();
        redefineFluxos();
        resetaVariáveis();
        calculaPotenciais(raiz);
        calculaCustosRelativos();
      }
        
       System.out.println("SOLUÇÃO OTIMIZADA PELA FASE II:");
       //soluçãoFactível();
       soluçãoÓtima();
       System.out.println("\nFIM DA FASE 2");
       System.out.println("------------------------------------------------------------");   
       
    }
        
        
        
    private boolean condiçãoDeParada()
    {
        for(Arco x : grafo_original)
        {
            if((!x.ehBásico()) && (x.getFluxo() < x.getLimSup()))
            {
                if(x.getCustoRelativo() > 0)
                return false;
            }
        }
        System.out.println("\nNão existem mais arcos com custo relativo maior do que zero,\nou os arcos com custo relativo maior que zero já estão saturados\n");
        return true;  
    }
        

    private void soluçãoÓtima()
    {
        int somatório = 0;
        for(Arco x : grafo_original)
        {
            if(x.getFluxo()>0)
            {
                System.out.println("("+x.getOrigem()+","+x.getDestino()+") --> "+"Fluxo: "+x.getFluxo()+" -- "+"Custo: "+x.getCusto());
                somatório += (x.getFluxo()*x.getCusto());
            }
        }
            
        System.out.println("\nFunção Objetivo: "+somatório);
    }
 
}

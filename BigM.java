import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BigM
{
    private ArrayList<N�> n�s = new ArrayList<N�>();
    private ArrayList<Arco> grafo_original = new ArrayList<Arco>();
    private ArrayList<Arco> ciclo = new ArrayList<Arco>();
    private ArrayList<Arco> saiDoCiclo = new ArrayList<Arco>();
    private Arco candidatoSair = new Arco(0,0,0,0,false,false);
    private Arco candidatoEntrar = new Arco(0,0,0,0,false,false);
    private int num_n�s;
    private int num_arestas;
    private int n�_artificial;
    private Scanner entrada = new Scanner(System.in);
    public static final int MGRANDE = 10000;
    public static final int INFINITO = 10000;
    private Fifo fila = new Fifo();
    int delta = 10000;

       
    public BigM(int num_n�s, int num_arestas)
    {
        this.num_n�s = num_n�s;
        this.num_arestas = num_arestas;
        n�_artificial = (num_n�s + 1);
                
        /*n�s.add(new N�(1, 6));
        n�s.add(new N�(2, -3));
        n�s.add(new N�(3, -4));
        n�s.add(new N�(4, -2));
        n�s.add(new N�(5, 3));
        n�s.add(new N�(n�_artificial, 0));*/
        
          /*n�s.add(new N�(1, 1));
          n�s.add(new N�(2, 0));
          n�s.add(new N�(3, 2));
          n�s.add(new N�(4, -3));
          n�s.add(new N�(n�_artificial, 0));*/
          
                /*n�s.add(new N�(1, 8));
                n�s.add(new N�(2, 0));
                n�s.add(new N�(3, -4));
                n�s.add(new N�(4, 0));
                n�s.add(new N�(5, 0));
                n�s.add(new N�(6, 0));
                n�s.add(new N�(7, 4));
                n�s.add(new N�(8, -8));
                n�s.add(new N�(n�_artificial, 0));*/

              //n�s.get(n�_artificial-1).setPotencialCalculado(true);
        
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
                
          
          
        l�_entrada();
        
        cria_arcos_articiais();
               
        int i = 1;
        while (contaArcosArtificiais()>1)
        {
           System.out.println("\n"+i+"� ITERA��O:");
            
            try 
            {    
                Thread.sleep(10); 
            }
            catch (Exception e) 
            {     
            }
            
            calculaPotenciais(n�_artificial);
            calculaCustosRelativos();
            candidatoAEntrar();
            encontraCiclo(candidatoEntrar);
            calculaBloqueio();
            redefineFluxos();
            resetaVari�veis();
            i++;
        }
        
        solu��oFact�vel();
        
        //fase2();        
    }

    public void l�_entrada()
    {
        int id, demanda;  
        
        System.out.println("Insira os dados dos n�s separados por um espa�o");

        for(int i=0; i<num_n�s; i++)
        {
            System.out.print("Insira o id do n�, seguido de sua demanda ");
            String linha_digitada = entrada.nextLine();    
        
            StringTokenizer tokenizer = new StringTokenizer(linha_digitada, " ");
              id = Integer.parseInt(tokenizer.nextToken());
              demanda = Integer.parseInt(tokenizer.nextToken());
            
            n�s.add(new N�(id, demanda));
        }
        n�s.add(new N�(n�_artificial, 0));
        n�s.get(n�_artificial-1).setPotencialCalculado(true);
        
        for(int i=0; i<num_arestas; i++)
        {
            int n�_origem, n�_destino, lim_sup, custo, b�sico, artificial;
            System.out.print("Insira os dados: origem, destino, lim_sup, custo: ");
            String linha_digitada = entrada.nextLine();    
        
            StringTokenizer tokenizer = new StringTokenizer(linha_digitada, " ");
              n�_origem = Integer.parseInt(tokenizer.nextToken());
              n�_destino = Integer.parseInt(tokenizer.nextToken());
              lim_sup = Integer.parseInt(tokenizer.nextToken());
              custo = Integer.parseInt(tokenizer.nextToken());
              b�sico = 0;
              artificial = 0;
            
            grafo_original.add(new Arco(n�_origem, n�_destino, lim_sup, custo, ((b�sico==1)? true : false), ((artificial==1)? true : false)));
        }        
    }
    
    
    private void cria_arcos_articiais()
    {
        Arco aux;
       
            for(N� x : n�s)
            {
                if((x.getId() < n�_artificial) && (x.getDemanda()<0))
                {
                    aux = (new Arco(n�_artificial, x.getId(), INFINITO, MGRANDE , true, true));
                    aux.setFluxo(x.getDemanda() * -1);
                    grafo_original.add(aux);
                }
                else
                if((x.getId()<n�_artificial) && (x.getDemanda()>=0))
                {
                    aux = (new Arco(x.getId(), n�_artificial, INFINITO, MGRANDE , true, true));
                    aux.setFluxo(x.getDemanda());
                    grafo_original.add(aux);
                }
            }
            
            for(Arco x : grafo_original)
              x.imprimeArco();
    }
    
    
    private void calculaPotenciais(int n�_raiz)
    {
        int raiz = n�_raiz;
        N� i, j;
        int wi, wj;
        fila.enfileira(raiz);
        do
        {
              for(Arco x : grafo_original)
              {
                 i = n�s.get(x.getOrigem()-1);
                 j = n�s.get(x.getDestino()-1);
                 wi = (0 + i.getPotencial());
                 wj = (0 + j.getPotencial());
                 
                 if ((x.ehB�sico()) && (x.getDestino() == fila.pegaPrimeiro()) && (i.potencialCalculado()==false))
                 {
                    i.setPotencial( wj + x.getCusto());
                    i.setPotencialCalculado(true);
                    fila.enfileira(i.getId());
                 }
                 else
                 if ((x.ehB�sico()) && (x.getOrigem() == fila.pegaPrimeiro()) && (j.potencialCalculado()==false))
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
        
        for(N� x : n�s)
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
            if (!x.ehB�sico())
            {
                wi = n�s.get(x.getOrigem()-1).getPotencial();
                wj = n�s.get(x.getDestino()-1).getPotencial();
                cij = x.getCusto();
                x.setCustoRelativo(wi - wj - cij);
            }
        }
        
        System.out.println("\n");
        for(Arco x : grafo_original)
        {
            if(!x.ehB�sico())
              System.out.println("Custo Relativo de ("+x.getOrigem() +", "+ x.getDestino() + "): " + x.getCustoRelativo() );
        }
    }
    
    
    
    private void candidatoAEntrar()
    {
        Arco candidato = new Arco(0,0,0,0,false,false);;
        int maiorCusto = 0;
        
        for(Arco x : grafo_original)
        {
            if ((!x.ehB�sico()) && (x.getCustoRelativo() > maiorCusto) && (x.getFluxo() < x.getLimSup()))
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
        candidatoEntrar.setB�sico(true);
        
        for(Arco x : grafo_original)
        {
            if((x.ehB�sico())&&(x != arco))
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
                if(x.ehB�sico() && (origem != n�_artificial))
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
        }while (origem != n�_artificial);
        
        do
        {
            for(Arco x : ciclo)
            {
              if(x != sentido)
              {
                if((x.ehB�sico()) && (destino != n�_artificial))
                {
                    if((x.getOrigem() == destino) && (!x.est�NoCiclo()))
                    {
                        x.setSentido(1);
                        destino = x.getDestino();
                    }
                    else
                    if((x.getDestino() == destino) && (!x.est�NoCiclo()))
                    {
                        x.setSentido(-1);
                        destino = x.getOrigem();
                    }
                }
              }
            }
        }while (destino != n�_artificial);
     }
     
     
     
     private boolean temFolha()
     {
        for(Arco x : ciclo)
        {
            if(((getAdjac�ncia(x.getOrigem())<2) || (getAdjac�ncia(x.getDestino())<2)) && (x != candidatoEntrar))
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
     
     
     private int getAdjac�ncia(int adj)
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
          candidatoSair.setB�sico(false);
        
        System.out.println("\nSai da base o arco:");
        candidatoSair.imprimeArco();
          ciclo.remove(candidatoSair); 
          System.out.println("------------------------------------------------------------");
        
        
    }
    
    
    private void resetaVari�veis()
    {
        for (Arco x : grafo_original)
        {
            x.setEst�NoCiclo(false);
        }    
        
        for (N� x : n�s)
        {
            if(x.getId() != n�_artificial)
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
    
    
    private void solu��oFact�vel()
    {
        Arco remove�ltimo = new Arco(0,0,0,0,false,false);
        for(Arco x : grafo_original)
        {
            if((x.getOrigem()== n�_artificial) || (x.getDestino()== n�_artificial)) 
            {
                if(x.getFluxo() > 0)
                    System.out.println("PROBLEMA SEM SOLU��O - FASE I");
                else
                    remove�ltimo = x;
            }
        }
        
        if(remove�ltimo.getOrigem() != 0)
        {
            grafo_original.remove(remove�ltimo);
       
            System.out.println("\nSOLU��O FACT�VEL: ");
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
       
       System.out.println("IN�CIO DA FASE 2");
       System.out.println("Insira um n� raiz para iniciar a fase 2: ");

       raiz = entrada.nextInt();
            
       for(N� x : n�s )
       {
            if(x.getId() == raiz)
            x.setPotencial(0);
       }
            
      calculaPotenciais(raiz);
      calculaCustosRelativos();
            
      while(condi��oDeParada()==false)
      {
        candidatoAEntrar();
        encontraCiclo(candidatoEntrar);
        calculaBloqueio();
        redefineFluxos();
        resetaVari�veis();
        calculaPotenciais(raiz);
        calculaCustosRelativos();
      }
        
       System.out.println("SOLU��O OTIMIZADA PELA FASE II:");
       //solu��oFact�vel();
       solu��o�tima();
       System.out.println("\nFIM DA FASE 2");
       System.out.println("------------------------------------------------------------");   
       
    }
        
        
        
    private boolean condi��oDeParada()
    {
        for(Arco x : grafo_original)
        {
            if((!x.ehB�sico()) && (x.getFluxo() < x.getLimSup()))
            {
                if(x.getCustoRelativo() > 0)
                return false;
            }
        }
        System.out.println("\nN�o existem mais arcos com custo relativo maior do que zero,\nou os arcos com custo relativo maior que zero j� est�o saturados\n");
        return true;  
    }
        

    private void solu��o�tima()
    {
        int somat�rio = 0;
        for(Arco x : grafo_original)
        {
            if(x.getFluxo()>0)
            {
                System.out.println("("+x.getOrigem()+","+x.getDestino()+") --> "+"Fluxo: "+x.getFluxo()+" -- "+"Custo: "+x.getCusto());
                somat�rio += (x.getFluxo()*x.getCusto());
            }
        }
            
        System.out.println("\nFun��o Objetivo: "+somat�rio);
    }
 
}

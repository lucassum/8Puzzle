package principal;

import front.Tela;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author lucassum
 */
public class Algoritmos {

    //Aqui temos o objetivo
    public static final byte[] objetivo = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    // Fila de prioridade 
    static final PriorityQueue<Estado> fila = new PriorityQueue<>(100, (Estado a, Estado b) -> a.prioridade() - b.prioridade());

    // Lista de elementos únicos fechados (já foram visitados)
    static final HashSet<Estado> fechados = new HashSet<>();

    // Adiciona um sucessor à fila de prioridades
    static void adicionarSucessor(Estado sucessor) {
        if (sucessor != null && !fechados.contains(sucessor)) {
            fila.add(sucessor);
        }
    }

    public static byte[] obterElementosPorPosicao(Estado estado, int posicao) {
        switch (posicao) {
            case 0:
                return new byte[]{estado.quadrados[0], estado.quadrados[1], estado.quadrados[2]};
            case 1:
                return new byte[]{estado.quadrados[3], estado.quadrados[4], estado.quadrados[5]};
            default:
                return new byte[]{estado.quadrados[6], estado.quadrados[7], estado.quadrados[8]};
        }
    }

    public static boolean isPossivel(Estado estado) {
        int contador = 0;
        List<Byte> array = new ArrayList<>();
        byte[][] matriz = new byte[][]{obterElementosPorPosicao(estado, 0), obterElementosPorPosicao(estado, 1), obterElementosPorPosicao(estado, 2)};

        for (byte[] matriz1 : matriz) {
            for (int j = 0; j < matriz.length; j++) {
                array.add(matriz1[j]);
            }
        }

        Byte[] outroVetor = new Byte[array.size()];
        array.toArray(outroVetor);

        for (int i = 0; i < outroVetor.length - 1; i++) {
            for (int j = i + 1; j < outroVetor.length; j++) {
                if (outroVetor[i] != 0 && outroVetor[j] != 0 && outroVetor[i] > outroVetor[j]) {
                    contador++;
                }
            }
        }

        return contador % 2 == 0;
    }

    // Inicia as verificações até achar o objetivo ou n encontrar o objetivo
    public static void resolver(Estado estado, Tela tela) {
        fila.clear();
        fechados.clear();

        // Temporizador
        long tempoInicio = System.currentTimeMillis();

        // estado inicial
        fila.add(estado);
        while (!fila.isEmpty()) {
            // retorna o estado com a menor prioridade
            Estado estadoPoll = fila.poll();
            // Se entrar, é o objetivo
            if (estadoPoll.isObjetivo()) {
                long tempoFim = System.currentTimeMillis() - tempoInicio;
                tela.setarLabelFinal(tempoFim + "", estadoPoll.qtdMovimentos + "",fechados.size()+"");
                tela.alterarStatusResolvidoBotaoResolver();
                tela.setEstado(estadoPoll);
                return;
            }
            // fecha o estado para que n seja revisitado
            fechados.add(estadoPoll);

            // Constroi os sucessores e os adiciona à fila de prioridades
            adicionarSucessor(estadoPoll.baixo());
            adicionarSucessor(estadoPoll.cima());
            adicionarSucessor(estadoPoll.esquerda());
            adicionarSucessor(estadoPoll.direita());
        }
    }
    
    // retorna o índice de val em a[]
    static int indice(byte[] a, int val) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == val) {
                return i;
            }
        }
        return -1;
    }

    // retorna a distancia de manhattan ente 2 valores em um valor absoluto (sem + ou -)
    static int manhattan(int a, int b) {
        return Math.abs(a / 3 - b / 3) + Math.abs(a % 3 - b % 3);
    }

    // Retorna o maior valor do vetor
    static int heuristico(byte[] quadrados) {
        int h = 0;
        for (int i = 0; i < quadrados.length; i++) {
            if (quadrados[i] != 0) {
                h = Math.max(h, manhattan(i, quadrados[i]));
            }
        }
        return h;
    }

}

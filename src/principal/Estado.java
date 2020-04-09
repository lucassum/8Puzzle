/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.util.Arrays;
import static principal.Algoritmos.indice;

/**
 *
 * @author lucassum
 */
public class Estado {

    public final byte[] quadrados;
    final int indiceZero;
    final int qtdMovimentos;
    final int valorHeuristico;
    public final Estado estadoAnterior;

    // Prioridade 
    int prioridade() {
        return qtdMovimentos + valorHeuristico;
    }

    // Estado inicial
    public Estado(byte[] quadradosIniciais) {
        quadrados = quadradosIniciais;
        indiceZero = indice(quadrados, 0);
        qtdMovimentos = 0;
        valorHeuristico = Algoritmos.heuristico(quadrados);
        estadoAnterior = null;
    }

    // Sucessor do estado anterior
    // Cria um novo estado e seta o anterior dele
    public Estado(Estado anterior, int indice) {
        quadrados = Arrays.copyOf(anterior.quadrados, anterior.quadrados.length);
        quadrados[anterior.indiceZero] = quadrados[indice];
        quadrados[indice] = 0;
        indiceZero = indice;
        qtdMovimentos = anterior.qtdMovimentos + 1;
        valorHeuristico = Algoritmos.heuristico(quadrados);
        this.estadoAnterior = anterior;
    }

    // Verifica se é o objetivo
    public boolean isObjetivo() {
        return Arrays.equals(quadrados, Algoritmos.objetivo);
    }

    // Sucessores de acordo com a posição atual do zero
    Estado baixo() {
        return indiceZero > 2 ? new Estado(this, indiceZero - 3) : null;
    }

    Estado cima() {
        return indiceZero < 6 ? new Estado(this, indiceZero + 3) : null;
    }

    Estado direita() {
        return indiceZero % 3 > 0 ? new Estado(this, indiceZero - 1) : null;
    }

    Estado esquerda() {
        return indiceZero % 3 < 2 ? new Estado(this, indiceZero + 1) : null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Estado) {
            Estado other = (Estado) obj;
            return Arrays.equals(quadrados, other.quadrados);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(quadrados);
    }

    @Override
    public String toString() {
        return "Estado{" + "quadrados=" + Arrays.toString(quadrados) + ", indiceZero=" + indiceZero + ", qtdMovimentos=" + qtdMovimentos + ", valorHeuristico=" + valorHeuristico + ", estadoAnterior=" + estadoAnterior + '}';
    }

}

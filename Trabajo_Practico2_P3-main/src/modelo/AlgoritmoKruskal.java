package modelo;

import java.util.*;

public class AlgoritmoKruskal {

    /**
     * Calcula el árbol generador mínimo sobre el grafo completo de localidades.
     * Usa Union-Find para detectar ciclos eficientemente.
     */
    public static ResultadoMST calcular(List<Localidad> localidades, ParametrosRed params) {
        if (localidades.size() < 2) {
            return new ResultadoMST(new ArrayList<>(), 0);
        }

        // 1. Generar todas las aristas del grafo completo
        List<Arista> todasLasAristas = new ArrayList<>();
        for (int i = 0; i < localidades.size(); i++) {
            for (int j = i + 1; j < localidades.size(); j++) {
                Localidad a = localidades.get(i);
                Localidad b = localidades.get(j);
                double costo = CalculadorCosto.calcular(a, b, params);
                todasLasAristas.add(new Arista(a, b, costo));
            }
        }

        // 2. Ordenar por costo ascendente
        Collections.sort(todasLasAristas);

        // 3. Kruskal con Union-Find
        UnionFind uf = new UnionFind(localidades);
        List<Arista> mst = new ArrayList<>();
        double costoTotal = 0;

        for (Arista arista : todasLasAristas) {
            if (!uf.mismoConjunto(arista.getOrigen(), arista.getDestino())) {
                uf.unir(arista.getOrigen(), arista.getDestino());
                mst.add(arista);
                costoTotal += arista.getCosto();
            }
            // El MST tiene exactamente n-1 aristas
            if (mst.size() == localidades.size() - 1) break;
        }

        return new ResultadoMST(mst, costoTotal);
    }

    // ── Union-Find interno ──────────────────────────────────────────────────

    private static class UnionFind {
        private Map<Localidad, Localidad> padre = new HashMap<>();
        private Map<Localidad, Integer> rango   = new HashMap<>();

        UnionFind(List<Localidad> nodos) {
            for (Localidad l : nodos) {
                padre.put(l, l);
                rango.put(l, 0);
            }
        }

        Localidad encontrar(Localidad l) {
            if (!padre.get(l).equals(l)) {
                padre.put(l, encontrar(padre.get(l))); // compresión de camino
            }
            return padre.get(l);
        }

        boolean mismoConjunto(Localidad a, Localidad b) {
            return encontrar(a).equals(encontrar(b));
        }

        void unir(Localidad a, Localidad b) {
            Localidad raizA = encontrar(a);
            Localidad raizB = encontrar(b);
            if (raizA.equals(raizB)) return;

            // unión por rango
            if (rango.get(raizA) < rango.get(raizB)) {
                padre.put(raizA, raizB);
            } else if (rango.get(raizA) > rango.get(raizB)) {
                padre.put(raizB, raizA);
            } else {
                padre.put(raizB, raizA);
                rango.put(raizA, rango.get(raizA) + 1);
            }
        }
    }
}
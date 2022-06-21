import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Set;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * Cada tema té el mateix pes, i l'avaluació consistirà en:
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - La neteja del codi (pensau-ho com faltes d'ortografia). L'estàndar que heu de seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . No és
 *   necessari seguir-la estrictament, però ens basarem en ella per jutjar si qualcuna se'n desvia
 *   molt.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Krishna Jorda Jimenez
 * - Nom 2:
 * - Nom 3:
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital abans de la data que se us hagui
 * comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més fàcilment
 * les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat, assegurau-vos
 * que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * Els mètodes reben de paràmetre l'univers (representat com un array) i els predicats adients
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un element de
   * l'univers, podeu fer-ho com `p.test(x)`, té com resultat un booleà. Els predicats de dues
   * variables són de tipus `BiPredicate<Integer, Integer>` i similarment s'avaluen com
   * `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
   * o `false` segons si la proposició donada és certa (suposau que l'univers és suficientment
   * petit com per utilitzar la força bruta)
   */
  static class Tema1 {
    /*
     * És cert que ∀x,y. P(x,y) -> Q(x) ^ R(y) ?
     */
    static boolean exercici1(
        int[] universe,
        BiPredicate<Integer, Integer> p,
        Predicate<Integer> q,
        Predicate<Integer> r) {
        
      for (int x : universe) { // ∀x
        for (int y : universe) { // ∀y
          boolean qYR = (q.test(x) && r.test(y)); // Q(x) ^ R(y)
          if (p.test(x, y) && !qYR) { // P(x,y) ^ ¬(Q(x) ^ R(y)) (La implicacion es falsa)
            return false;
          }
        }
      }

      return true; // TO DO
    }

    /*
     * És cert que ∃!x. ∀y. Q(y) -> P(x) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
      boolean verdadero = false;

      for (int x : universe) {
        boolean paraTodoY = true;
        for (int y : universe) {
          if (q.test(y) && !p.test(x)) { // ¬(Q(y) -> P(x))
            paraTodoY = false;
          }
        }
        if (verdadero && paraTodoY) {
          return false; // Si ya habia un "x" valido y hay otro es false directamente
        } else if (paraTodoY) {
          verdadero = true; // Si no habia ningun "x" previo este es el unico de momento
        }
      }
        
      return verdadero; // TO DO
    }

    /*
     * És cert que ¬(∃x. ∀y. y ⊆ x) ?
     *
     * Observau que els membres de l'univers són arrays, tractau-los com conjunts i podeu suposar
     * que cada un d'ells està ordenat de menor a major.
     */
    static boolean exercici3(int[][] universe) {
      for (int[] x : universe) {
        boolean todoYSubconjuntoDeX = true;
        for (int[] y : universe) {
          if (!esSubconjunto(y, x)) {
            todoYSubconjuntoDeX = false;
          }
        }
        if (todoYSubconjuntoDeX) {
          return false;
        }
      }
      return true; // TO DO
    }
    
    static boolean esSubconjunto(int[] conjunto1, int[] conjunto2){
      if (conjunto1.length > conjunto2.length) {
        return false;
      }

      int idxConjunto2 = 0; // Dado que estan ordenados
      for (int i = 0; i < conjunto1.length; i++) {
        boolean elementoEncontrado = false;
        for (; (idxConjunto2 < conjunto2.length) && !elementoEncontrado; idxConjunto2++) {
          if (conjunto1[i] == conjunto2[idxConjunto2]) {
            elementoEncontrado = true;
          }
        }
        if (!elementoEncontrado) {
          return false;
        }
      }
      return true;
    }

    /*
     * És cert que ∀x. ∃!y. x·y ≡ 1 (mod n) ?
     */
    static boolean exercici4(int[] universe, int n) {
      boolean resultado = true;

      for (int x : universe) {
        boolean hayYValido = false;
        for (int y : universe) {
          if (((x * y) % n) == 1) {
            if (hayYValido) {
              return false;
            } else {
              hayYValido = true;
            }
          }
        }
        if (!hayYValido) {
          return false;
        }
      }
      return true; // TO DO
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // ∀x,y. P(x,y) -> Q(x) ^ R(y)

      assertThat(
          exercici1(
              new int[] { 2, 3, 5, 6 },
              (x, y) -> x * y <= 4,
              x -> x <= 3,
              x -> x <= 3
          )
      );

      assertThat(
          !exercici1(
              new int[] { -2, -1, 0, 1, 2, 3 },
              (x, y) -> x * y >= 0,
              x -> x >= 0,
              x -> x >= 0
          )
      );

      // Exercici 2
      // ∃!x. ∀y. Q(y) -> P(x) ?

      assertThat(
          exercici2(
              new int[] { -1, 1, 2, 3, 4 },
              x -> x < 0,
              x -> true
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3, 4, 5, 6 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0  // x és múltiple de 4
          )
      );

      // Exercici 3
      // ¬(∃x. ∀y. y ⊆ x) ?

      assertThat(
          exercici3(new int[][] { {1, 2}, {0, 3}, {1, 2, 3}, {} })
      );

      assertThat(
          !exercici3(new int[][] { {1, 2}, {0, 3}, {1, 2, 3}, {}, {0, 1, 2, 3} })
      );

      // Exercici 4
      // És cert que ∀x. ∃!y. x·y ≡ 1 (mod n) ?

      assertThat(
          exercici4(
              new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
              11
          )
      );

      assertThat(
          !exercici4(
              new int[] { 0, 5, 7 },
              13
          )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * De la mateixa manera que al Tema 1, per senzillesa tractarem els conjunts com arrays (sense
   * elements repetits). Per tant, un conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant int[] a,
   * int[] b, i un objecte de tipus Function<Integer, Integer> que podeu avaluar com f.apply(x) (on
   * x és un enter d'a i el resultat f.apply(x) és un enter de b).
   */
  static class Tema2 {
    /*
     * És `p` una partició d'`a`?
     *
     * `p` és un array de conjunts, haureu de comprovar que siguin elements d'`a`. Podeu suposar que
     * tant `a` com cada un dels elements de `p` està ordenat de menor a major.
     */
    static boolean exercici1(int[] a, int[][] p) {
      for (int[] subConjunto : p) {
        int idxA = 0;
        for (int elemSubconjunto : subConjunto) { // Para cada elemento del subconjunto actual
          boolean encontrado = false;
          for (; (idxA < a.length) && !encontrado; idxA++) { // Buscarlo en a
            if (a[idxA] == elemSubconjunto) {
              a[idxA] = Integer.MIN_VALUE; // Si lo encuentra marcado como usado
              encontrado = true;
            }
          }
          if (!encontrado) { // Si no ha encontrado un solo elemento de un solo subconjunto
            return false; // No es una particion
          }
        }
      }
      return true; // TO DO
    }

    /*
     * Comprovau si la relació `rel` definida sobre `a` és un ordre parcial i que `x` n'és el mínim.
     *
     * Podeu soposar que `x` pertany a `a` i que `a` està ordenat de menor a major.
     */
    static boolean exercici2(int[] a, int[][] rel, int x) {
        
      // Reflexiva
      for (int elem : a) {
        boolean reflexiva = false;
        for (int[] elemRel : rel) {
          if (elem == elemRel[0] && elem == elemRel[1]) {
            reflexiva = true;
          }
        }
        if (reflexiva == false) { // Si hay un solo elemento que no se relaciona con si mismo no es un POSET
          return false;
        }
      }

      // Antisimetrica
      for (int[] elemRel : rel) { // Si "a" se relaciona con "b"
        for (int i = 1; i < rel.length; i++) {
          if (rel[i][0] == elemRel[1] && rel[i][1] == elemRel[0]) { // Y "b" se relaciona con "a"
            if (elemRel[0] != elemRel[1]) { // Y "a" ≠ "b"
              return false; // La relacion no es antisimetrica y por lo tanto no es de orden parcial
            }
          }
        }
      }
        
      // Transitiva
      for (int[] rel1 : rel) { // Para cada relacion a -> b (rel1)
        for (int[] rel2 : rel) {
          if (rel2[0] == rel1[1]) { // Para cada relacion b -> c (rel2)
            boolean encontrada = false;
            for (int[] rel3 : rel) { // Buscar una relacion a -> c (rel3)
              if ((rel3[0] == rel1[0]) && (rel3[1] == rel2[1])) { // Si encuentra una relacion a -> c
                encontrada = true;
              }
            }
            if (!encontrada) {
              return false;
            }
          }
        }
      }
      
      // Minimo
      if (a[0] != x){
        return false;
      }
      
      return true; // TO DO
    }

    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Trobau l'antiimatge de
     * `y` (ordenau el resultat de menor a major, podeu utilitzar `Arrays.sort()`). Podeu suposar
     * que `y` pertany a `codom` i que tant `dom` com `codom` també estàn ordenats de menor a major.
     */
    static int[] exercici3(int[] dom, int[] codom, Function<Integer, Integer> f, int y) {
      int[] res = new int[codom.length];
      int ind = 0;
      for (int i = 0; i < dom.length && ind < res.length; i++) {
        if (f.apply(dom[i]) == y) {
          res[ind] = dom[i];
          ind++;
        }
      }

      if (esNulo(res)) {
        res = new int[]{};
      }
      Arrays.sort(res);
      return res; // TO DO
    }
    
    static boolean esNulo(int[] ary) {
      boolean res = true;
      for (int i = 0; i < ary.length; i++) {
        if (!(ary[i] == 0)) {
          res = false;
          break;
        }
      }
      return res;
    }

    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Retornau:
     * - 3 si `f` és bijectiva
     * - 2 si `f` només és exhaustiva
     * - 1 si `f` només és injectiva
     * - 0 en qualsevol altre cas
     *
     * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major. Per comoditat, podeu
     * utilitzar les constants definides a continuació:
     */
    static final int NOTHING_SPECIAL = 0;
    static final int INJECTIVE = 1;
    static final int SURJECTIVE = 2;
    static final int BIJECTIVE = INJECTIVE + SURJECTIVE;

    static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
      int res = NOTHING_SPECIAL;
      int[] img = new int[codom.length];
      int count = 0;
      int max = 1;
      int min = 1;

      for (int i = 0; i < dom.length; i++) {
        count = 0;
        for (int j = 0; j < codom.length; j++) {
          if (f.apply(dom[i]) == codom[j]) {
            img[j]++;
            count++;
          }
          if (count > 1) {
            return NOTHING_SPECIAL;
          }
          if (j == codom.length - 1 && count == 0) {
            return NOTHING_SPECIAL;
          }
        }
      }

      for (int i = 0; i < img.length; i++) {
        if (img[i] > 1) {
          max = img[i];
        }
        if (img[i] < 1) {
          min = img[i];
        }
      }

      if (dom.length < codom.length && min < 1 && max == 1) {
        res = INJECTIVE;
      }
      if (dom.length > codom.length && min == 1 && max > 1) {
        res = SURJECTIVE;
      }
      if (dom.length == codom.length && min == 1 && max == 1) {
        res = BIJECTIVE;
      }
      if (min < 1 && max > 1) {
        res = NOTHING_SPECIAL;
      }

      return res; // TO DO
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {

      // Exercici 1
      // `p` és una partició d'`a`?

      assertThat(
          exercici1(
              new int[] { 1, 2, 3, 4, 5 },
              new int[][] { {1, 2}, {3, 5}, {4} }
          )
      );

      assertThat(
          !exercici1(
              new int[] { 1, 2, 3, 4, 5 },
              new int[][] { {1, 2}, {5}, {1, 4} }
          )
      );

      // Exercici 2
      // és `rel` definida sobre `a` d'ordre parcial i `x` n'és el mínim?

      ArrayList<int[]> divisibility = new ArrayList<int[]>();

      for (int i = 1; i < 8; i++) {
        for (int j = 1; j <= i; j++) {
          if (i % j == 0) {
            // i és múltiple de j, és a dir, j|i
            divisibility.add(new int[] { j, i });
          }
        }
      }

      assertThat(
          exercici2(
              new int[] { 1, 2, 3, 4, 5, 6, 7 },
              divisibility.toArray(new int[][] {}),
              1
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3 },
              new int[][] { {1, 1}, {2, 2}, {3, 3}, {1, 2}, {2, 3} },
              1
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3, 4, 5, 6, 7 },
              divisibility.toArray(new int[][] {}),
              2
          )
      );

      // Exercici 3
      // calcular l'antiimatge de `y`

      assertThat(
          Arrays.equals(
              new int[] { 0, 2 },
              exercici3(
                  new int[] { 0, 1, 2, 3 },
                  new int[] { 0, 1 },
                  x -> x % 2, // residu de dividir entre 2
                  0
              )
          )
      );

      assertThat(
          Arrays.equals(
              new int[] { },
              exercici3(
                  new int[] { 0, 1, 2, 3 },
                  new int[] { 0, 1, 2, 3, 4 },
                  x -> x + 1,
                  0
              )
          )
      );

      // Exercici 4
      // classificar la funció en res/injectiva/exhaustiva/bijectiva

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3 },
              x -> (x + 1) % 4
          )
          == BIJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3, 4 },
              x -> x + 1
          )
          == INJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1 },
              x -> x / 2
          )
          == SURJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3 },
              x -> x <= 1 ? x+1 : x-1
          )
          == NOTHING_SPECIAL
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Aritmètica).
   *
   */
  static class Tema3 {
    /*
     * Donat `a`, `b` retornau el màxim comú divisor entre `a` i `b`.
     *
     * Podeu suposar que `a` i `b` són positius.
     */
    static int exercici1(int a, int b) {
      int aux;//Para no perder b
      while (b != 0) {
        aux = b;
        b = a % b;
        a = aux;
      }
      return a; // TO DO
    }

    /*
     * Es cert que `a``x` + `b``y` = `c` té solució?.
     *
     * Podeu suposar que `a`, `b` i `c` són positius.
     */
    static boolean exercici2(int a, int b, int c) {
      int aux;//Para no perder b
      while (b != 0) {
        aux = b;
        b = a % b;
        a = aux;
      }
      if (a % c == 0) {
        return true;
      }
      
      return false;
    }

    /*
     * Quin es l'invers de `a` mòdul `n`?
     *
     * Retornau l'invers sempre entre 1 i `n-1`, en cas que no existeixi retornau -1
     */
    static int exercici3(int a, int n) {
      int res = -1;
      int naux = n;
      int aaux = a;
      int aux;//Para no perder b
      while (naux != 0) {
        aux = naux;
        naux = aaux % naux;
        aaux = aux;
      }
      if (aaux == 1) {
        int x = 0;
        int r;
        boolean bucle = true;
        while (bucle) {
          r = (a * x) % n;
          if (r == 1) {
            bucle = false;
          } else {
            x++;
          }
        }
        res = x;
      }
      return res; // TO DO
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `mcd(a,b)`

      assertThat(
              exercici1(2, 4) == 2
      );

      assertThat(
              exercici1(1236, 984) == 12
      );

      // Exercici 2
      // `a``x` + `b``y` = `c` té solució?

      assertThat(
              exercici2(4,2,2)
      );
      assertThat(
              !exercici2(6,2,1)
      );
      // Exercici 3
      // invers de `a` mòdul `n`
      assertThat(exercici3(2, 5) == 3);
      assertThat(exercici3(2, 6) == -1);
    }
  }

  static class Tema4 {
    /*
     * Donada una matriu d'adjacencia `A` d'un graf no dirigit, retornau l'ordre i la mida del graf.
     */
    static int[] exercici1(int[][] A) {
      int[] res = new int[2];
      res[0] = A.length;
      for (int i = 0; i < A.length; i++) {
        for (int j = 0; j < A[0].length; j++) {
          if (A[i][j] == 1 || A[j][i] == 1) {
            res[1]++;
          }
        }

      }
      res[1] = res[1] / 2;
      System.out.println(res[0] + " " + res[1]);
      return res; // TO DO
    }

    /*
     * Donada una matriu d'adjacencia `A` d'un graf no dirigit, digau si el graf es eulerià.
     */
    static boolean exercici2(int[][] A) {
      return false; // TO DO
    }

    /*
     * Donat `n` el número de fulles d'un arbre arrelat i `d` el nombre de fills dels nodes interiors i de l'arrel,
     * retornau el nombre total de vèrtexos de l'arbre
     *
     */
    static int exercici3(int n, int d) {
      return -1; // TO DO
    }

    /*
     * Donada una matriu d'adjacencia `A` d'un graf connex no dirigit, digau si el graf conté algún cicle.
     */
    static boolean exercici4(int[][] A) {
      int dim = A.length;
      int[] recorrido = new int[dim]; // Aqui se guardara el numero de cada nodo que vayamos recorriendo
      recorrido[0] = 0;
      int indiceRecorrido = 0;
      // Tambien consideramos que un nodo es una 'hoja' si ya sabemos que solo lleva a nodos hoja
      boolean[] hoja = getHojas(A);
      boolean ciclo = false, fin = false;
      
      while (!ciclo) {
        //System.out.println("Indice actual:" + indiceRecorrido + "Nodo actual: " + recorrido[indiceRecorrido]);
        boolean avanzado = siguienteNodo(recorrido, indiceRecorrido, A, hoja);
        //System.out.println("Avanzado: " + avanzado);
        if (!avanzado) {
          if (indiceRecorrido == 0){
            return false;
          } else {
            hoja[recorrido[indiceRecorrido]] = true; // Marcar el nodo actual como hoja
            indiceRecorrido--; // Volver al nodo anterior
          }
        } else {
          if (indiceRecorrido == (A.length - 1)) { // Si este era el ultimo nodo y ha encontrado un siguiente nodo
            return true; // Solo puede significar que el ultimo nodo esta conectado con undo de los anterires
          }
          indiceRecorrido++;
          
          for (int i = 0; i < indiceRecorrido; i++) {
            if (recorrido[i] == recorrido[indiceRecorrido]) {
              ciclo = true; // Si encuentra un nodo por el que ya hemos pasado que sea igual al actual
            }
          }
        }
      }
      return ciclo; // TO DO
    }
    
    static boolean siguienteNodo(int[] recorrido, int indiceRecorrido, int[][] A, boolean[] hoja) {
      int siguienteNodo = 0;
      boolean encontrado = false;
      
      while ((siguienteNodo < A.length) && !encontrado) {
        if (A[recorrido[indiceRecorrido]][siguienteNodo] == 1) { // Si encuentra una conexion a otro nodo
          if (indiceRecorrido == 0 || siguienteNodo != recorrido[indiceRecorrido - 1]) { // Si ese nodo no es el anterior
            if (hoja[siguienteNodo] == false) { // Si el nodo que hemos encontrado lleva a algun sitio
              if (indiceRecorrido == (A.length - 1)) { // Si este es el ultimo nodo y ha encontrado un siguiente nodo
                return true;
              }
              encontrado = true;
              recorrido[indiceRecorrido + 1] = siguienteNodo;
            }
          }
          
        }
        siguienteNodo++;
      }
      
      return encontrado;
    }
    
    static boolean[] getHojas(int[][] A) {
      boolean[] hoja = new boolean[A.length];
      
      for (int i = 0; i < A.length; i++) { // Recorrer nodos
        int grado = 0;
        for (int j = 0; j < A.length; j++) { // Recorrer posibles arestas de ese nodo
          if (A[i][j] == 1) { // Si hay una aresta con otro nodo (=1), sumarle uno al grado de ese nodo
            grado++;
          }
        }
        if (grado == 1) {
          hoja[i] = true;
        } else{
          hoja[i] = false;
        }
      }
      
      return hoja;
    }
    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `ordre i mida`

      assertThat(
              Arrays.equals(exercici1(new int[][] { {0, 1, 0}, {1, 0, 1}, {0,1, 0}}), new int[] {3, 2})
      );

      assertThat(
              Arrays.equals(exercici1(new int[][] { {0, 1, 0, 1}, {1, 0, 1, 1}, {0 , 1, 0, 1}, {1, 1, 1, 0}}), new int[] {4, 5})
      );

      // Exercici 2
      // `Es eulerià?`

      assertThat(
              exercici2(new int[][] { {0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
      );
      assertThat(
              !exercici2(new int[][] { {0, 1, 0}, {1, 0, 1}, {0,1, 0}})
      );
      // Exercici 3
      // `Quants de nodes té l'arbre?`
      assertThat(exercici3(5, 2) == 9);
      assertThat(exercici3(7, 3) == 10);

      // Exercici 4
      // `Conté algún cicle?`
      assertThat(
              exercici4(new int[][] { {0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
      );
      assertThat(
              !exercici4(new int[][] { {0, 1, 0}, {1, 0, 1}, {0, 1, 0}})
      );

    }
  }


  /*
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    //Tema1.tests();
    //Tema2.tests();
    //Tema3.tests();
    Tema4.tests();
  }

  static void assertThat(boolean b) {
    if (!b) {
            System.out.println("ta mal");
            //throw new AssertionError();
        } else {
            System.out.println("ta bien");
        }
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :

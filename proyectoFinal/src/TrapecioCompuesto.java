import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class TrapecioCompuesto {
/**
     * Clase para almacenar los resultados de una ejecución de experimento.
     */
    static class Resultado {
        // Parámetros utilizados
        int n; // Número de subintervalos
        double h; // Tamaño del paso
        
        // Resultados
        double valorNumerico;
        double valorExacto;
        double errorAbsoluto;
        double errorRelativo;
        long tiempoEjecucionNano; // Tiempo en nanosegundos (Punto 3)
        int numIteraciones;      // Número de sumas realizadas (Punto 3)
    }

    // Función f(x) = x^3 − 6x^2 + 11x − 6
    static double f(double x) {
        return x*x*x - 6*x*x + 11*x - 6;
    }

    /**
     * Función para calcular el valor exacto de la integral de f(x). (Punto 3)
     */
    public static double valorExacto(double a, double b) {
        // F(x) = x^4/4 - 2x^3 + 5.5x^2 - 6x
        double Fb = 0.25 * Math.pow(b, 4) - 2 * Math.pow(b, 3) + 5.5 * Math.pow(b, 2) - 6 * b;
        double Fa = 0.25 * Math.pow(a, 4) - 2 * Math.pow(a, 3) + 5.5 * Math.pow(a, 2) - 6 * a;
        return Fb - Fa;
    }
    
    /**
     * Función para calcular el error absoluto. (Punto 3)
     */
    public static double calcularErrorAbsoluto(double exacto, double numerico) {
        return Math.abs(exacto - numerico);
    }

    /**
     * Función para calcular el error relativo. (Punto 3)
     */
    public static double calcularErrorRelativo(double exacto, double numerico) {
        if (exacto == 0) return Double.NaN;
        return calcularErrorAbsoluto(exacto, numerico) / Math.abs(exacto);
    }

    /**
     * Implementación del Algoritmo de la Regla del Trapecio Compuesta. (Punto 2 y 3)
     * NOTA: Esta versión es solo para el cálculo numérico, sin impresión de pasos.
     */
    public static double trapecioCompuesto(double a, double b, int n) {
        double h = (b - a) / n;
        double S = 0.0;
        
        // Suma de puntos intermedios (k=1 hasta n-1)
        for (int k = 1; k <= n - 1; k++) {
            S += f(a + k * h);
        }

        // Aplicar fórmula I = h * [ f(a)/2 + S + f(b)/2 ]
        return h * (0.5 * f(a) + S + 0.5 * f(b));
    }


    // =========================================================================
    // FUNCIÓN PRINCIPAL
    // =========================================================================

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // Para usar punto decimal con '.'
        Scanner leer = new Scanner(System.in);

        // 1) Mensaje inicial: descripción de la función y el método
        System.out.println("==============================================");
        System.out.println("  Regla del Trapecio Compuesta");
        System.out.println("  Funcion a integrar:");
        System.out.println("      f(x) = x^3 - 6x^2 + 11x - 6");
        System.out.println("==============================================\n");

        // 2) Pedir datos al usuario
        System.out.println("Ingrese los datos para el calculo de la integral:");
        System.out.print("  a = limite inferior del intervalo: ");
        double a = leer.nextDouble();

        System.out.print("  b = limite superior del intervalo: ");
        double b = leer.nextDouble();

        System.out.print("  n = cantidad de subintervalos: ");
        int n = leer.nextInt();

        System.out.println("\n--- Calculando con Regla del Trapecio Compuesta ---");

         // --- CÁLCULO DEL VALOR EXACTO (NUEVO) ---
        double I_exacto = valorExacto(a, b);
        System.out.printf("Valor Exacto (I_exacto) = %.16f%n", I_exacto);
        System.out.println("--------------------------------------------------\n");

        // 3) Calcular h
        double h = (b - a) / n;
        System.out.printf("\nh = (b - a) / n = (%.6f - %.6f) / %d = %.15f%n",
                b, a, n, h);
        System.out.println("  -> h es el tamano de cada subintervalo.\n");

        double[] x = new double[n + 1];
        double[] fx = new double[n + 1];

        // 4) Calcular nodos y valores de la funcion
        System.out.println("Tabla de nodos x_k y valores f(x_k):");
        System.out.println("  k\t     x_k\t\t\t\t\t       f(x_k)");
        System.out.println("  --------------------------------------------------------------");

        for (int k = 0; k <= n; k++) {
            x[k] = a + k * h;
            fx[k] = f(x[k]);
            System.out.printf("  %d\t(x_%d = %.15f)\t(f(x_%d) = %.16f)%n",
                    k, k, x[k], k, fx[k]);
        }

        System.out.println("\n  -> x_k son los nodos del intervalo [a, b].");
        System.out.println("  -> f(x_k) es el valor de la funcion en cada nodo.\n");

        // 5) Sumar puntos intermedios
        double S = 0.0;
        for (int k = 1; k <= n - 1; k++) {
            S += fx[k];
        }
        System.out.printf("S = suma de los valores f(x_k) para k = 1,...,%d-1%n", n);
        System.out.printf("S = %.16f%n", S);
        System.out.println("  -> S corresponde a los puntos interiores (sin incluir x_0 ni x_n).\n");

        // 6) Aplicar fórmula del trapecio compuesto y medir tiempo
        long startTime = System.nanoTime(); // INICIO de la medición de tiempo
        
        // I = h * [ f(x0)/2 + S + f(xn)/2 ]
        // Variable original 'I'
        double I = h * (0.5 * fx[0] + S + 0.5 * fx[n]); 
        
        long endTime = System.nanoTime();   // FIN de la medición de tiempo
        long tiempo_usuario = endTime - startTime;
        
        // Cálculo de errores (nuevas variables)
        double error_abs = calcularErrorAbsoluto(I_exacto, I);
        double error_rel = calcularErrorRelativo(I_exacto, I);
        int iteraciones_usuario = n - 1;


        System.out.println("Aplicando la formula del trapecio compuesto:");
        System.out.println("  I = h * [ f(x0)/2 + S + f(xn)/2 ]");
        System.out.printf("  I = %.15f * [ %.16f/2 + %.16f + %.16f/2 ]%n",
                h, fx[0], S, fx[n]);

        System.out.println("\n--------------------------------------------------------------");
        System.out.printf("VALOR APROXIMADO: I_aprox = %.16f%n", I); // Usando I
        System.out.printf("VALOR EXACTO: I_exacto = %.16f%n", I_exacto);
        System.out.printf("ERROR ABSOLUTO: %.16f (%.8e)%n", error_abs, error_abs);
        System.out.printf("ERROR RELATIVO: %.16f (%.8e)%n", error_rel, error_rel);
        System.out.printf("TIEMPO DE EJECUCIÓN: %d nanosegundos%n", tiempo_usuario);
        System.out.printf("NÚMERO DE ITERACIONES (Sumas): %d%n", iteraciones_usuario);
        System.out.println("--------------------------------------------------------------\n");

        // =========================================================================
        // SECCIÓN DE EXPERIMENTOS Y TABLA (Punto 4)
        // =========================================================================

        System.out.println(">>> INICIO DE EXPERIMENTOS PARA LA TABLA DE ANÁLISIS (Variando N) <<<");

        // Valores de n a utilizar para generar los datos de los gráficos
        int[] n_values = {10, 20, 40, 80, 160, 320}; 
        ArrayList<Resultado> resultados = new ArrayList<>();
        
        for (int n_exp : n_values) { // Usamos n_exp para el bucle y evitar conflicto con la 'n' original
            Resultado res = new Resultado();
            res.n = n_exp;
            res.h = (b - a) / n_exp;

            // Medición de Tiempo y Cálculo
            startTime = System.nanoTime();
            double I_aprox_exp = trapecioCompuesto(a, b, n_exp); // Llama a la función modular
            endTime = System.nanoTime();

            // Guardar resultados
            res.valorExacto = I_exacto;
            res.valorNumerico = I_aprox_exp;
            res.errorAbsoluto = calcularErrorAbsoluto(I_exacto, I_aprox_exp);
            res.errorRelativo = calcularErrorRelativo(I_exacto, I_aprox_exp);
            res.tiempoEjecucionNano = endTime - startTime;
            res.numIteraciones = n_exp - 1;
            
            resultados.add(res);
        }
        
        leer.close();

        // Generación de la Tabla de Valores (Punto 4.a)
        System.out.println("\n" + "=".repeat(160));
        System.out.println("                         TABLA DE VALORES PARA ANÁLISIS (Punto 4.a)");
        System.out.println("=".repeat(160));
        System.out.printf("%-10s | %-15s | %-25s | %-25s | %-20s | %-20s | %-20s | %-15s%n",
                          "N", "Paso h", "Resultado Numérico", "Valor Exacto", "Error Absoluto", "Error Relativo", "Tiempo (nanosegundos)", "Iteraciones");
        System.out.println("-".repeat(160));

        for (Resultado r : resultados) {
            System.out.printf("%-10d | %-15.10f | %-25.16f | %-25.16f | %-20.8e | %-20.8e | %-20d | %-15d%n",
                              r.n, r.h, r.valorNumerico, r.valorExacto, r.errorAbsoluto, r.errorRelativo, r.tiempoEjecucionNano, r.numIteraciones);
        }
        System.out.println("=".repeat(160));
        
        System.out.println("\n Fin!");
    }
}

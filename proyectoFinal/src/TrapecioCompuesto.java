import java.util.Locale;
import java.util.Scanner;

public class TrapecioCompuesto {

    // Función f(x) = x^3 − 6x^2 + 11x − 6
    static double f(double x) {
        return x*x*x - 6*x*x + 11*x - 6;
    }

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

        // 6) Aplicar formula del trapecio compuesto
        // I = h * [ f(x0)/2 + S + f(xn)/2 ]
        double I = h * (0.5 * fx[0] + S + 0.5 * fx[n]);

        System.out.println("Aplicando la formula del trapecio compuesto:");
        System.out.println("  I = h * [ f(x0)/2 + S + f(xn)/2 ]");
        System.out.printf("  I = %.15f * [ %.16f/2 + %.16f + %.16f/2 ]%n",
                h, fx[0], S, fx[n]);
        System.out.printf("%nValor aproximado de la integral:%n");
        System.out.printf("  I = %.16f%n", I);
        System.out.printf("  I ≈ %.8f%n", I);
        System.out.println("\n  -> I es la aproximacion de la integral de f(x)");
        System.out.println("     en el intervalo [a, b] usando la regla del trapecio compuesta.");

        leer.close();
    }
}

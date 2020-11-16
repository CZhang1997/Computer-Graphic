package io.github.czhang1997.assignment4.Q3_Bonus;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @Author: churongzhang
 * @Date: 10/24/20
 * @Time: 9:16 PM
 * @Info: Generate a data file for two tori (the plural form of torus) as shown in
 * Fig. 5.29.
 */
public class Tori {
    public static void main(String[] args) throws IOException {
        int n = 30;
        double R = 2.5;
        String fileName = "tori.dat";
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the value for n: ");
            n = scanner.nextInt();
            System.out.print("Enter the value for R: ");
            R = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter the filename to store these data: ");
            fileName = scanner.nextLine();

            if (n <= 2 || R < 2)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("n must be an integer > 2");
            System.out.println("R must be a real number >= 2");
            System.exit(1);
        }
        new ToriObj(n, R, fileName);
    }
}

class ToriObj {
    ToriObj(int n, double R, String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);

        double delta = 2 * Math.PI / n;
        for (int i = 0; i < n; i++) {
            double alpha = i * delta,
                    cosa = Math.cos(alpha), sina = Math.sin(alpha);
            for (int j = 0; j < n; j++) {
                double beta = j * delta, x = R + Math.cos(beta);
                float x1 = (float) (cosa * x),
                        y1 = (float) (sina * x),
                        z1 = (float) Math.sin(beta);
                fw.write(
                        (i * n + j + 1) + " " + x1 + " " + y1 + " " + z1 + "\r\n");
            }
        }
        // draw vertices for the second tori
        for (int i = 0; i < n; i++) {
            double alpha = i * delta,
                    cosa = Math.cos(alpha), sina = Math.sin(alpha);
            for (int j = 0; j < n; j++) {
                double beta = j * delta, x = R + Math.cos(beta);
                float x1 = (float) (cosa * x),
                        y1 = (float) (sina * x),
                        z1 = (float) Math.sin(beta);
                // rotate around x axis by 90 degree
                double degree = 90.0;
                float y2 = (float) (y1 * Math.cos(Math.toRadians(degree))
                        + z1 * Math.sin(Math.toRadians(degree)));
                float z2 = (float) (y1 * -Math.sin(Math.toRadians(degree))
                        + z1 * Math.cos(Math.toRadians(degree)));
                float xp = (float) (x1 + R);
                float yp = y2;
                float zp = z2;
                fw.write(
                        (i * n + j + 1 + n * n) + " " + xp + " " + yp + " " + zp + "\r\n");
            }
        }

        fw.write("Faces:\r\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int i1 = (i + 1) % n, j1 = (j + 1) % n,
                        a = i * n + j + 1, b = i1 * n + j + 1,
                        c = i1 * n + j1 + 1, d = i * n + j1 + 1;
                fw.write(a + " " + b + " " + c + " " + d + ".\r\n");
            }
        }
        // write the faces for the second tori
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int i1 = (i + 1) % n, j1 = (j + 1) % n,
                        a = i * n + j + 1 + n * n, b = i1 * n + j + 1 + n * n,
                        c = i1 * n + j1 + 1 + n * n, d = i * n + j1 + 1 + n * n;
                fw.write(a + " " + b + " " + c + " " + d + ".\r\n");
            }
        }
        System.out.println(fileName + " is ready");
        fw.close();
    }
}

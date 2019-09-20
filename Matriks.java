/* Nama     : Lionnarta Savirandy */
/* NIM      : 13518128 */
/* File     : ADT Matriks */

package MatriksLibrary;
import java.util.Scanner;

public class Matriks{
    /* Konstanta */
    static final int BrsMax = 100;
    static final int BrsMin = 1;
    static final int KolMax = 100;
    static final int KolMin = 1;

    /* Tipe Bentukan */
    public class matrix{
        public int NBrsEff;
        public int NkolEff;
        public double [][] Mat = new double [BrsMax+1][BrsMin+1];
    }

    /* Konstruktor */
    static void MakeMatriks(int NB, int NK, matrix M){
        M.NBrsEff = NB;
        M.NkolEff = NK;
    }

    /* Baca-tulis */
    static void BacaMatrisks(matrix M, int NB, int NK){
        // Kamus lokal
        int i,j;
        // ALgoritma
        Scanner key = new Scanner(System.in);
        MakeMatriks(NB, NK, M);
        for ( i = BrsMin; i <= NB; i++)
        {
            for ( j = KolMin; j <= NK; j++)
            {
                M.Mat[i][j] = key.nextInt();
            }
        }
    }
    static void TulisMatriks(matrix M){
        // Kamus lokal
        int i,j;
        // ALgoritma
        for ( i = BrsMin; i <= M.NBrsEff; i++)
        {
            for ( j = KolMin; j < M.NkolEff; j++)
            {
                System.out.print(String.format("%.2f ",M.Mat[i][j]));
            }
            System.out.println(String.format("%.2f",M.Mat[i][j]));
        }

    }
}
/* Nama     :   Michael Hans
                Lionnarta Savirandy */
/* NIM      :   13518056
                13518128 */
/* Tanggal  :   20 September 2019 */

import OpsMatriks.MatriksAdt;
import java.lang.Math; 
import java.util.Scanner;

public class MainMatriks{
    public static void main(String []args) {
        /* Kamus Lokal */
        MatriksAdt obj = new MatriksAdt();
        int i,j;
        MatriksAdt.MATRIKS M1 = new MatriksAdt.MATRIKS();
        MatriksAdt.MATRIKS M2 = new MatriksAdt.MATRIKS();
        MatriksAdt.MATRIKS M3 = new MatriksAdt.MATRIKS();
        MatriksAdt.MATRIKS M4 = new MatriksAdt.MATRIKS(); 
        /* Algoritma */
        Scanner input = new Scanner(System.in);
        System.out.println("Program Matriks OBE");
        System.out.println("Matriks 1 : ");
        obj.BacaMATRIKS(M1);
        System.out.println("Isi Matriks 1 :");
        obj.TulisMATRIKS(M1);
        System.out.println();
        System.out.println("Eliminasi Gauss");
        obj.EGauss(M1);
        obj.TulisMATRIKS(M1);
    }
}
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
        System.out.println("Program Invers Matriks");
        System.out.println("Matriks M1 :");
        obj.BacaMATRIKS(M1);
        System.out.println();
        System.out.println("Matriks Kanan :");
        obj.BacaMATRIKS(M3);
        System.out.println();
        System.out.println("Determinan M1 adalah "+obj.Determinan(M1));
        System.out.println();
        System.out.println("Matriks Kofaktor dari Matriks M1 adalah :");
        M2 = obj.MatriksKofaktor(M1);
        obj.TulisMATRIKS(M2);
        System.out.println();
        System.out.println();
        System.out.println("Adjoin dari Matriks M1 adalah :");
        M2 = obj.Adjoin(M1);
        obj.TulisMATRIKS(M2);
        System.out.println();
        System.out.println();
        if (obj.Determinan(M1)!=0){
            M2 = obj.Invers(M1);
            System.out.println("Invers dari Matriks M1 adalah :");
            obj.TulisMATRIKS(M2);
            System.out.println();
        }
        else {
            System.out.println("M1 tidak mempunyai invers");
        }
        System.out.println();
        System.out.println("Solusi persamaan Ax = B adalah ");
        M4 = obj.KaliMATRIKS(M2, M3);
        for (i=1; i<=M1.NBrsEff; i++){
            System.out.println("x"+i+" = "+String.format("%.4f", M4.Mem[i][1]));
        }
    }
}
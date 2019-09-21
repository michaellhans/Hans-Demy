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
        /* KAMUS UTAMA */
        MatriksAdt obj = new MatriksAdt();
        int op = 1;
        Scanner keyboard = new Scanner (System.in);
        /* AlGORITMA */
        System.out.println();
        System.out.println("IF2123 Aljabar Linear dan Geometri");
        System.out.println("Sistem Persamaan Linear, Determinan, dan Aplikasinya");
        System.out.println();
        System.out.println("Created by : Hans-Demy");
        System.out.println("13518056 - Michael Hans");
        System.out.println("13518119 - Lionnarta Savirandy");
        System.out.println("13518128 - Rafael Sean Putra");
        System.out.println();
        while ((op >= 1) & (op <= 7)){
            System.out.println("MENU PROGRAM");
            System.out.println("1. Sistem Persamaan Linier");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks Balikan");
            System.out.println("4. Matriks Kofaktor");
            System.out.println("5. Adjoin");
            System.out.println("6. Interpolasi Polinom");
            System.out.println("7. Keluar");
            System.out.print("Masukkan pilihan menu yang diinginkan : ");
            op = keyboard.nextInt();
            if ((op < 1) || (op > 7)){
                System.out.println("Input tidak valid, harap untuk mengulangi kembali");
                System.out.println();
            }
            else {
                System.out.println();
            }
            switch(op){
                case 1:
                    obj.SPL(); break;
                case 2:
                    obj.MainDeterminan(); break;
                case 3:
                    obj.MainInvers(); break;
                case 4:
                    obj.MainKofaktor(); break;
                case 5:
                    obj.MainAdjoin(); break;
                case 6:
                    obj.Interpolasi(); break;
                case 7:
                    obj.ExitProgram();
            }
        }
    }
}
/* NIM / Nama  :  13518056 / Michael Hans */
/* Tanggal     :  Rabu, 18 September 2019 */
/* Deskripsi   :  Program Pembalikkan Matriks */

import java.util.Scanner;
import java.lang.Math; 

public class MatriksBalikan {

   public static void main(String []args) {
      /* Kamus Lokal */
      int i,j;
      MATRIKS M1 = new MATRIKS();
      MATRIKS M2 = new MATRIKS();
      MATRIKS M3 = new MATRIKS();
      MATRIKS M4 = new MATRIKS(); 
      /* Algoritma */
      System.out.println("Program Invers Matriks");
      System.out.println("Matriks M1 :");
      BacaMATRIKS(M1);
      System.out.println();
      System.out.println("Matriks Kanan :");
      BacaMATRIKS(M3);
      System.out.println();
      System.out.println("Determinan M1 adalah "+Determinan(M1));
      System.out.println();
      System.out.println("Matriks Kofaktor dari Matriks M1 adalah :");
      M2 = MatriksKofaktor(M1);
      TulisMATRIKS(M2);
      System.out.println();
      System.out.println();
      System.out.println("Adjoin dari Matriks M1 adalah :");
      M2 = Adjoin(M1);
      TulisMATRIKS(M2);
      System.out.println();
      System.out.println();
      if (Determinan(M1)!=0){
        M2 = Invers(M1);
        System.out.println("Invers dari Matriks M1 adalah :");
        TulisMATRIKS(M2);
        System.out.println();
      }
      else {
        System.out.println("M1 tidak mempunyai invers");
      }
      System.out.println();
      System.out.println("Solusi persamaan Ax = B adalah ");
      M4 = KaliMATRIKS(M2, M3);
      for (i=1; i<=M1.NBrsEff; i++){
         System.out.println("x"+i+" = "+String.format("%.4f", M4.Mem[i][1]));
       }
   }

   static void MakeMATRIKS (int NB, int NK, MATRIKS M)
   /* Membentuk sebuah MATRIKS "kosong" yang siap diisi berukuran NB x NK di "ujung kiri" memori */
   /* I.S. NB dan NK adalah valid untuk memori matriks yang dibuat */
   /* F.S. Matriks M sesuai dengan definisi di atas terbentuk */
   {  /* Kamus Lokal */
	   /* Algoritma */
	   M.NBrsEff=NB;
	   M.NKolEff=NK;
   }

   static MATRIKS Minor(int baris, int kolom, MATRIKS M){
      /* Kamus Lokal */
      int i,j;
      int subi, subj;
      MATRIKS MHsl = new MATRIKS();
      /* Algoritma */
      MakeMATRIKS(M.NBrsEff-1,M.NKolEff-1,MHsl);
      subi = 1;
      for (i=1; i<=M.NBrsEff; i++){
        subj = 1;
        if (i != baris) {
            for (j=1; j<=M.NKolEff; j++){
              if ((i != baris) & (j != kolom)){
               MHsl.Mem[subi][subj] = M.Mem[i][j];
                subj++;
              }
            }
            subi++;
        }
      }
      return MHsl;
   }

   static MATRIKS MatriksKofaktor (MATRIKS M){
      /* Kamus Lokal */
      int i,j;
      MATRIKS MKof = new MATRIKS();
      /* Algoritma */
      MakeMATRIKS(M.NBrsEff, M.NKolEff, MKof);
      for (i=1; i<=M.NBrsEff; i++){
          for (j=1; j<=M.NKolEff; j++){
              MKof.Mem[i][j] = Math.pow(-1,i+j)*Determinan(Minor(i,j,M));
           }
      }
      return MKof;
   }

   static MATRIKS Adjoin (MATRIKS M){
      /* Kamus Lokal */
      MATRIKS MKof = new MATRIKS();
      MATRIKS MAdj = new MATRIKS();
      /* Algoritma */
      MakeMATRIKS(M.NBrsEff, M.NKolEff, MKof);
      MakeMATRIKS(M.NBrsEff, M.NKolEff, MAdj);
      MKof = MatriksKofaktor(M);
      CopyMATRIKS(MKof, MAdj);
      Transpose(MAdj);
      return MAdj;
   }

   static MATRIKS Invers (MATRIKS M){
      /* Kamus Lokal */
      int i,j;
      MATRIKS MKof = new MATRIKS();
      MATRIKS MAdj = new MATRIKS();
      MATRIKS MInv = new MATRIKS();
      /* Algoritma */
      MakeMATRIKS(M.NBrsEff, M.NKolEff, MAdj);
      MakeMATRIKS(M.NBrsEff, M.NKolEff, MInv);
      MAdj = Adjoin(M);
      for (i=1; i<=M.NBrsEff; i++){
         for (j=1; j<=M.NKolEff; j++){
             MInv.Mem[i][j] = MAdj.Mem[i][j]/Determinan(M);
          }
      }
      return MInv;
   }

   static void BacaMATRIKS(MATRIKS M){
      /* Kamus Lokal */
      int i,j,NB,NK;
      Scanner keyboard = new Scanner(System.in);
      /* Algoritma */
      System.out.print("Masukkan ukuran matriks NxN : ");
      NB = keyboard.nextInt();
      NK = keyboard.nextInt();
      for (i=1; i<=NB; i++){
         for (j=1; j<=NK; j++){
            M.Mem[i][j]= keyboard.nextInt();
         }
      }
      MakeMATRIKS(NB, NK, M);
   }

   static void TulisMATRIKS(MATRIKS M){
      /* Kamus Lokal */
      int i,j;
      /* Algoritma */
      for (i=1; i<=M.NBrsEff; i++){
         for (j=1; j<=M.NKolEff; j++){
            if (M.Mem[i][j]==-0.0){
                M.Mem[i][j]= -0.0 + 0.0;
            }
            if (j == M.NKolEff){
               System.out.print(String.format("%.2f", M.Mem[i][j]));
            }
            else {
               System.out.print(String.format("%.2f", M.Mem[i][j])+" ");
            }
         }
         if (i<M.NBrsEff){
            System.out.println();
         }
      }
   }
   
   static MATRIKS KaliMATRIKS (MATRIKS M1, MATRIKS M2)
   /* Prekondisi : Ukuran kolom efektif M1 = ukuran baris efektif M2 */
   /* Mengirim hasil perkalian matriks: salinan M1 * M2 */
   {	/* Kamus Lokal */
      int i,j,k;
      double sum;
      MATRIKS M3 = new MATRIKS();
      /* Algoritma */
      MakeMATRIKS(M1.NBrsEff, M2.NKolEff, M3);
      for (i=1; i<=M3.NBrsEff;i++){
         for (j=1; j<=M3.NKolEff;j++){
            sum = 0;
            for (k=1;k<=M1.NKolEff;k++){
               sum += M1.Mem[i][k]*M2.Mem[k][j];
            }
            M3.Mem[i][j]=sum;
         }
      }
      return M3;
   }

   static void Transpose (MATRIKS M)
   /* I.S. M terdefinisi dan IsBujursangkar(M) */
   /* F.S. M "di-transpose", yaitu setiap elemen M(i,j) ditukar nilainya dengan elemen M(j,i) */
   {	/* Kamus Lokal */
      MATRIKS M3 = new MATRIKS();
      int i,j;
      /* Algoritma */
      CopyMATRIKS(M,M3);
      MakeMATRIKS(M.NKolEff,M.NBrsEff,M);
      for (i=1; i<=M.NBrsEff;i++){
         for (j=1; j<=M.NKolEff;j++){
            M.Mem[i][j]=M3.Mem[j][i];
         }
      }
   }
   
   static void CopyMATRIKS (MATRIKS MIn, MATRIKS MHsl)
   /* Melakukan assignment MHsl  MIn */
   {	/* Kamus Lokal */
      int i,j;
      /* Algoritma */
      for (i=1; i<=MIn.NBrsEff;i++){
         for (j=1; j<=MIn.NKolEff;j++){
            MHsl.Mem[i][j]=MIn.Mem[i][j];
         }
      }
   }

   static double Determinan (MATRIKS M)
   /* Prekondisi: IsBujurSangkar(M) */
   /* Menghitung nilai determinan M */
   {	/* Kamus Lokal */
      int x,i,j;
      MATRIKS sub = new MATRIKS();
      double det = 0;
      /* Algoritma */
      if ((M.NBrsEff==1) && (M.NKolEff==1)){
         det = M.Mem[1][1];
      }
      else if ((M.NBrsEff==2) && (M.NKolEff==2)){
         det = M.Mem[1][1]*M.Mem[2][2]-M.Mem[2][1]*M.Mem[1][2];
      }
      else {
         MakeMATRIKS(M.NBrsEff-1,M.NKolEff-1,sub);
         for (x=1; x<=M.NKolEff;x++){
            int subi = 1;
            for (i=1+1; i<=M.NBrsEff;i++){
               int subj = 1;
               for (j=1; j<=M.NKolEff;j++){
                  if (j!=x){
                     sub.Mem[subi][subj]=M.Mem[i][j];
                     subj++;
                  }
               }
               subi++;
            }
            det = det + (Math.pow(-1,1+x) * M.Mem[1][x] * Determinan(sub));
         }
      }
      return det;
   }
}

class MATRIKS
   {
      public double [][] Mem = new double[100][100]; 
      public int NBrsEff;
      public int NKolEff; 
   }
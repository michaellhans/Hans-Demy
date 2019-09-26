/* Nama     :   Michael Hans
                Rafael Sean Putra
                Lionnarta Savirandy */
/* NIM      :   13518056
                13518119
                13518128 */
/* Tanggal  :   20 September 2019 */

package OpsMatriks;
import java.lang.Math; 
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class MatriksAdt{
    /* Tipe data matriks */
    public static class MATRIKS{
        public double [][] Mem = new double[100][100]; 
        public int NBrsEff;
        public int NKolEff; 
    }

    public static class POINT{
       public double x;
       public double y;
    }
    /* Konstruktor */
    public static void MakeMATRIKS (int NB, int NK, MATRIKS M)
   /* Membentuk sebuah MATRIKS "kosong" yang siap diisi berukuran NB x NK di "ujung kiri" memori */
   /* I.S. NB dan NK adalah valid untuk memori matriks yang dibuat */
   /* F.S. Matriks M sesuai dengan definisi di atas terbentuk */
   {  /* Kamus Lokal */
	   /* Algoritma */
	   M.NBrsEff=NB;
	   M.NKolEff=NK;
   }

   public static MATRIKS Identity (int N)
   /* Membentuk sebuah matriks identitas berukuran n x n */ 
   {  /* Kamus Lokal */
      int i,j;
      MATRIKS I = new MATRIKS();
      /* Algoritma */
      MakeMATRIKS(N,N,I);
      for (i=1; i<=N; i++){
         for(j=1; j<=N; j++){
            if (i==j){
               I.Mem[i][j]=1;
            }
            else {
               I.Mem[i][j]=0;
            }
         }
      }
      return I;
   }

   public static MATRIKS MCramer (int kol, MATRIKS M, MATRIKS MK)
   /* Membentuk sebuah matriks cramer dari matriks M dengan kolom ke-kol digantikan oleh matriks konstanta MK */
   {  /* Kamus Lokal */
      MATRIKS MC = new MATRIKS();
      int i;
      /* Algoritma */
      MakeMATRIKS(M.NBrsEff, M.NKolEff, MC);
      CopyMATRIKS(M, MC);
      for (i=1; i<=MC.NBrsEff; i++){
         MC.Mem[i][kol] = MK.Mem[i][1];
      }
      return MC;
   }

   public static void MetodeCramer (MATRIKS MAug)
   /* Melakukan operasi penyelesaian SPL dengan Metode Cramer dengan masukan berupa Matriks Augmented */
   {  /* Kamus Lokal */
      double[] Hasil = new double[100];
      MATRIKS M = new MATRIKS();
      MATRIKS MK = new MATRIKS();
      int i;
      /* Algoritma */
      GetMATRIKS(MAug, M);
      GetKONSTANTA(MAug, MK);
      System.out.println("Matriks A = ");
      TulisMATRIKS(M); System.out.println();
      System.out.println("Determinan Matriks A adalah "+Determinan(M)); System.out.println();
      if (Determinan(M)==0){
         System.out.println("Karena determinan A = 0, maka SPL tidak mempunyai solusi");
      }
      else {
         for (i = 1; i<=M.NKolEff; i++){
            System.out.println("Matriks A"+i+" = ");
            TulisMATRIKS(MCramer(i,M,MK)); System.out.println();
            System.out.println("Determinan Matriks A"+i+" adalah "+Determinan(MCramer(i,M,MK)));
            Hasil[i] = Determinan(MCramer(i,M,MK))/Determinan(M);
            System.out.println("x"+i+" = "+String.format("%.4f / %.4f = %.4f",Determinan(MCramer(i,M,MK)),Determinan(M),Hasil[i]));
            System.out.println();
         }
      } 
   }

   public static MATRIKS Minor(int baris, int kolom, MATRIKS M){
      /* Menghasilkan minor (baris,kolom) dari matriks M */
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

   public static MATRIKS MatriksKofaktor (MATRIKS M){
      /* Menghasilkan suatu matriks kofaktor dari matriks M */
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

   public static MATRIKS Adjoin (MATRIKS M){
      /* Menghasilkan Matriks Adjoin dari Matriks M */
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

   public static MATRIKS Invers (MATRIKS M){
      /* Menghasilkan Invers atau Balikan dari Matriks M */
      /* Kamus Lokal */
      int i,j;
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

   public static void BacaMATRIKS(MATRIKS M){
      /* Membaca sebuah matriks M mulai dari ukuran hingga komponen-komponennya */
      /* Kamus Lokal */
      int i,j,N;
      Scanner keyboard = new Scanner(System.in);
      /* Algoritma */
      System.out.print("Masukkan nilai N untuk ukuran matriks NxN : ");
      N = keyboard.nextInt();
      for (i=1; i<=N; i++){
         for (j=1; j<=N; j++){
            M.Mem[i][j]= keyboard.nextDouble();
         }
      }
      MakeMATRIKS(N, N, M);
   }

   public static void BacaAUG(MATRIKS MAug){
      /* Membaca sebuah (SPL) dalam bentuk Matriks Augmented */
      /* Kamus Lokal */
      int i,j,m,n;
      Scanner keyboard = new Scanner(System.in);
      /* Algoritma */
      System.out.print("Masukkan ukuran matriks augmented MxN : ");
      m = keyboard.nextInt();
      n = keyboard.nextInt();
      for (i=1; i<=m; i++){
         for (j=1; j<=n; j++){
            MAug.Mem[i][j]= keyboard.nextDouble();
         }
      }
      MakeMATRIKS(m, n, MAug);
   }

   public static void TulisMATRIKS(MATRIKS M){
      /* Menuliskan matriks ke layar */
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
   
   public static MATRIKS KaliMATRIKS (MATRIKS M1, MATRIKS M2)
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

   public static void Transpose (MATRIKS M)
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

   public static void GetMATRIKS (MATRIKS MAug, MATRIKS M)
   /* Membentuk matriks M berukuran NxN yang berisikan bagian matriks dari MAug */
   {	/* Kamus Lokal */
      int i,j;
      /* Algoritma */
      MakeMATRIKS(MAug.NBrsEff, MAug.NKolEff-1, M);
      for (i=1; i<=M.NBrsEff;i++){
         for (j=1; j<=M.NKolEff;j++){
            M.Mem[i][j]=MAug.Mem[i][j];
         }
      }
   }

   public static void GetKONSTANTA (MATRIKS MAug, MATRIKS MK)
   /* Membentuk matriks MK berukuran Nx1 yang berisikan bagian konstanta dari MAug */
   {	/* Kamus Lokal */
      int i,j;
      /* Algoritma */
      MakeMATRIKS(MAug.NBrsEff, 1, MK);
      for (i=1; i<=MAug.NBrsEff;i++){
         MK.Mem[i][1]=MAug.Mem[i][MAug.NKolEff];
      }
   }

   public static void CopyMATRIKS (MATRIKS MIn, MATRIKS MHsl)
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

   public static double Determinan (MATRIKS M)
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

   public static void SPL()
    {  /* Kamus Lokal */
        MATRIKS MAug = new MATRIKS();
        int op; int choose;
        Scanner keyboard = new Scanner (System.in);
        /* Algoritma */
        System.out.println("Terdapat 3 metode pengisian matriks");
        System.out.println("1. Baca dari input keyboard");
        System.out.println("2. Baca dari suatu file .txt");
        System.out.println("3. Baca dari matriks Hilbert");
        System.out.println("Pilih metode pengisian matriks yang diinginkan");
        choose = keyboard.nextInt();
        while ((choose < 1) || (choose > 3)){
            System.out.println("Input Tidak Valid! Harap diulangi");
            System.out.println("1. Baca dari input keyboard");
            System.out.println("2. Baca dari suatu file .txt");
            System.out.println("3. Baca dari matriks Hilbert");
            System.out.println("Pilih metode pengisian matriks yang diinginkan");
            choose = keyboard.nextInt();
        }
        if (choose == 1){
           BacaAUG(MAug);
        }
        else if (choose == 2){
           bacaFile(MAug);
        }
        else if (choose == 3){
           BacaHilbert(MAug);
        }
        System.out.println("Terdapat 4 metode untuk menyelesaikan Sistem Persamaan Linier");
        System.out.println("1. Metode eliminasi Gauss");
        System.out.println("2. Metode eliminasi Gauss Jordan");
        System.out.println("3. Metode matriks balikan");
        System.out.println("4. Kaidah Cramer");
        System.out.print("Pilihlah metode pengerjaan yang diinginkan : ");
        op = keyboard.nextInt();
        while ((op < 1) || (op > 4)){
            System.out.println("Input tidak valid, silahkan diulangi kembali");
            System.out.println("1. Metode eliminasi Gauss");
            System.out.println("2. Metode eliminasi Gauss Jordan");
            System.out.println("3. Metode matriks balikan");
            System.out.println("4. Kaidah Cramer");
            System.out.print("Pilihlah metode pengerjaan yang diinginkan : ");
            op = keyboard.nextInt();
        }
        switch(op){
            case 1:
                Gauss(MAug); 
                BackwardSub(MAug); break;
            case 2:
                GaussJordan(MAug); 
                BackwardSub(MAug); break;    
            case 3:
               if (MAug.NBrsEff == MAug.NKolEff-1)   
                  InverseMethod(MAug);
               else {
                  System.out.println("Metode Matriks Balikan tidak bisa dilakukan karena ukuran Matriks Koefisien Bukan Bujur Sangkar");
               } 
               break;
            case 4:
               if (MAug.NBrsEff == MAug.NKolEff-1)   
                  MetodeCramer(MAug);
               else {
                  System.out.println("Kaidah Cramer tidak bisa dilakukan karena ukuran Matriks Koefisien Bukan Bujur Sangkar");
               }
               break;
        }
        System.out.println();
    }

   public static void MainDeterminan()
    /* Prosedur untuk Menerima Matriks M berukuran NxN kemudian menampilkan nilai determinan dari M */
    {  /* Kamus Lokal */
        MATRIKS M = new MATRIKS();
        int op; int choose;
        Scanner keyboard = new Scanner (System.in);
        /* Algoritma */
        System.out.println("Terdapat 2 metode pengisian matriks");
        System.out.println("1. Baca dari input keyboard");
        System.out.println("2. Baca dari suatu file .txt");
        System.out.println("Pilih metode pengisian matriks yang diinginkan");
        choose = keyboard.nextInt();
        while ((choose < 1) || (choose > 2)){
            System.out.println("Input Tidak Valid! Harap diulangi");
            System.out.println("1. Baca dari input keyboard");
            System.out.println("2. Baca dari suatu file .txt");
            System.out.println("Pilih metode pengisian matriks yang diinginkan");
            choose = keyboard.nextInt();
        }
        if (choose == 1){
           BacaMATRIKS(M);
        }
        else if (choose == 2){
           bacaFile(M);
        }
        System.out.println("Terdapat 3 metode untuk menentukan determinan Matriks dari A");
        System.out.println("1. Metode matriks segitiga atas");
        System.out.println("2. Metode matriks segitiga bawah");
        System.out.println("3. Metode kofaktor");
        System.out.print("Pilihlah metode pengerjaan yang diinginkan : ");
        op = keyboard.nextInt();
        while ((op < 1) || (op > 3)){
            System.out.println("Input tidak valid, silahkan diulangi kembali");
            System.out.println("1. Metode matriks segitiga atas");
            System.out.println("2. Metode matriks segitiga bawah");
            System.out.println("3. Metode kofaktor");
            System.out.print("Pilihlah metode pengerjaan yang diinginkan : ");
            op = keyboard.nextInt();
        }
        switch(op){
            case 1:
                HighTriangle(M); break;
            case 2:
                LowTriangle(M); break;    
            case 3:
                MetodeKofaktor(M); break;
        }
        System.out.println();
    }

   public static void MainInvers()
    /* Prosedur untuk Menerima Matriks M berukuran NxN kemudian menampilkan Matriks Kofaktor dari M */
    {  /* Kamus Lokal */
        MATRIKS M = new MATRIKS();
        MATRIKS MAug = new MATRIKS();
        MATRIKS MInv = new MATRIKS();
        MATRIKS I = new MATRIKS();
        int op; int choose;
        Scanner keyboard = new Scanner (System.in);
        /* Algoritma */
        System.out.println("Terdapat 2 metode pengisian matriks");
        System.out.println("1. Baca dari input keyboard");
        System.out.println("2. Baca dari suatu file .txt");
        System.out.println("Pilih metode pengisian matriks yang diinginkan");
        choose = keyboard.nextInt();
        while ((choose < 1) || (choose > 2)){
            System.out.println("Input Tidak Valid! Harap diulangi");
            System.out.println("1. Baca dari input keyboard");
            System.out.println("2. Baca dari suatu file .txt");
            System.out.println("Pilih metode pengisian matriks yang diinginkan");
            choose = keyboard.nextInt();
        }
        if (choose == 1){
           BacaMATRIKS(M);
        }
        else if (choose == 2){
           bacaFile(M);
        }
        System.out.println("Terdapat 2 metode untuk menentukan Invers Matriks dari A");
        System.out.println("1. Metode eliminasi Gauss Jordan");
        System.out.println("2. Metode adjoin dan determinan");
        System.out.print("Pilihlah metode pengerjaan yang diinginkan : ");
        op = keyboard.nextInt();
        while ((op < 1) || (op > 2)){
            System.out.println("Input tidak valid, silahkan diulangi kembali");
            System.out.println("1. Metode eliminasi Gauss Jordan");
            System.out.println("2. Metode adjoin dan determinan");
            System.out.print("Pilihlah metode pengerjaan yang diinginkan : ");
            op = keyboard.nextInt();
        }
        switch(op){
            case 1:
               if (Determinan(M) == 0) {
                  System.out.println();
                  System.out.println("Matriks tidak memiliki matriks balikan");
                  System.out.println();
                  System.out.println();
                  break;
               }
               else {
                  I = Identity(M.NBrsEff);
                  MAug = GabungMATRIKS(M,I);
                  InversGJ(MAug);
                  MInv = GetINVERSE(MAug); 
                  System.out.println("Invers dari M adalah ");
                  TulisMATRIKS(MInv);
                  System.out.println();
                  System.out.println();
                  break;
               }
            case 2:
               System.out.println();
               System.out.println("Matriks Kofaktor dari M adalah ");
               TulisMATRIKS(MatriksKofaktor(M)); System.out.println(); System.out.println();
               System.out.println("Adjoin dari M adalah ");
               TulisMATRIKS(Adjoin(M)); System.out.println(); System.out.println();
               System.out.println("Determinan dari M adalah "+Determinan(M));  
               if (Determinan(M)== 0){
                  System.out.println();
                  System.out.println("Matriks M tidak mempunyai invers");
                  System.out.println();
                  System.out.println();
               }
               else {
                  MInv = Invers(M);
                  System.out.println("Invers dari M adalah ");
                  TulisMATRIKS(MInv);
                  System.out.println();
                  System.out.println();
               }   
               break;
        }
    }

   public static void MainKofaktor()
    /* Prosedur untuk Menerima Matriks M berukuran NxN kemudian menampilkan Matriks Kofaktor dari M */
    {  /* Kamus Lokal */
        MatriksAdt.MATRIKS M = new MatriksAdt.MATRIKS();
        MatriksAdt.MATRIKS MKof = new MatriksAdt.MATRIKS();
        Scanner keyboard = new Scanner (System.in);
        int choose;
        /* Algoritma */
        System.out.println("Terdapat 2 metode pengisian matriks");
        System.out.println("1. Baca dari input keyboard");
        System.out.println("2. Baca dari suatu file .txt");
        System.out.println("Pilih metode pengisian matriks yang diinginkan");
        choose = keyboard.nextInt();
        while ((choose < 1) || (choose > 2)){
            System.out.println("Input Tidak Valid! Harap diulangi");
            System.out.println("1. Baca dari input keyboard");
            System.out.println("2. Baca dari suatu file .txt");
            System.out.println("Pilih metode pengisian matriks yang diinginkan");
            choose = keyboard.nextInt();
        }
        if (choose == 1){
           BacaMATRIKS(M);
        }
        else if (choose == 2){
           bacaFile(M);
        }
        MKof = MatriksKofaktor(M);
        System.out.println("Matriks Kofaktor dari M adalah ");
        TulisMATRIKS(MKof);
        System.out.println();
        System.out.println();
    }

   public static void MainAdjoin()
    /* Prosedur untuk Menerima Matriks M berukuran NxN kemudian menampilkan Adjoin dari M */
    {  /* Kamus Lokal */
        MATRIKS M = new MATRIKS();
        MATRIKS MAdj = new MATRIKS();
        int choose;
        Scanner keyboard = new Scanner (System.in);
        /* Algoritma */
        System.out.println("Terdapat 2 metode pengisian matriks");
        System.out.println("1. Baca dari input keyboard");
        System.out.println("2. Baca dari suatu file .txt");
        System.out.println("Pilih metode pengisian matriks yang diinginkan");
        choose = keyboard.nextInt();
        while ((choose < 1) || (choose > 2)){
            System.out.println("Input Tidak Valid! Harap diulangi");
            System.out.println("1. Baca dari input keyboard");
            System.out.println("2. Baca dari suatu file .txt");
            System.out.println("Pilih metode pengisian matriks yang diinginkan");
            choose = keyboard.nextInt();
        }
        if (choose == 1){
           BacaMATRIKS(M);
        }
        else if (choose == 2){
           bacaFile(M);
        }
        MAdj = Adjoin(M);
        System.out.println("Adjoin dari M adalah ");
        TulisMATRIKS(MAdj);
        System.out.println();
        System.out.println();
    }

   public static void Interpolasi()
   /* Program membaca sebuah bilangan N, kemudian program membaca sebanyak N titik */
   {  /* Kamus Lokal */
      double[] Hasil = new double[100];
      MATRIKS MPoint = new MATRIKS();
      MATRIKS MAug = new MATRIKS();
      int i,j,N,op,choose;
      Scanner keyboard = new Scanner (System.in);
      /* Algoritma */
      System.out.println("Terdapat 2 metode pengisian matriks");
      System.out.println("1. Baca dari input keyboard");
      System.out.println("2. Baca dari suatu file .txt");
      System.out.println("Pilih metode pengisian matriks yang diinginkan");
      choose = keyboard.nextInt();
      while ((choose < 1) || (choose > 2)){
         System.out.println("Input Tidak Valid! Harap diulangi");
         System.out.println("1. Baca dari input keyboard");
         System.out.println("2. Baca dari suatu file .txt");
         System.out.println("Pilih metode pengisian matriks yang diinginkan");
         choose = keyboard.nextInt();
      }
      if (choose == 2){
         bacaFile(MPoint);
      }
      else if (choose == 1){
         System.out.print("Masukkan jumlah titik yang diinginkan = ");
         N = keyboard.nextInt();
         MakeMATRIKS(N,2,MPoint);
         for (i=1; i<=N; i++){
            MPoint.Mem[i][1] = keyboard.nextDouble();
            MPoint.Mem[i][2] = keyboard.nextDouble();
         }
      }
      N = MPoint.NBrsEff;
      MakeMATRIKS(N,N+1,MAug);
      for (i=1; i<=N; i++){
         for (j=1; j<=N+1; j++){
            if (j==N+1){
               MAug.Mem[i][j]=MPoint.Mem[i][2];
            }
            else if (j==N){
               MAug.Mem[i][j]=1;
            }
            else {
               MAug.Mem[i][j]=Math.pow(MPoint.Mem[i][1],(N-j));
            }
         }
      }
      System.out.println("Matriks Augmented yang terbentuk adalah ");
      TulisMATRIKS(MAug); System.out.println(); System.out.println();
      System.out.println("Terdapat 4 metode untuk menyelesaikan Sistem Persamaan Linier");
      System.out.println("1. Metode eliminasi Gauss");
      System.out.println("2. Metode eliminasi Gauss Jordan");
      System.out.println("3. Metode matriks balikan");
      System.out.println("4. Kaidah Cramer");
      System.out.print("Pilihlah metode pengerjaan yang diinginkan : ");
      op = keyboard.nextInt();
      while ((op < 1) || (op > 4)){
         System.out.println("Input tidak valid, silahkan diulangi kembali");
         System.out.println("1. Metode eliminasi Gauss");
         System.out.println("2. Metode eliminasi Gauss Jordan");
         System.out.println("3. Metode matriks balikan");
         System.out.println("4. Kaidah Cramer");
         System.out.print("Pilihlah metode pengerjaan yang diinginkan : ");
         op = keyboard.nextInt();
      }
      switch(op){
         case 1:
             Gauss(MAug);
             BackwardSubInterpolasi(MAug,Hasil);
             TulisPolinom(Hasil,N); break;
         case 2:
             GaussJordan(MAug);
             BackwardSubInterpolasi(MAug,Hasil);
             TulisPolinom(Hasil,N); break;    
         case 3:  
             InverseMethodInterpolasi(MAug,Hasil);
             TulisPolinom(Hasil,N); break;
         case 4:
             MetodeCramerInterpolasi(MAug,Hasil);
             TulisPolinom(Hasil,N); break;
        }
      System.out.println();
   }
   
   public static void ExitProgram()
    /* Exit dari Program Utama */
    {   /* Kamus Lokal */
        /* Algoritma */
        System.exit(1);
    }

    
    public static void InverseMethod(MATRIKS MAug)
    /* Melakukan prosedur untuk mencari solusi SPL dengan metode matriks balikan dari matriks augmented MAug */
    { /* Kamus Lokal */
      MATRIKS M = new MATRIKS();
      MATRIKS MK = new MATRIKS();
      MATRIKS MSol = new MATRIKS();
      int i,j;
      int N = MAug.NBrsEff;
      /* Algoritma */
      GetMATRIKS(MAug, M);
      GetKONSTANTA(MAug, MK);
      System.out.println(); 
      System.out.println("Matriks A = ");
      TulisMATRIKS(M); System.out.println();
      System.out.println();
      if (Determinan(M)==0){
         System.out.println("SPL tidak mempunyai solusi");  
      }
      else {
         System.out.println("Matriks Balikan A = ");
         TulisMATRIKS(Invers(M)); System.out.println();
         System.out.println();  
         System.out.println("Matriks Konstanta = ");
         TulisMATRIKS(MK); System.out.println();
         System.out.println(); 
         MakeMATRIKS(N,1,MSol);
         MSol = KaliMATRIKS(Invers(M),MK);
         System.out.println("Matriks Solusi SPL = ");
         for (i=1; i<=N; i++){
            System.out.println("x"+i+" = "+MSol.Mem[i][1]);
         }
      } 
      
      System.out.println(); 
    }

    public static void HighTriangle(MATRIKS M){
        /* Kamus Lokal */
        int i,j,k,l,m;
        double x,y,z;
        /* Algoritma */
        TulisMATRIKS(M);
        System.out.println(String.format("\n"));
        l = 1;
        for(i = 1; i <= M.NKolEff; i++){
            if(!LeadOne(M,l,i)){
                for(j = l; j <= M.NBrsEff; j++){
                    if(M.Mem[j][i] != 0){
                        x = M.Mem[j][i];
                        for(k = j+1; k <= M.NBrsEff; k++){
                            if(M.Mem[k][i] != 0){
                                y = M.Mem[k][i];
                                for(m = i; m <= M.NKolEff; m++){
                                    M.Mem[k][m] -= y/x*M.Mem[j][m];
                                }
                            }
                        }
                    }
                }
            l++;
            }
        }
        for(i = M.NBrsEff; i > 1; i--){
            if(Idx(M,i) < Idx(M,i-1)){
                SwapBaris(M,i,i-1);
            }
        }
        z = 1;
        for(i = 1; i <= M.NBrsEff; i++){
            z = z*M.Mem[i][i];
        }
        TulisMATRIKS(M);
        System.out.println();
        System.out.println();
        System.out.println(String.format("Determinannya adalah %.2f",z));
    }

    public static void LowTriangle(MATRIKS M){
       /* KAMUS LOKAL */
       int i, j, n, k;
       double y,det;

       /* ALGORITMA */
       for (i = M.NBrsEff; i > 1; i --) {
          for (n = i - 1; n >= 1; n--) {  
            if ( M.Mem[n][i] != 0 ) {
               y = M.Mem[n][i];
               for (k = 1; k <= M.NKolEff; k++) {
                  M.Mem[n][k] = M.Mem[n][k] - ( (M.Mem[i][k] / M.Mem[i][i]) * y);
               }
            }
          }
       }
       det = 1;
       for (i = 1; i <= M.NBrsEff ; i ++) {
          det = det * M.Mem[i][i];
       }
       TulisMATRIKS(M);
       System.out.println();
       System.out.println();
       System.out.println("Determinan: " + det);
    }

    public static MATRIKS GetINVERSE(MATRIKS MAug)
    /* Menghasilkan matriks yang merupakan gabungan antara matriks M dan matriks I yang berukuran sama */
    { /* Kamus Lokal */
      MATRIKS MInv = new MATRIKS();
      int i,j;
      int N = MAug.NBrsEff;
      /* Algoritma */
      MakeMATRIKS(N, N, MInv);
      for (i=1; i<=N; i++){
         for (j=1; j<=N; j++){
            MInv.Mem[i][j] = MAug.Mem[i][j+N];
         }
      }
      return MInv;
    }

    public static MATRIKS GabungMATRIKS(MATRIKS M, MATRIKS I)
    /* Menghasilkan matriks yang merupakan gabungan antara matriks M dan matriks I yang berukuran sama */
    { /* Kamus Lokal */
      MATRIKS MAug = new MATRIKS();
      int i,j;
      int N = M.NBrsEff;
      /* Algoritma */
      CopyMATRIKS(M, MAug);
      MakeMATRIKS(N, 2*N, MAug);
      for (i=1; i<=N; i++){
         for (j=N+1; j<=2*N; j++){
            MAug.Mem[i][j] = I.Mem[i][j-N];
         }
      }
      return MAug;
    }

    public static void SwapBaris (MATRIKS M, int r1, int r2){
        /* Kamus Lokal */
        MATRIKS temp = new MATRIKS();
        int i,j;
        /* ALgoritma */
        CopyMATRIKS(M, temp);
        for(i = 1; i <= M.NKolEff; i++){
            M.Mem[r1][i] = temp.Mem[r2][i];
            M.Mem[r2][i] = temp.Mem[r1][i];
        }
    }

    public static int Idx (MATRIKS M, int NB){
        /* Kamus lokal */
        int j,x;
        boolean b;
        /* ALgoitma */
        x = 0;
        b = false;
        j = 1;
        while(j <= M.NKolEff && b == false){
            if(M.Mem[NB][j] != 0){
                x = j;
                b = true;
            }
            j++;
        }
        if(x == 0){
            x = M.NKolEff + 1;
        }
        return x;
    }

    public static boolean LeadOne (MATRIKS M, int NB, int NK){
        /* Kamus lokal */
        int c,i;
        /* Algoritma */
        c = 0;
        for(i = NB; i <= M.NBrsEff; i++){
            if(M.Mem[i][NK] != 0){
                c++;
            }
        }
        return(c == 1 || c == 0);
    }
   
   public static void BagiBaris (MATRIKS M, int NB){
      /* Kamus lokal */
      int i,j;
      double x;
      /* Algoritma */
      j = 1;
      while(M.Mem[NB][j] == 0 && j <= M.NKolEff){
         j++;
      }
      x = M.Mem[NB][j];
      for (i = j; i <= M.NKolEff; i++){
         M.Mem[NB][i] = M.Mem[NB][i]/x;
      }
   }

   public static void SwapAllBaris (MATRIKS M){
      /* Kamus lokal */
      int i;
      /* Algoritma */
      for(i = M.NBrsEff; i > 1; i--){
         if(Idx(M,i) < Idx(M,i-1)){
               SwapBaris(M,i,i-1);
         }
      }
   }

   public static void KurangOBE (MATRIKS M, int NB){
      /* sudah di swap */
      int i,j,k;
      double x;
      /* algoritma */
      i = 1;
      k = NB;
      
      while(LeadOne(M,k,i) && i <= M.NKolEff){
         if(M.Mem[k][i] == 0){
            i++;
         }
         else{
            BagiBaris(M,k);
            k++;
         }
      }
      BagiBaris(M,k);
      
      for(j = k+1; j <= M.NBrsEff ; j++){
         if(M.Mem[j][i] != 0){
            x = M.Mem[j][i];
            for(k = i; k <= M.NKolEff; k++){
               M.Mem[j][k] = M.Mem[j][k] - x/M.Mem[NB][i]*M.Mem[NB][k];
            }
            BagiBaris(M,j);
         }
      }
   }

   public static void Gauss (MATRIKS M){
      /* Kamus Lokal */
      int i;
      /* Algoritma */
      
      for(i = 1; i <= M.NBrsEff; i++){
         SwapAllBaris(M);
         KurangOBE(M,i);
      }
      TulisMATRIKS(M);
      System.out.println();
      System.out.println();
   }
        

    public static void GaussJordan (MATRIKS M) {
    /* Melakukan prosedur mengubah matriks augmented dengan Eliminasi Gauss Jordan */
       /* KAMUS LOKAL */
         int i, j, k, n, x;
         double y;
         boolean status;

       /* ALGORITMA */
       Gauss(M);
       for (i = 2; i <= M.NBrsEff; i++ ) {
          j = 1;
          status = false;
          x = 0;
          while (j <= M.NKolEff  &&  status == false) {
             if (M.Mem[i][j] != 0) {
                x = j;
                status = true;
             }
             j ++ ;
          }

          if (status) {
             for (n = i - 1; n >= 1; n--) {
                if (M.Mem[n][x] != 0) {
                   y = M.Mem[n][x];
                   for (k = 1; k <= M.NKolEff; k++) {
                      M.Mem[n][k] = M.Mem[n][k] - ( M.Mem[i][k] * y ); 
                   }
                }
             }
          }
       }
       TulisMATRIKS(M);
       System.out.println();
       System.out.println();
    }


   public static void bacaFile(MATRIKS M){
      /* Kamus Lokal */
      char cc;
      Double elmt;
      int i,j,NB,NK;

      /* Algoritma */
      Scanner sc = new Scanner(System.in);
      try {
         System.out.print("Masukkan nama file : ");
         File fl = new File(sc.next());
         Scanner read = new Scanner(fl);
         NB = 0;
         NK = 0;
         while (read.hasNextLine()) {
            String data = read.nextLine();
            NB++;
            Scanner readline = new Scanner(data);
            while(readline.hasNextDouble()){
               elmt = readline.nextDouble();
               NK++;
            }
            readline.close();
         }
         NK = NK/NB;
         read.close();
         Scanner reads = new Scanner(fl);
         MakeMATRIKS(NB,NK,M);
         i = 1;
         while (reads.hasNextLine()) {
            String data = reads.nextLine();
            Scanner readmatrik = new Scanner(data);
            j = 1;
            while(readmatrik.hasNextDouble()){
               elmt = readmatrik.nextDouble();
               M.Mem[i][j] = elmt;
               j++;
            }
            i++;
            readmatrik.close();
         }
         reads.close();
         System.out.println(String.format("Matriks dalam file berukuran %dx%d : ",NB,NK));
         TulisMATRIKS(M);
         System.out.println();
      } 
      catch (FileNotFoundException e) {
         System.out.println("Terjadi kesalahan");
         e.printStackTrace();
      }
   }

    // Function to perform the inverse operation on the matrix.
   public static void BackwardSubInterpolasi (MATRIKS MAug, double [] Hasil){
   /* Melakukan substitusi mundur sehingga diperoleh sebuah array Hasil yang berisi solusi augmented */
      /* Kamus Lokal */
      int i,j;
      int n = MAug.NBrsEff;
      double sum;
      /* Algoritma */
      Hasil[n]=MAug.Mem[n][n+1]/MAug.Mem[n][n];
      for(i=n-1; i>=1; i--){
          sum=0;
          for(j=i+1; j<=n; j++){
              sum=sum+MAug.Mem[i][j]*Hasil[j];
          }
          Hasil[i]=(MAug.Mem[i][n+1]-sum)/MAug.Mem[i][i];
      }
      System.out.println(); System.out.println();
      System.out.println("Matriks Solusi SPL diatas adalah = ");
      for (i=1; i<=n; i++){
           System.out.println("a"+i+" = "+String.format("%.4f",Hasil[i]));
      }
      System.out.println();
   }

   public static void TulisPolinom (double Hasil[], int N)
   /* Menuliskan hasil solusi matriks dalam bentuk fungsi interpolasi polinomial */
   { /* Kamus Lokal */
      int i;
      double x;
      Scanner keyboard = new Scanner(System.in);
     /* Algoritma */
     System.out.println("Fungsi Polinom Interpolasi yang terbentuk adalah ");
     System.out.print("P(x) = ");
     for (i=1; i<=N; i++){
        if (i!=N){
           if ((Math.abs(Hasil[i])>0.000001)){
              System.out.print(String.format("%.4f",Hasil[i])+"x^"+(N-i)+" + ");
           }
        }
        else {
               System.out.print(String.format("%.4f",Hasil[i]));
        }
      }
     System.out.println();
     System.out.println();
     System.out.print("Masukkan nilai x yang ingin dihitung dengan P(x) = ");
     x = keyboard.nextDouble();
     System.out.println("Hasil P("+x+") adalah "+String.format("%.4f",HitungPolinom(Hasil,N,x)));
   }
    
   public static void InversGJ(MATRIKS MAug)
   /* Prosedur untuk mendapatkan invers dari suatu matriks dengan metode Gauss Jordan */
   { 
      /* Kamus Lokal */ 
      double temp;
      int N = MAug.NBrsEff;
      int i,j,k;
      
      // Menuliskan Matriks Augmented. 
      System.out.println(); 
      System.out.println("=== Augmented Matrix ==="); 
      TulisMATRIKS(MAug); System.out.println(); 
      System.out.println(); 

      /* Pertukaran Baris sebelum dilakukan OBE */
      for (i=N; i>1; i--) { 
            if (MAug.Mem[i - 1][1] < MAug.Mem[i][1]) { 
               SwapBaris(MAug,i,i-1); 
         } 
      } 
  
      // Replace a row by sum of itself and a 
      // constant multiple of another row of the matrix 
      for (i = 1; i <= N; i++) { 
         for (j = 1; j <= N; j++) { 
               if (j != i) { 
                  temp = MAug.Mem[j][i] / MAug.Mem[i][i]; 
                  for (k = 1; k <= 2*N; k++) { 
                     MAug.Mem[j][k] -= MAug.Mem[i][k] * temp; 
                  }
                  System.out.println("----------------");
                  TulisMATRIKS(MAug); System.out.println(); 
               } 
         } 
      } 
      // Multiply each row by a nonzero integer. 
      // Divide row element by the diagonal element 
      for (i = 1; i <= N; i++) { 
  
         temp = MAug.Mem[i][i]; 
         for (j = 1; j <= 2*N; j++) { 
  
               MAug.Mem[i][j] = MAug.Mem[i][j] / temp; 
         }
         System.out.println("----------------");
         TulisMATRIKS(MAug); System.out.println();  
      } 
  
      /* Menuliskan Hasil Inverse berupa Matriks Augmented */ 
      System.out.println(); 
      System.out.println("=== Invers Matriks Augmented ==="); 
      TulisMATRIKS(MAug); System.out.println(); 
      System.out.println(); 
   }
   
   public static void MetodeKofaktor(MATRIKS M)
   /* Prosedur untuk mendapatkan nilai determinan dengan metode kofaktor */
   {	/* Kamus Lokal */
      int x,i,j;
      MATRIKS sub = new MATRIKS();
      double det = 0;
      /* Algoritma */
      if ((M.NBrsEff==1) && (M.NKolEff==1)){
         det = M.Mem[1][1];
         System.out.println("Karena ukuran Matriks 1x1, maka det = a11 = "+det);
      }
      else if ((M.NBrsEff==2) && (M.NKolEff==2)){
         det = M.Mem[1][1]*M.Mem[2][2]-M.Mem[2][1]*M.Mem[1][2];
         System.out.println("Karena ukuran Matriks 2x2, maka det = ad - bc = "+det);
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
            System.out.println("Minor C1"+x+" adalah ");
            TulisMATRIKS(sub); System.out.println(); System.out.println();
            det = det + (Math.pow(-1,1+x) * M.Mem[1][x] * Determinan(sub));
         }
         System.out.println("Sehingga determinan matriks tersebut adalah :");
         for (x=1; x<=M.NKolEff;x++){
            if (x < M.NKolEff){
               System.out.print("-1^"+(1+x)+"*"+M.Mem[1][x]+"*det(C1"+x+") + ");
            }   
            else if (x == M.NKolEff){
               System.out.print("-1^"+(1+x)+"*"+M.Mem[1][x]+"*det(C1"+x+") = ");
            }
         }
         System.out.println(det);
      }
   }

   public static void InverseMethodInterpolasi(MATRIKS MAug, double[] Hasil)
    /* Melakukan prosedur untuk mencari solusi SPL dengan metode matriks balikan dari matriks augmented MAug */
    { /* Kamus Lokal */
      MATRIKS M = new MATRIKS();
      MATRIKS MK = new MATRIKS();
      MATRIKS MSol = new MATRIKS();
      int i,j;
      int N = MAug.NBrsEff;
      /* Algoritma */
      GetMATRIKS(MAug, M);
      GetKONSTANTA(MAug, MK);
      System.out.println(); 
      System.out.println("Matriks A = ");
      TulisMATRIKS(M); System.out.println();
      System.out.println(); 
      if (Determinan(M)==0){
         System.out.println("Matriks A tidak mempunyai invers");  
      }
      else {
         System.out.println("Matriks Balikan A = ");
         TulisMATRIKS(Invers(M)); System.out.println();
         System.out.println();  
         System.out.println("Matriks Konstanta = ");
         TulisMATRIKS(MK); System.out.println();
         System.out.println(); 
         MakeMATRIKS(N,1,MSol);
         MSol = KaliMATRIKS(Invers(M),MK);
         System.out.println("Matriks Solusi SPL = ");
         for (i=1; i<=N; i++){
         System.out.println("a"+i+" = "+MSol.Mem[i][1]);
         Hasil[i] = MSol.Mem[i][1];
         }
      }
      System.out.println(); 
    }

    public static void MetodeCramerInterpolasi (MATRIKS MAug, double[] Hasil)
   /* Melakukan operasi penyelesaian SPL dengan Metode Cramer dengan masukan berupa Matriks Augmented */
   {  /* Kamus Lokal */
      MATRIKS M = new MATRIKS();
      MATRIKS MK = new MATRIKS();
      int i;
      /* Algoritma */
      GetMATRIKS(MAug, M);
      GetKONSTANTA(MAug, MK);
      System.out.println("Matriks A = ");
      TulisMATRIKS(M); System.out.println();
      System.out.println("Determinan Matriks A adalah "+Determinan(M)); System.out.println(); 
      for (i = 1; i<=M.NKolEff; i++){
         System.out.println("Matriks A"+i+" = ");
         TulisMATRIKS(MCramer(i,M,MK)); System.out.println();
         System.out.println("Determinan Matriks A"+i+" adalah "+Determinan(MCramer(i,M,MK)));
         Hasil[i] = Determinan(MCramer(i,M,MK))/Determinan(M);
         System.out.println("a"+i+" = "+String.format("%.4f / %.4f = %.4f",Determinan(MCramer(i,M,MK)),Determinan(M),Hasil[i]));
         System.out.println();

      }
   }

   public static boolean IsAllZero (MATRIKS MAug)
   /* Mengecek apakah Matriks Augmented MAug mempunyai solusi atau tidak */
   {  /* Kamus Lokal */
      int i,j;
      boolean check = true;
      /* Algoritma   */
      i=MAug.NBrsEff;
      j=1;
      while ((j<=MAug.NKolEff) & (check)){
         check = (MAug.Mem[i][j]==0);
         j++;
      }
      return check;
   }

   public static boolean IsNoSolution (MATRIKS MAug)
   /* Mengecek apakah Matriks Augmented MAug mempunyai solusi atau tidak */
   {  /* Kamus Lokal */
      int i,j;
      int count;
      boolean check = true;
      /* Algoritma   */
      i=MAug.NBrsEff;
      j=1; count=0;
      while ((j<MAug.NKolEff) & (check)){
         check = (MAug.Mem[i][j]==0);
         j++;
      }
      if (check) {
         check = (MAug.Mem[i][j]!=0);
      }
      return check;
   }

   public static double HitungPolinom (double[] Hasil, int N, double x)
   /* Menghitung fungsi polinomial yang diperoleh dengan parameter nilai berupa x */
   {  /* Kamus Lokal */
      double total;
      int i;
      /* Algoritma */
      total = 0;
      for (i=N; i>0; i--){
         total = total + Hasil[i]*Math.pow(x,N-i);
      }
      return total;
   }

   public static void ReduceBrs (MATRIKS MAug)
   /* Menghapus baris terakhir yang berisi 0 semua */
   {  /* Kamus Lokal */
      int i,j;
      MATRIKS MTemp = new MATRIKS();
      /* Algoritma */
      CopyMATRIKS(MAug,MTemp);
      MakeMATRIKS(MAug.NBrsEff-1, MAug.NKolEff, MAug);
      for (i=1; i<=MAug.NBrsEff; i++){
         for (j=1; j<=MAug.NKolEff; j++){
            MAug.Mem[i][j]=MTemp.Mem[i][j];
         }
      }
   }

   public static void BackwardSub (MATRIKS MAug)
   /* Melakukan substitusi mundur sehingga diperoleh solusi-solusi SPL berdasarkan augmented matrix */
   {  /* Kamus Lokal */
      char [] FreeVar = new char[100];
      int temp,i,j,k,count;
      int n, x;
      double y;
      boolean status;
      /* Algoritma */
      for (i = 2; i <= MAug.NBrsEff; i++ ) {
         j = 1;
         status = false;
         x = 0;
         while (j <= MAug.NKolEff  &&  status == false) {
            if (MAug.Mem[i][j] != 0) {
               x = j;
               status = true;
            }
            j ++ ;
         }

         if (status) {
            for (n = i - 1; n >= 1; n--) {
               if (MAug.Mem[n][x] != 0) {
                  y = MAug.Mem[n][x];
                  for (k = 1; k <= MAug.NKolEff; k++) {
                     MAug.Mem[n][k] = MAug.Mem[n][k] - ( MAug.Mem[i][k] * y ); 
                  }
               }
            }
         }
      }

      /* Penentuan Solusi-Solusi SPL */
      if (IsNoSolution(MAug)) {
         System.out.println("Tidak ada solusi SPL");
      }
      else {
         while (IsAllZero(MAug)){
            ReduceBrs(MAug);
         }
         if (IsNoSolution(MAug)) {
            System.out.println("Tidak ada solusi SPL");
         }
         else {
            System.out.println("Matriks Solusi SPL diatas adalah = ");
            for (i=1; i<=MAug.NBrsEff; i++){
               count = 0;
               temp = 115;
               for (j=MAug.NKolEff-1; j>=i; j--){
                  if ((j > i) & (MAug.Mem[i][j]!=0)){
                     count++;
                     FreeVar[j]= (char) temp;
                     temp++;
                  }
                  else if ((j==i) & (MAug.Mem[i][j]==1)){
                     if (count == 0){
                        System.out.print("X"+i+" = ");
                        System.out.print(String.format("%.4f",MAug.Mem[i][MAug.NKolEff]));
                     }
                     else {
                        System.out.print("X"+i+" = ");
                        if (MAug.Mem[i][MAug.NKolEff]!=0){
                           System.out.print(MAug.Mem[i][MAug.NKolEff]+" + ");
                        }
                        for (k=1; k<=count; k++){
                           if ((k<count) && (MAug.Mem[i][MAug.NKolEff-k]!=0)){
                              System.out.print(String.format("%.4f",-MAug.Mem[i][MAug.NKolEff-k])+""+FreeVar[MAug.NKolEff-k]+" + ");
                           }
                           else if (MAug.Mem[i][MAug.NKolEff-k]!=0) {
                              System.out.print(String.format("%.4f",-MAug.Mem[i][MAug.NKolEff-k])+""+FreeVar[MAug.NKolEff-k]);
                           }
                        }
                     }
                     System.out.println();
                  } 
               }
            }
            if (MAug.NBrsEff < MAug.NKolEff-1){
               for (i=MAug.NBrsEff+1; i<=MAug.NKolEff-1; i++){
                  System.out.print("X"+i+" = ");
                  System.out.println(""+FreeVar[i]);
               }
               System.out.println("Solusi merupakan solusi banyak");      
            }
            else {
               System.out.println("Solusi merupakan solusi unik");
            }   
         }
         
      }
      System.out.println();
   }

   public static void BacaHilbert (MATRIKS MAug) 
   {
      /* KAMUS LOKAL */
      int i, j, N;
      double k;
      
      /* ALGORITMA */
      System.out.print("Masukkan ukuran N matriks hilbert: ");
      Scanner keyboard = new Scanner(System.in);
      N = keyboard.nextInt();
      for (i = 1; i <= N; i ++) {
         k = i;
         for (j = 1; j <= N + 1; j ++) {
            if (j != N +1) {
               MAug.Mem[i][j] = (1/k);
               k++; 
            }
            else {
               if (i == 1) {
                  MAug.Mem[i][j] = 1;
               }
               else {
                  MAug.Mem[i][j] = 0;
               }
            }
         }
      }
      MakeMATRIKS(N, N+1, MAug);
      TulisMATRIKS(MAug);
      System.out.println();
   }
}

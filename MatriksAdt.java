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
      for (i = 1; i<=M.NKolEff; i++){
         System.out.println("Matriks A"+i+" = ");
         TulisMATRIKS(MCramer(i,M,MK)); System.out.println();
         System.out.println("Determinan Matriks A"+i+" adalah "+Determinan(MCramer(i,M,MK)));
         Hasil[i] = Determinan(MCramer(i,M,MK))/Determinan(M);
         System.out.println("x"+i+" = "+String.format("%.4f / %.4f = %.4f",Determinan(MCramer(i,M,MK)),Determinan(M),Hasil[i]));
         System.out.println();
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
      System.out.print("Masukkan ukuran matriks NxN : ");
      N = keyboard.nextInt();
      for (i=1; i<=N; i++){
         for (j=1; j<=N; j++){
            M.Mem[i][j]= keyboard.nextDouble();
         }
      }
      MakeMATRIKS(N, N, M);
   }

   public static void BacaAUG(MATRIKS MAug){
      /* Membaca sebuah SPL dalam bentuk Matriks Augmented */
      /* Kamus Lokal */
      int i,j,m,n;
      Scanner keyboard = new Scanner(System.in);
      /* Algoritma */
      System.out.print("Masukkan ukuran matriks augmented MxN : ");
      m = keyboard.nextInt();
      n = keyboard.nextInt();
      for (i=1; i<=m; i++){
         for (j=1; j<=n; j++){
            MAug.Mem[i][j]= keyboard.nextInt();
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
        int op;
        Scanner keyboard = new Scanner (System.in);
        /* Algoritma */
        BacaAUG(MAug);
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
        int op;
        Scanner keyboard = new Scanner (System.in);
        /* Algoritma */
        BacaMATRIKS(M);
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
        int op;
        Scanner keyboard = new Scanner (System.in);
        /* Algoritma */
        BacaMATRIKS(M);
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
                I = Identity(M.NBrsEff);
                MAug = GabungMATRIKS(M,I);
                InversGJ(MAug);
                MInv = GetINVERSE(MAug); break;
            case 2:
               System.out.println();
               System.out.println("Matriks Kofaktor dari M adalah ");
               TulisMATRIKS(MatriksKofaktor(M)); System.out.println(); System.out.println();
               System.out.println("Adjoin dari M adalah ");
               TulisMATRIKS(Adjoin(M)); System.out.println(); System.out.println();
               System.out.println("Determinan dari M adalah "+Determinan(M));  
               if (Determinan(M)== 0){
                  System.out.println("Matriks M tidak mempunyai invers"); 
               }
               else {
                  MInv = Invers(M);
               }   
               break;
        }
        System.out.println("Invers dari M adalah ");
        TulisMATRIKS(MInv);
        System.out.println();
        System.out.println();
    }

   public static void MainKofaktor()
    /* Prosedur untuk Menerima Matriks M berukuran NxN kemudian menampilkan Matriks Kofaktor dari M */
    {  /* Kamus Lokal */
        MatriksAdt.MATRIKS M = new MatriksAdt.MATRIKS();
        MatriksAdt.MATRIKS MKof = new MatriksAdt.MATRIKS();
        /* Algoritma */
        BacaMATRIKS(M);
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
        /* Algoritma */
        BacaMATRIKS(M);
        MAdj = Adjoin(M);
        System.out.println("Adjoin dari M adalah ");
        TulisMATRIKS(MAdj);
        System.out.println();
        System.out.println();
    }

   public static void Interpolasi()
   /* Program membaca sebuah bilangan N, kemudian program membaca sebanyak N titik */
   {  /* Kamus Lokal */
      double[] XTemp = new double[100];
      double[] YTemp = new double[100];
      double[] Hasil = new double[100];
      MATRIKS MAug = new MATRIKS();
      int i,j,N,op;
      Scanner keyboard = new Scanner (System.in);
      /* Algoritma */
      System.out.print("Masukkan jumlah titik yang diinginkan = ");
      N = keyboard.nextInt();
      for (i=1; i<=N; i++){
         XTemp[i] = keyboard.nextDouble();
         YTemp[i] = keyboard.nextDouble();
      }
      MakeMATRIKS(N,N+1,MAug);
      for (i=1; i<=N; i++){
         for (j=1; j<=N+1; j++){
            if (j==N+1){
               MAug.Mem[i][j]=YTemp[i];
            }
            else if (j==N){
               MAug.Mem[i][j]=1;
            }
            else {
               MAug.Mem[i][j]=Math.pow(XTemp[i],(N-j));
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
                                TulisMATRIKS(M);
                                System.out.println();
                                System.out.println("------------");
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
        TulisMATRIKS(M);
        System.out.println();
        System.out.println("----------------");
        z = 1;
        for(i = 1; i <= M.NBrsEff; i++){
            z = z*M.Mem[i][i];
        }
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
               TulisMATRIKS(M);
               System.out.println();
               System.out.println("----------------"); 
            }
            TulisMATRIKS(M);
            System.out.println();
            System.out.println("----------------");
          }
          TulisMATRIKS(M);
          System.out.println();
          System.out.println("----------------");
       }
       det = 1;
       for (i = 1; i <= M.NBrsEff ; i ++) {
          det = det * M.Mem[i][i];
       }
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
        return(c == 0 || c == 1);
    }


    public static void Gauss (MATRIKS M){
        /* Kamus lokal */
        int i,j,k,l,m,n;
        double x;
        boolean found;
        /* Algoritma */
        /* Membuat semua baris leading 1 */
        for (i = 1; i <= M.NBrsEff; i++){
            x = 1;
            j = i;
            found = false;
            while(j <= M.NBrsEff && found == false){
                k = 1;
                while(k <= M.NKolEff && found == false){
                    if(M.Mem[j][k] != 0){
                        found = true;
                        x = M.Mem[j][k];
                    }
                    else {
                        k++;
                    }
                }
                j++;
            }
            if(found == true){
                for(j = 1; j <= M.NKolEff; j++){
                    M.Mem[i][j] = M.Mem[i][j]/x;
                }
                TulisMATRIKS(M);
                System.out.println();
                System.out.println("--------------");
            }
        }

        i = 1;
        for(j = 1; j <= M.NKolEff; j++){
            if(!LeadOne(M,i,j)){
                /* Mencari baris lead one teratas */
                k = i;
                found = false;
                while(k <= M.NBrsEff && found == false){
                    if(M.Mem[k][j] != 0){
                        found = true;
                    }
                    else{
                        k++;
                    }
                }
                /* meng-OBE baris dibawah lead one */
                for(l = k+1; l <= M.NBrsEff; l++){
                    if(M.Mem[l][j] != 0){
                        for(m = j; m <= M.NKolEff; m++){
                            M.Mem[l][m] = M.Mem[l][m] - M.Mem[k][m];
                        }
                        TulisMATRIKS(M);
                        System.out.println();
                        System.out.println("----------------");
                        n = j;
                        x = 1;
                        while(M.Mem[l][n] == 0 && n <= M.NKolEff){
                            n++;
                            if(M.Mem[l][n] != 0){
                                x = M.Mem[l][n];
                            }
                        }
                        for(m = j+1; m <= M.NKolEff; m++){
                            M.Mem[l][m] = M.Mem[l][m]/x;
                        }
                        TulisMATRIKS(M);
                        System.out.println();
                        System.out.println("----------------");
                    }
                }
            }
            if(M.Mem[i][j] != 0){
                i++;
            }
        }
        for(i = M.NBrsEff; i > 1; i--){
            if(Idx(M,i) < Idx(M,i-1)){
                SwapBaris(M,i,i-1);
            }
        }
        TulisMATRIKS(M);
        System.out.println();
        System.out.println("----------------");
    }

    public static void GaussJordan (MATRIKS M) {
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
                TulisMATRIKS(M);
                System.out.println();
                System.out.println("--------------");
             }
          }
          TulisMATRIKS(M);
          System.out.println();
          System.out.println("--------------");
       }
    }

    // Function to perform the inverse operation on the matrix.
    
    public static void BackwardSub (MATRIKS MAug){
      /* Kamus Lokal */
      double [] X = new double[100];
      int i,j;
      int n = MAug.NBrsEff;
      double sum;
      /* Algoritma */
      X[n]=MAug.Mem[n][n+1]/MAug.Mem[n][n];
      for(i=n-1; i>=1; i--){
          sum=0;
          for(j=i+1; j<=n; j++){
              sum=sum+MAug.Mem[i][j]*X[j];
          }
          X[i]=(MAug.Mem[i][n+1]-sum)/MAug.Mem[i][i];
      }
      System.out.println(); System.out.println();
      System.out.println("Matriks Solusi SPL diatas adalah = ");
      for (i=1; i<=n; i++){
           System.out.println("x"+i+" = "+X[i]);
      }
      System.out.println();
   }

   public static void BackwardSubInterpolasi (MATRIKS MAug, double [] Hasil){
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
           System.out.println("a"+i+" = "+Hasil[i]);
      }
      System.out.println();
   }

   public static void TulisPolinom (double Hasil[], int N)
   { /* Kamus Lokal */
      int i;
     /* Algoritma */
     System.out.println("Fungsi Polinom Interpolasi yang terbentuk adalah ");
     System.out.print("P(x) = ");
     for (i=1; i<=N; i++){
        if (i!=N){
           if ((Hasil[i]>0.00001)){
              System.out.print(String.format("%.4f",Hasil[i])+"x^"+(N-i)+" + ");
           }
        }
        else {
               System.out.print(String.format("%.4f",Hasil[i]));
        }
      }
     System.out.println();
     System.out.println();
   }
    
   public static void InversGJ(MATRIKS MAug) 
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
}

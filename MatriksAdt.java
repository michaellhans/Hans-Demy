/* Nama     :   Michael Hans
                Lionnarta Savirandy */
/* NIM      :   13518056
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
    /* Konstruktor */
    public static void MakeMATRIKS (int NB, int NK, MATRIKS M){
	   M.NBrsEff=NB;
	   M.NKolEff=NK;
    }

    /* Baca Matriks */
    public static void BacaMATRIKS(MATRIKS M){
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

    /* Tulis Matriks */
    public static void TulisMATRIKS(MATRIKS M){
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
   
    public static MATRIKS KaliMATRIKS (MATRIKS M1, MATRIKS M2){	
        /* Kamus Lokal */
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

    public static void Transpose (MATRIKS M){
        /* Kamus Lokal */
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

    public static void CopyMATRIKS (MATRIKS MIn, MATRIKS MHsl){
        /* Kamus Lokal */
        int i,j;
        /* Algoritma */
        for (i=1; i<=MIn.NBrsEff;i++){
            for (j=1; j<=MIn.NKolEff;j++){
                MHsl.Mem[i][j]=MIn.Mem[i][j];
            }
        }
    }

    public static double Determinan (MATRIKS M){
        /* Kamus Lokal */
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
    public static MATRIKS Minor(int baris, int kolom, MATRIKS M){
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


    public static void EGauss (MATRIKS M){
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
            found = false;
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
                        while(M.Mem[l][n] == 0){
                            n++;
                            x = M.Mem[l][n];
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
            i++;
        }
    }
}

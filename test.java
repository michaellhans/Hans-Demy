import OpsMatriks.MatriksAdt;
import java.lang.Math; 
import java.util.Scanner;

public class test{
    public static void main(String[] args){
        MatriksAdt obj = new MatriksAdt();
        MatriksAdt.MATRIKS M = new MatriksAdt.MATRIKS();
        obj.BacaAUG(M);
        obj.TulisMATRIKS(M);
        System.out.println();
        System.out.println();
        obj.Gauss(M);
        obj.TulisMATRIKS(M);
    }
}
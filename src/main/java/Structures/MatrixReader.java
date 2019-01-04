package Structures;

import java.util.Scanner;

public class MatrixReader {

    public static int[][] readAdjMatrix(){
        Scanner sc = new Scanner(System.in);
        int amtLines = Integer.parseInt(sc.nextLine());
        int[][] adjacencyMatrix = new int[amtLines][amtLines];
        for (int j = 0; j < amtLines; j++) {


            String a = sc.nextLine();

            for (int i = 0; i < a.length(); i++) {
                if(a.charAt(i) == '0'){
                    adjacencyMatrix[j][i] = 0;
                }
                else if(a.charAt(i) == '1'){
                    adjacencyMatrix[j][i] = 1;
                }
                else if(a.charAt(i) == 'w'){
                    adjacencyMatrix[j][i] = 2;
                }
                else if(a.charAt(i) == '3'){// <------------------------- DANGER STRANGER
                    adjacencyMatrix[j][i] = 3;
                }
            }
        }

        return adjacencyMatrix;
    }
}

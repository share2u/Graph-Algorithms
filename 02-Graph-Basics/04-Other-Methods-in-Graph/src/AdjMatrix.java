import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AdjMatrix {

    private int V;
    private int E;
    private int[][] adj;

    public AdjMatrix(String filename){

        File file = new File(filename);

        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if(V < 0) throw new IllegalArgumentException("V must be non-negative");
            adj = new int[V][V];

            E = scanner.nextInt();
            if(E < 0) throw new IllegalArgumentException("E must be non-negative");

            for(int i = 0; i < E; i ++){
                int a = scanner.nextInt();
                validateVertex(a);
                int b = scanner.nextInt();
                validateVertex(b);

                if(a == b) throw new IllegalArgumentException("自环边 is Detected!");
                if(adj[a][b] == 1) throw new IllegalArgumentException("平行边 are Detected!");

                adj[a][b] = 1;
                adj[b][a] = 1;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    // 校验顶点v
    private void validateVertex(int v){
        if(v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + "is invalid");
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    // 顶点v,w是否有边
    public boolean hasEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        return adj[v][w] == 1;
    }

    // v顶点 相连的顶点
    public ArrayList<Integer> adj(int v){
        validateVertex(v);
        ArrayList<Integer> res = new ArrayList<>();
        for(int i = 0; i < V; i ++)
            if(adj[v][i] == 1)
                res.add(i);
        return res;
    }
    // 顶点的度
    public int degree(int v){
        return adj(v).size();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("V = %d, E = %d\n", V, E));
        for(int i = 0; i < V; i ++){
            for(int j = 0; j < V; j ++)
                sb.append(String.format("%d ", adj[i][j]));
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args){

        AdjMatrix adjMatrix = new AdjMatrix("g.txt");
        System.out.print(adjMatrix);
    }
}

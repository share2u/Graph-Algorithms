import java.util.*;

public class Hungarian {

    private Graph G;
    private int maxMatching = 0;
    private int matching[];// 结点匹配的点

    public Hungarian(Graph G){

        BipartitionDetection bd = new BipartitionDetection(G);
        if(!bd.isBipartite())
            throw new IllegalArgumentException("Hungarian only works for bipartite graph.");

        this.G = G;

        int[] colors = bd.colors();

        matching = new int[G.V()];
        Arrays.fill(matching, -1);
        for(int v = 0; v < G.V(); v ++)
            if(colors[v] == 0 && matching[v] == -1)// 只遍历左侧的为找到匹配的点
                if(bfs(v)) maxMatching ++;
    }

    // 是否为结点v 找到增广路
    private boolean bfs(int v){

        Queue<Integer> q = new LinkedList<>();
        int[] pre = new int[G.V()];// 结点访问过的路径
        Arrays.fill(pre, -1);

        q.add(v);
        pre[v] = v;
        while(!q.isEmpty()){
            int cur = q.remove();// 左侧结点
            for(int next: G.adj(cur))
                if(pre[next] == -1){ // 未被访问过
                    if(matching[next] != -1){ // 是匹配过的点
                        q.add(matching[next]);
                        pre[next] = cur;
                        pre[matching[next]] = next;
                    }
                    else{ // 找到增广路
                        pre[next] = cur;
                        ArrayList<Integer> augPath = getAugPath(pre, v, next);
                        for(int i = 0; i < augPath.size(); i += 2){
                            matching[augPath.get(i)] = augPath.get(i + 1);
                            matching[augPath.get(i + 1)] = augPath.get(i);
                        }
                        return true;
                    }
                }
        }
        return false;
    }

    // 起始 未匹配点start 到未匹配点end的路径
    private ArrayList<Integer> getAugPath(int[] pre, int start, int end){

        ArrayList<Integer> res = new ArrayList<>();
        int cur = end;
        while(cur != start){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(start);
        return res;
    }

    public int maxMatching(){
        return maxMatching;
    }

    public boolean isPerfectMatching(){
        return maxMatching * 2 == G.V();
    }

    public static void main(String[] args){

        Graph g = new Graph("g.txt");
        Hungarian hungarian = new Hungarian(g);
        System.out.println(hungarian.maxMatching());

        Graph g2 = new Graph("g2.txt");
        Hungarian hungarian2 = new Hungarian(g2);
        System.out.println(hungarian2.maxMatching());
    }
}

package rubikscube;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class SolveCube {

    PriorityQueue<Node> pq;
    private String[] rotations = new String[18];
    private String[] sides = {"R", "O", "Y", "G", "B", "W"};
    private String[] cubietypes = {"corner", "firstsix", "othersix"};
    private char[] cornermap = new char[88182027];
    private char[] firstsixmap = new char[42577984];
    private char[] othersixmap = new char[42577984];
    CalculateTables ct = new CalculateTables();
    String initialState;
    private ArrayList<String> expanded;

    public SolveCube() {
        int index = 0;
        for (String side : sides) {
            for (int i = 1; i <= 3; i++) {
                rotations[index] = side + i;
                index++;
            }
        }
    }

    public static void main(String[] args) {
        String state = "RRRRRRRRRGGGYYYBBBGGGYYYBBBGGGYYYBBBOOOOOOOOOWWWWWWWWW";
        try{
            state = "";
            File srcFile = new File(args[0]);
            BufferedReader in = new BufferedReader(new FileReader(srcFile)); 
            while (in.ready()) { 
                state += in.readLine();
            }
            in.close();
        }
        catch (Exception e){
            System.out.println(e);
            return;
        }
        state = state.replaceAll("\\s", "");
        state = state.substring(0, 12)
                + state.substring(18, 21)
                + state.substring(27, 30)
                + state.substring(12, 15)
                + state.substring(21, 24)
                + state.substring(30, 33)
                + state.substring(15, 18)
                + state.substring(24, 27)
                + state.substring(33, state.length());
        state = state.replaceAll("([A-Z])([A-Z])", "$1 $2");
        state = state.replaceAll("([A-Z])([A-Z])", "$1 $2");
        
        /* test rotation */
//        RubiksCube rc = new RubiksCube(state);
//        rc.rotate("R1Y1B2R1G1W2");
//        state = rc.toString();
//        state = state.replaceAll("([A-Z])([A-Z])", "$1 $2");
//        state = state.replaceAll("([A-Z])([A-Z])", "$1 $2");
        /* end test rotation */
        
        SolveCube sc = new SolveCube();
        System.out.println(sc.solve(state));
    }
    
    private String solve(String initialState) {
        try {
            for (String cubie : cubietypes) {
                File srcFile = new File(cubie+".tbl");
                BufferedReader in = new BufferedReader(new FileReader(srcFile));
                if (cubie.equals("corner")){
                    in.read(cornermap, 0, 88182027);
                    in.close();
                }
                else if (cubie == "firstsix"){
                    in.read(firstsixmap, 0, 42577984);
                    in.close();
                }
                else if (cubie == "othersix"){
                    in.read(othersixmap, 0, 42577984);
                    in.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        this.initialState = initialState;
        if (!isValidState()) {
            System.out.println("Invalid input state. Disregarding due to small heuristic tables and searching anyway.");
        }
        int maxdepth = 18;
        return idaStar(maxdepth);
    }

    private class Node implements Comparable<Node> {
        int g, h, f;
        String path;
        public Node(){
            path = "";
            g = 0;
            h = getMovesNeededToComplete();
            f = g + h;
        }
        public Node(String s) {
            path = s;
            g = path.length()/2;
            h = getMovesNeededToComplete();
            f = g + h;
//            if (h != -1) System.out.println(s + ": " + f + "=" + g + "+" + h);
        }
        @Override
        public int compareTo(Node n) {
            if (f > n.f) {
                return 1;
            } else if (f == n.f) {
                return 0;
            } else {
                return -1;
            }
        }
        private int getMovesNeededToComplete() {
            RubiksCube rc = new RubiksCube(initialState);
            if (path.length() > 0){
                rc.rotate(path);
            }
            String cornerCubies = rc.getCornerCubies();
            String firstSixCubies = rc.getFirstSixSideCubies();
            String otherSixCubies = rc.getOtherSixSideCubies();
            int cornerEncoding = ct.encode(cornerCubies, "corner");
            int firstSixEncoding = ct.encode(firstSixCubies, "firstsix");
            int otherSixEncoding = ct.encode(otherSixCubies, "othersix");
            int cornerMoves = Integer.parseInt(Character.toString(cornermap[cornerEncoding]));
            int firstSixMoves = Integer.parseInt(Character.toString(firstsixmap[firstSixEncoding]));
            int otherSixMoves = Integer.parseInt(Character.toString(othersixmap[otherSixEncoding]));
            int moves = Math.max(cornerMoves, firstSixMoves);
            moves = Math.max(moves, otherSixMoves);
            moves--;
            return moves;
        }
    }

    private boolean isValidState() {
        Node n = new Node();
        if (n.h == -1){
            return false;
        }
        return true;
    }
    
    private String idaStar(int maxdepth){
        for (int i = 1; i <= maxdepth; i++){
//            System.out.println("Searching with maxdepth " + i);
            expanded = new ArrayList<>();
            pq = new PriorityQueue();
            pq.add(new Node());
            Node result;
            while (!pq.isEmpty()){
                try {
                    result = dlAstar(i);
                } catch (Exception ex) {
                    return ex.getMessage();
                }
                if (result != null){
                    return result.path;
                }
            }
        }
        return "No solution found within max depth "+maxdepth;
    }
    private Node dlAstar(int maxdepth) throws Exception{
        Node n = pq.poll();
        if (n.g > maxdepth){
            return null;
        }
        if (n.h == 0){
            RubiksCube rc = new RubiksCube(initialState);
            rc.rotate(n.path);
//            System.out.println("Potential solution: " + rc.toString());
            if ("RRRRRRRRRGGGGGGGGGYYYYYYYYYBBBBBBBBBOOOOOOOOOWWWWWWWWW".equals(rc.toString())){
                return n;
            }
        }
//        System.out.println("Expanding " + n.path + " (" + n.g + "+" + n.h + "=" + n.f + ")");
        expand(n);
        return null;
    }
    
    private void expand(Node n) {
        for (String rotation : rotations) {
            String newpath = n.path+rotation;
            Node newnode = new Node(newpath);
            RubiksCube rc = new RubiksCube(initialState);
            rc.rotate(newpath);
            String state = rc.toString();
            if (newnode.h != -1 && expanded.indexOf(state) == -1){
                pq.add(newnode);
                expanded.add(state);
            }
            else if (newnode.h == -1 && expanded.indexOf(state) == -1){
                newnode.h = 99;
                newnode.f = newnode.g + newnode.h;
                pq.add(newnode);
                expanded.add(state);
            }
        }
    }
    
}
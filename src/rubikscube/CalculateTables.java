package rubikscube;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class CalculateTables {
    private int[] cornermap;
    private int[] sidemap;
    private int[] othersidemap;
    private String[] cubietypes = {"corner", "firstsix", "othersix"};
    private ArrayList<String> corners1 = new ArrayList<>(Arrays.asList("YGR", "YBO", "WGO", "WBR", "YRB", "YOG", "WOB", "WRG"));
    private ArrayList<String> corners2 = new ArrayList<>(Arrays.asList("YRG", "YOB", "WOG", "WRB", "YBR", "YGO", "WBO", "WGR"));
    private ArrayList<String> edges = new ArrayList<>(Arrays.asList("RW", "RG", "RB", "RY", "YB", "BW", "WG", "GY", "OY", "OG", "OW", "OB"));
    private String[] sides = {"R", "O", "Y", "G", "B", "W"};
    private String[] rotations = new String[18];
    private ArrayList<Node> q;
    
    private int maxdepth = 5;

    public CalculateTables() {
        int index = 0;
        for (String side : sides) {
            for (int i = 1; i <= 3; i++) {
                rotations[index] = side + i;
                index++;
            }
        }
    }
    
    public static void main(String args[]) {
        CalculateTables ct = new CalculateTables();
        ct.calculate();
    }

    private void calculate() {
        q = new ArrayList<>();
        q.add(new Node(1, ""));
        cornermap = new int[88179840];
        sidemap = new int[42577984];
        othersidemap = new int[42577984];
        while (q.size() > 0) {
            search();
        }
        try {
            toFile();
        } catch (Exception e) {
            System.out.println("Failed: " + e);
        }
    }

    private void search() {
        Node n = q.get(0);
        q.remove(0);
        RubiksCube rc = new RubiksCube("R R R R R R R R R G G G G G G G G G Y Y Y Y Y Y Y Y Y B B B B B B B B B O O O O O O O O O W W W W W W W W W");
        rc.rotate(n.path);
        System.out.println(n.depth);
        int cornerencoding = encode(rc.getCornerCubies(), "corner");
        int sideencoding = encode(rc.getFirstSixSideCubies(), "firstsix");
        int othersideencoding = encode(rc.getOtherSixSideCubies(), "othersix");
        if (cornermap[cornerencoding] == 0 || sidemap[sideencoding] == 0 ||
        othersidemap[othersideencoding] == 0) {
            if (cornermap[cornerencoding] == 0){
                cornermap[cornerencoding] = n.depth;
            }
            if (sidemap[sideencoding] == 0){
                sidemap[sideencoding] = n.depth;
            }
            if (othersidemap[othersideencoding] == 0){
                othersidemap[othersideencoding] = n.depth;
            }
            if (++n.depth > maxdepth+1) {
                return;
            }
            for (String rotation : rotations) {
                rotation = n.path + rotation;
                q.add(new Node(n.depth, rotation));
            }
        }
    }
    
    public int encode(String state, String cubietype) {
        int encoding = 0;
        if ("corner".equals(cubietype)){
            int factorialnum = 7;
            ArrayList<String> corners1copy = new ArrayList<>(corners1);
            ArrayList<String> corners2copy = new ArrayList<>(corners2);
            for (int i = 0; i < state.length(); i+=3) {
                String cubie = state.substring(i, i+3);
                int whichcubie = -1;
                int orientation = 0;
                if (i/3 == 0 || i/3 == 3 || i/3 == 4){
                    corners:
                    for (String s: corners1copy){
                        whichcubie++;
                        for (int j = 0; j < 3; j++){
                            if (cubie.equals(s)){
                                corners1copy.remove(whichcubie);
                                corners2copy.remove(whichcubie);
                                break corners;
                            }
                            else{
                                s = s.substring(1, 3) + s.charAt(0);
                                orientation++;
                            }
                        }
                        orientation = 0;
                    }
                }
                else{
                    corners:
                    for (String s: corners2copy){
                        whichcubie++;
                        for (int j = 0; j < 3; j++){
                            if (cubie.equals(s)){
                                corners1copy.remove(whichcubie);
                                corners2copy.remove(whichcubie);
                                break corners;
                            }
                            else{
                                s = s.substring(1, 3) + s.charAt(0);
                                orientation++;
                            }
                        }
                        orientation = 0;
                    }
                }
                /* 2187 = 3^7 */
                encoding += factorial(factorialnum) * whichcubie * 2187 + 
                            Math.pow(3, --factorialnum) * orientation;
            }
        }
        else{
            int factorialnum = 11;
            ArrayList<String> edgescopy = new ArrayList<>(edges);
            for (int i = 0; i < state.length(); i+=2) {
                String cubie = state.substring(i, i+2);
                int whichcubie = -1;
                int orientation = 0;
                edges:
                for (String s: edgescopy){
                    whichcubie++;
                    for (int j = 0; j < 2; j++){
                        if (cubie.equals(s)){
                            edgescopy.remove(whichcubie);
                            break edges;
                        }
                        else{
                            s = s.substring(1, 2) + s.charAt(0);
                            orientation++;
                        }
                    }
                    orientation = 0;
                }
                /* 720 = 6!, 64 = 2^6 */
                encoding += factorial(factorialnum)/720 * whichcubie * 64 + 
                            Math.pow(2, factorialnum - 6) * orientation;
                factorialnum--;
            }
        }
        return encoding;
    }
    
    private int factorial(int n) {
        return n <= 1 ? 1 : n * factorial(n-1);
    }

    private void toFile() throws Exception {
        for (String cubie : cubietypes) {
            File dstFile = new File(cubie + ".tbl");
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(dstFile)));
            if (cubie == "corner"){
                for (int h : cornermap) {
                    out.print(h);
                }
            }
            else if (cubie == "firstsix"){
                for (int h : sidemap) {
                    out.print(h);
                }
            }
            else if (cubie == "othersix"){
                for (int h : othersidemap) {
                    out.print(h);
                }
            }
            out.close();
        }
    }
    
    private class Node {
        int depth;
        String path;
        public Node(int depth, String rotations) {
            this.depth = depth;
            this.path = rotations;
        }
    }
}
package pcd.ass01.multithread;

public class Main {
    public static void main(String[] args) {
        BoidsModel model = new BoidsModel();
        new BoidsSimulator(model);
    }
}
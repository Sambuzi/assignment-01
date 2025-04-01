package pcd.ass01.virtualthread;

public class Main {
    public static void main(String[] args) {
        BoidsModel model = new BoidsModel();
        new BoidsVirtualThreadSimulator(model);
    }
    
}

package TestInheritanceInAbstract;

public class ADemo extends Demo{
    @Override
    public void m1() {
    System.out.println("Overridden abstract method in implementation ADemo");
    }

    public void m2(){
        System.out.println("Overridden concrete method in implementation ADemo");
    }
}

package TestInheritanceInAbstract;

public class BDemo extends Demo{
    @Override
    public void m1() {
        System.out.println("Overridden abstract method in implementation DDemo");
    }

    public void m2(){
        System.out.println("Overridden concrete method in implementation BDemo");
    }
}

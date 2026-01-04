package TestInheritanceInAbstract;

public class TestDemo {

    public static  void main(String[] args){
        Demo ademo = new ADemo();
        ademo.m1();
        ademo.m2();

        Demo bdemo = new BDemo();
        bdemo.m1();
        bdemo.m2();
    }
}

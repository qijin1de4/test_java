
public class InheritanceTest {

    public static void main(String[] args) {
        Father f = new Son();
        f.methodOverwrite("a");
    }
}
    class Father {
        public  void methodOverload() {}

        public void methodOverwrite(Object obj) {
            System.out.println("Father methodOverwrite get  executed!");
        }
    }

    class Son extends Father{
        public void methodOverload() {}

        public void methodOverwrite(int i) {
            System.out.println("Son methodOverwrite gets executed!");
        }
    }

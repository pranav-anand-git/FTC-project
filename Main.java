//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static int arraySum(int[] array){
        int sum = 0;
        for (int i = 0; i < array.length; i++){
          sum = array[i] + sum;
        }
        return sum;
    }

    public static void main(String[] args) {

        int[] array = new int[] {1,2,3,4,5,6};

        int finalSum = Main.arraySum(array);
        System.out.println(finalSum);
    }
}

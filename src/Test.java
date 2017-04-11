import com.teamtreehouse.model.TestData;

/**
 * Created by psp58 on 12/30/2016.
 */
public class Test {

    public static void main(String[] args){

        TestData t1 = new TestData(1, "Prinal","Patel");
        TestData t2 = new TestData(2,"Primus","Patel");
        TestData t3 = t1;
        TestData t4 = new TestData(1, "Prinal","Patel");

        System.out.println("The answer is : " +(t3==t4));
        System.out.println("The answer is : " +(t3.equals(t4)));


    }

}

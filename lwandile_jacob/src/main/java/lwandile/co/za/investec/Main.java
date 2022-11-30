package lwandile.co.za.investec;

public class Main {

    public static void main(String[] args) {
        //input array
        int inputArray[] = { 4, 8, 16};

        GreatestCommonDivisor greatestCommonDivisor = new GreatestCommonDivisor();
        System.out.println("The Highest Common Factor of the elements is :"+greatestCommonDivisor.highestCommonFactor(inputArray));
    }


}

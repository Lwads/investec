package lwandile.co.za.investec;

public class GreatestCommonDivisor {

    //find result of the highest common factor
    public int highestCommonFactor(int [] numbers){
        int result = numbers[0];
        for (int i = 1; i < numbers.length; i++){

            result = findGCDofNumbers(numbers[i], result);

            if(result == 1){
                return 1;
            }
        }
        return result;
    }

    //Check GCD
    private int findGCDofNumbers(int value1, int value2){

        //check if value if 0 and return value 2 to do another check because 0 is not a divisible number
        if (value1 == 0)
            return value2;

        return findGCDofNumbers(value2 % value1, value1);
    }


}

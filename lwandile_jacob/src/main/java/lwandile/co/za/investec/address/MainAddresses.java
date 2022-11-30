package lwandile.co.za.investec.address;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainAddresses {

    public static void main(String[] args) {

        JsonFactory jsonFactory = new JsonFactory();

        JsonParser jsonParser = null;

        //son parser to convert json into object
        try {
            jsonParser = jsonFactory.createJsonParser(new File("src/main/resources/addresses.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        List<Address> addressList = new ArrayList<>();

        //Mapping json parser into the actual list project
        try {
            addressList = objectMapper.readValue(jsonParser, new TypeReference<List<Address>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("2 a. Pretty print addresses ############################################################################################");
        System.out.println();
        //2 a. Pretty print addresses
        addressList.forEach(address -> System.out.println(prettyPrintAddress(address)));
        System.out.println();
        System.out.println();

        System.out.println("2 b. pretty print all addresses from the json list #######################################################################");
        System.out.println();
        //2 b. pretty print all addresses from the json list
        addressList.forEach(System.out::println);
        System.out.println();
        System.out.println();

        System.out.println("2 c. print type postal,physical and business #############################################################################");
        System.out.println();
        //2 c. print type postal,phyical and business
        printCertainTypes(addressList).forEach(System.out::println);
        System.out.println();
        System.out.println();

        System.out.println("2 d. i , ii and e. validations ###########################################################################################");
        System.out.println();
        // 2 d. i , ii and e.
        System.out.println(validateEachAddress(addressList));

    }

    //2 a.
    private static String prettyPrintAddress(Address address) {
        return address.getType() + "" + address.getAddressLineDetail() +
                " city =" + address.getCityOrTown() +
                " - " + address.getProvinceOrState() +
                " postal code =" + address.getPostalCode() +
                " - " + address.getCountry();
    }

    // 2 c.
    private static List<Address> printCertainTypes(List<Address> addresses) {

        List<Address> addressList = new ArrayList<>();
        for (Address address : addresses) {

            //used code for filtering because they unique and more accurate
            if (address.getType().getCode().equalsIgnoreCase("1")
                    || address.getType().getCode().equalsIgnoreCase("2")
                    || address.getType().getCode().equalsIgnoreCase("5")) {

                addressList.add(address);

            }

        }

        return addressList;

    }

    private static String validateEachAddress(List<Address> addresses) {

        String messageStr = "Valid fields";

        for (Address address : addresses) {

            if (!isNumeric(address.getPostalCode())) {
                return "Invalid Postal code,  Postal code must contain only numbers :::::::::::::"+address;
            }
            if (validateCountry(address.getCountry())) {
                return "Invalid country,  country should not contain empty or null values :::::::::::::"+address;
            }
            if (!validateAddressLine(address.getAddressLineDetail())) {
                return "Invalid address line,  address line should contain at least 1 line :::::::::::::::"+address;
            }
            if (address.getCountry().getCode().equalsIgnoreCase("ZA")
                    && address.getProvinceOrState() == null) {

                return "Invalid address for South Africa, province must not be empty  ::::::::::::::::"+address;

            }else if( address.getCountry().getCode().equalsIgnoreCase("ZA") && validateStringNullOrEmpty(address.getProvinceOrState().getCode())
                    && validateStringNullOrEmpty(address.getProvinceOrState().getName())){

                return "Invalid address for South Africa, province must not be empty  ::::::::::::"+address;
            }

        }

        return messageStr;

    }

    private static boolean validateAddressLine(AddressLineDetail addressLineDetail) {
        boolean validator = false;

        if (addressLineDetail != null) {
            if (addressLineDetail.getLine1() != null || !addressLineDetail.getLine1().isEmpty()) {
                validator = true;
            }

            if (addressLineDetail.getLine2() != null || !addressLineDetail.getLine2().isEmpty()) {
                validator = true;
            }

        }
        return validator;
    }

    private static boolean validateStringNullOrEmpty(String str) {

        return null == str && str.isEmpty();
    }

    private static boolean validateCountry(Country country) {
        return null == country
                && validateStringNullOrEmpty(country.getName())
                && validateStringNullOrEmpty(country.getCode());
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

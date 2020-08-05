package engine.validations;

import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;

import java.util.HashSet;

public class SuggestValidator extends ActionValidator {
    private StringBuilder addNewTripSuggestErrorMessage = new StringBuilder();
    private static final int TRIP_SUGGEST_INPUT_LIMIT = 7;
    private static final int[] TRIP_SCHEDULE_TYPE_INPUT_RANGE  = {1, 5};

    public String getAddNewTripSuggestErrorMessage() {
        return addNewTripSuggestErrorMessage.toString();
    }

    public boolean validateTripSuggestInput(String[] inputTripSuggestString, HashSet<String> allStationsLogicNames, MapDescriptor mapDescriptor) {
        boolean isValid = true;
        //example of valid input - Ohad,A.C.B,3,13:25,4,30,2

        if (inputTripSuggestString.length != TRIP_SUGGEST_INPUT_LIMIT) {
            addNewTripSuggestErrorMessage.append("Please insert 7 elements, try again.\n");
            return false;
        }
        if(!validateOwnerRoute(inputTripSuggestString[1], allStationsLogicNames, mapDescriptor)) {//add 1 more check of valid route A.B.C
            addNewTripSuggestErrorMessage.append("Not valid route.\n");
            isValid = false;
        }
        if (!validateDepartureDayNumber(inputTripSuggestString[2])) {
            isValid = false;
        }
        if (!validateTime(inputTripSuggestString[3], 1)) {
            //check if its : 24 (0-23) and minutes in multiples of 5 (0 - 55)
            isValid = false;
        }
        if (!validateTripScheduleType(inputTripSuggestString[4])) {
            isValid = false;
        }
        if (!validatePPK(inputTripSuggestString[5])) {
            // check again if how the calc if this value valid
            isValid = false;
        }
        if (!validatePossiblePassengerCapacity(inputTripSuggestString[6])) {
            isValid = false;
        }

        addNewTripSuggestErrorMessage.append(this.getGeneralErrorMessage());
        this.setGeneralErrorMessage(new StringBuilder());

        return isValid;
    }

    public boolean validateDepartureDayNumber(String input) {
        boolean res = false;
        try {
            int intInput = Integer.parseInt(input);
            if (isNumeric(input)) {
                if (intInput >= 1) {
                    res = true;
                }
            }
            if(!res) {
                addNewTripSuggestErrorMessage.append("Arrival day number is not valid," +
                        " please try again, insert a number bigger than 0 \n");
            }
        }
        catch (NumberFormatException e) {
            addNewTripSuggestErrorMessage.append(e.getMessage());
        }

        return res;
    }

    public boolean validateTripScheduleType(String input) {
        boolean res = false;
        try {
            int intInput = Integer.parseInt(input);
            if (isNumeric(input)) {
                if (intInput >= TRIP_SCHEDULE_TYPE_INPUT_RANGE[0] && intInput <=TRIP_SCHEDULE_TYPE_INPUT_RANGE[1]) {
                    res = true;
                }
            }
            if(!res) {
                addNewTripSuggestErrorMessage.append("Trip schedule type is not valid" +
                        "please try again, insert a number between 1 to 5 (include) \n");
            }
        }
        catch (Exception e) {

        }

        return res;
    }

    public boolean validatePPK(String input) {
        boolean res = false;
        res = checkIfANumberAndBiggerThanOne(input);

        if(!res) {
            addNewTripSuggestErrorMessage.append("PPK number is not valid," +
                    " please try again, insert a number bigger than 0 \n");
        }
        return res;
    }

    public boolean validatePossiblePassengerCapacity(String input) {
        boolean res = false;
        res = checkIfANumberAndBiggerThanOne(input);

        if(!res) {
            addNewTripSuggestErrorMessage.append("Possible passenger capacity number is not valid," +
                    " please try again, insert a number bigger than 0 \n");
        }
        return res;
    }

    public static boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        return str.chars().allMatch(Character::isDigit);
    }

    public boolean checkIfANumberAndBiggerThanOne(String input) {
        int intInput = Integer.parseInt(input);

        if (isNumeric(input)) {
            if (intInput >= 1) {
                return true;
            }
        }
        return false;
    }

    public boolean validateOwnerRoute(String route, HashSet<String> allStationsLogicNames, MapDescriptor mapDescriptor) {
        String[] stations = route.split(",");

        if(isStationsExists(stations, allStationsLogicNames)) {
            if(isPathsValid(stations, mapDescriptor)) {
                return true;
            }
        }

        return false;
    }

    private boolean isPathsValid(String[] stations, MapDescriptor mapDescriptor) {
        for(int i =0; i < stations.length - 1; i++) {
            if(!isPathValid(stations[i], stations[i+1], mapDescriptor)) {
                return false;
            }
        }
        return true;
    }


    private boolean isStationsExists(String[] stations, HashSet<String> allStationsLogicNames) {
        for(String station : stations) {
            if(!allStationsLogicNames.contains(station)) {
                return false;
            }
        }
        return true;
    }

}

package engine.validations;

import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;

import java.util.List;

public class RequestValidator extends ActionValidator {
    private StringBuilder addNewTripRequestErrorMessage;

    private static final int TRIP_REQUEST_INPUT_LIMIT = 6;

    public RequestValidator() {
        this.addNewTripRequestErrorMessage = new StringBuilder();
        this.addNewTripRequestErrorMessage.append("\nSorry, your input was not valid. Errors: \n");
    }

    public boolean validateTripRequestInput(String[] inputs, MapDescriptor mapDescriptor) {
        boolean isValid = true;

        if(inputs.length != TRIP_REQUEST_INPUT_LIMIT) {
            addNewTripRequestErrorMessage.append("Please insert 6 elements, try again.\n");
            return false;
        }
        if(!(inputs[1].equals(inputs[2]))) {//dest and source stations are not the same
            if(!validateSource(inputs[1], mapDescriptor)) {
                isValid = false;
            }
            if(!validateDestination(inputs[2], mapDescriptor)) {
                isValid = false;
            }
        }
        else {
            addNewTripRequestErrorMessage.append("You entered same Source station and Destination station!!\n");
            isValid = false;
        }
        if(!super.validateTime(inputs[3], 3)) {
            isValid = false;
        }

        if(!validateTimeParam(inputs[4])) {
            addNewTripRequestErrorMessage.append("The fifth parameter is invalid, please insert a to choose arrival time or s to choose starting time.");
            isValid = false;
        }
        if(!validateTripDay(inputs[5])) {
            isValid = false;
        }


        addNewTripRequestErrorMessage.append(this.getGeneralErrorMessage());
        this.setGeneralErrorMessage(new StringBuilder());

        return isValid;
    }

    private boolean validateTripDay(String input) {
        int dayN;
        try {
            dayN = Integer.parseInt(input);
        }
        catch (Exception e) {
            addNewTripRequestErrorMessage.append("Trip request Day number isn't an integer.");
            return false;
        }
        if(dayN >= 1) {
            return true;
        }
        else {
            addNewTripRequestErrorMessage.append("Trip request Day number isn't a day >= 1.");
            return false;
        }
    }

    private boolean validateTimeParam(String input) {
        if(input.equals("userRequestDeparture") || input.equals("userRequestArrival")) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getAddNewTripRequestErrorMessage() {
        return addNewTripRequestErrorMessage.toString();
    }

    public boolean validateSource(String input, MapDescriptor mapDescriptor) {
        if(checkIFStationsIsExist(input, mapDescriptor)) {
            return true;
        }
        else {
            addNewTripRequestErrorMessage.append("Source isn't exist in the system\n");
            return false;
        }
    }
}

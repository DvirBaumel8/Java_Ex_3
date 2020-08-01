package engine.validations;

import engine.manager.EngineManager;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Stop;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionValidator {

    private StringBuilder generalErrorMessage;

    public ActionValidator() {
        this.generalErrorMessage = new StringBuilder();
    }

    public boolean validateOwnerName(String input, List<String> names) {
        if(!validateOwnerNameUnique(input, names)) {
            generalErrorMessage.append("Owner name should be unique\n");
            return false;
        }
        try {
            if (input.isEmpty()) {
                generalErrorMessage.append("Request owner name is empty\n");
                return false;
            }
            Integer.parseInt(input);
            generalErrorMessage.append("Owner name can't contains only numbers\n");
            return false;
        }
        catch(Exception e) {
            return true;
        }
    }

    private boolean validateOwnerNameUnique(String input, List<String> names) {
        for(String ownerName : names) {
            if(ownerName.equals(input)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateTime (String time, int index) {
        final String TIME24HOURS_PATTERN =
                "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
        Matcher matcher = pattern.matcher(time);
        if(matcher.matches()) {
            char[] charTimeArr = time.toCharArray();
            int lastDigitInt = charTimeArr[time.length() - 1] - '0';
            if(lastDigitInt % 5 == 0) {
                return true;
            }
            else {
                generalErrorMessage.append("Time isn't valid, should be in the following format__:__  and the last digit should be zero or five \n");
            }
        }
        else {
            if(index == 3) {
                generalErrorMessage.append("Time template of trip arrival time isn't valid, template should be __:__ (12:35)\n");
            }
            else {
                generalErrorMessage.append("Time template isn't valid, template should be __:__ (12:35)\n");
            }
        }
        return false;
    }

    public boolean checkIFStationsIsExist(String stationName, MapDescriptor mapDescriptor) {
        for(Stop stop : mapDescriptor.getStops().getStop()) {
            if(stop.getName().equals(stationName)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateDestination(String input, MapDescriptor mapDescriptor) {
        if(checkIFStationsIsExist(input, mapDescriptor)) {
            return true;
        }
        else {
            generalErrorMessage.append("Destination isn't exist in the system\n");
            return false;
        }
    }


    public StringBuilder getGeneralErrorMessage() {
        return generalErrorMessage;
    }

    public void setGeneralErrorMessage(StringBuilder generalErrorMessage) {
        this.generalErrorMessage = generalErrorMessage;
    }

}

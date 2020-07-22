package Engine.manager;

import Engine.Notifications.MatchNotificationsDetails;
import Engine.maps.MapsManager;
import Engine.maps.MapsTableElementDetails;
import Engine.maps.graph.GraphBuilder;
import Engine.matching.MatchUtil;
import Engine.matching.MatchingHelper;
import Engine.matching.RoadTrip;
import Engine.matching.SubTrip;
import Engine.trips.TripRequest;
import Engine.trips.TripSuggest;
import Engine.users.Transaction;
import Engine.users.User;
import Engine.users.UsersManager;
import Engine.validations.RequestValidator;
import Engine.validations.SuggestValidator;
import Engine.xmlLoading.SchemaBasedJAXBMain;
import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;
import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Route;
import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.TransPool;
import Engine.xmlLoading.xmlValidation.XMLValidationsImpl;
import com.fxgraph.graph.Graph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EngineManager {
    private static EngineManager engineManagerInstance;
    private static MapsManager mapsManager;
    private static UsersManager usersManager;
    private static List<RoadTrip> potentialCacheList;


    public static EngineManager getEngineManagerInstance() {
        if (engineManagerInstance == null) {
            engineManagerInstance = new EngineManager();
            mapsManager = new MapsManager();
            usersManager = new UsersManager();
        }
        return engineManagerInstance;
    }

    public UsersManager getUsersManager() {
        return usersManager;
    }

    public void handleFileUploadProcess(String fileContent, String userName, String mapName) throws FileNotFoundException {
        SchemaBasedJAXBMain schemaBasedJAXBMain = new SchemaBasedJAXBMain();
        TransPool transPool = schemaBasedJAXBMain.deserializeFrom(new FileInputStream(fileContent));
        XMLValidationsImpl xmlValidator = new XMLValidationsImpl(transPool);
        List<String> validationErrors = new ArrayList<>();
        if(!xmlValidator.validateXmlFile(validationErrors)) {
            //handle file error content
        }
        try {
            mapsManager.createNewMap(transPool.getMapDescriptor(), userName, mapName);
        }
        catch (Exception ex) {
            //TODO - handle new map name already exist
        }

    }

    public List<MapsTableElementDetails> getAllMapsTableElementsDetails() {
            return mapsManager.getAllMapsTableElementsDetails();
    }

    public void createNewTripRequest(String mapName, String[] inputsArr) {
        RequestValidator requestValidator = new RequestValidator();
        if(requestValidator.validateTripRequestInput(inputsArr)) {
            TripRequest tripRequest = buildNewRequest(inputsArr);
            mapsManager.addTripRequestByMapName(mapName, tripRequest);
        }
        else {
            String errorMessage = requestValidator.getAddNewTripRequestErrorMessage();
            //Todo - handle error input
        }
    }

    private TripRequest buildNewRequest(String[] inputsArr) {
        int hour = Integer.parseInt(inputsArr[3].split(":")[0]);
        int minutes = Integer.parseInt(inputsArr[3].split(":")[1]);
        int day = Integer.parseInt(inputsArr[5]);
        return new TripRequest(inputsArr[0], inputsArr[1], inputsArr[2], minutes, hour, day, inputsArr[4].equals("S"));
    }


    public void createNewTripSuggest(String mapName, String[] inputsArr) {
        SuggestValidator suggestValidator = new SuggestValidator();
            if(suggestValidator.validateTripSuggestInput(inputsArr, mapsManager.getAllLogicStationsByMapName(mapName))) {
                TripSuggest tripSuggest = buildNewSuggest(inputsArr);
                mapsManager.addTripSuggestByMapName(mapName, tripSuggest);
            }
            else {
                //TODO - handle error input
            }
    }

    private TripSuggest buildNewSuggest(String[] inputsArr) {
            int hour = Integer.parseInt(inputsArr[3].split(":")[0]);
        int minutes = Integer.parseInt(inputsArr[3].split(":")[1]);
        Route newTripSuggestRoute = new Route();
        String stringPath = inputsArr[1].replace('-', ',');
        newTripSuggestRoute.setPath(stringPath);
        int day = 0;
        int ppk = 0;
        int scheduleTypeInt = 0;
        int tripCapacity = 0;
        day = Integer.parseInt(inputsArr[2]);
        scheduleTypeInt = Integer.parseInt(inputsArr[4]);
        ppk = Integer.parseInt(inputsArr[5]);
        tripCapacity = Integer.parseInt(inputsArr[6]);
        return new TripSuggest(inputsArr[0], newTripSuggestRoute, minutes, hour, day, scheduleTypeInt, ppk, tripCapacity);
    }

    private void addNewUser(String userName, String userType) {
            User user = new User(userName, userType);
            usersManager.addNewUser(userName, user);
    }

    public void loadMoneyIntoAccount(String userName, double moneyToLoad) {
        usersManager.loadMoneyIntoUserAccount(userName, moneyToLoad);
    }

    private void transferMoneyFromRequesterToSuggester(int totalCost, Integer requestId, Integer suggestId, String mapName) {
        String requesterUserName = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, requestId).getNameOfOwner();
        User requesterUser = usersManager.getUserByName(requesterUserName);
        String suggesterUerName = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId).getTripOwnerName();
        User SuggesterUser = usersManager.getUserByName(suggesterUerName);
        requesterUser.takeMoney(totalCost);
        SuggesterUser.addMoney(totalCost);
    }

    private boolean isRequesterHaveEnoughCash(int totalCost, String mapName, Integer requestId) {
        String requesterUserName = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, requestId).getNameOfOwner();
        double currentUserCash = usersManager.getCurrentUserCashByUserName(requesterUserName);
        if(currentUserCash - totalCost < 0) {
            return false;
        }
        return true;
    }

    private void updateMapTableEntityDetails(String mapName) {
        mapsManager.addMatchTripRequestToMapByMap(mapName);
    }

    private void sendNotificationToSuggester(String mapName, Integer requestId, Integer suggestId, double totalPayment) {
        MapsTableElementDetails mapsTableElementDetails = mapsManager.getMapTableElementDetailsByMapName(mapName);
        MatchNotificationsDetails matchNotificationsDetails = new MatchNotificationsDetails(mapsTableElementDetails, requestId, totalPayment);
        sendNotification(suggestId, matchNotificationsDetails);
    }

    private void sendNotification(Integer suggestId, MatchNotificationsDetails matchNotificationsDetails) {
        //TODO
    }

    public String getRatingsToSuggest(String mapName, Integer suggestId) {
        return mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId).getDriverRating().getDriverRatingInfo();
    }

    public Graph getGraph(String mapName) {
        MapDescriptor mapDescriptor = mapsManager.getMapDescriptorByMapName(mapName);
        GraphBuilder graphBuilder = new GraphBuilder(mapDescriptor);
        return graphBuilder.createGraph();
    }

    public void rankDriver(String mapName, Integer requestId, Integer suggestId, String[] inputs) {
        TripSuggest suggest = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId);
        TripRequest request = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, requestId);
        RoadTrip roadTrip = request.getMatchTrip();
        LinkedList<SubTrip> subTrips = roadTrip.getSubTrips();
        for(SubTrip subTrip : subTrips) {
            if(subTrip.getTrip().getSuggestID() == suggestId) {
                subTrip.setIsRanked(true);
            }
        }
        if (inputs[2].isEmpty()) {
            suggest.addRatingToDriver(Integer.parseInt(inputs[1]));
        } else {
            suggest.addRatingToDriver(Integer.parseInt(inputs[1]), inputs[2]);
        }
    }

    public List<String> findPotentialSuggestedTripsToMatch(String mapName, String inputMatchingString) {
        String[] elements = inputMatchingString.split(",");
        String tripRequestID = elements[0];
        String amountS = elements[1];
        TripRequest request = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, Integer.parseInt(tripRequestID));
        int amount = Integer.parseInt(amountS);
        MatchUtil matchUtil = new MatchUtil();
        LinkedList<LinkedList<SubTrip>> potentialRoadTrips = matchUtil.findPotentialMatches(request, amount, mapsManager.getTripSuggestsByMapName(mapName));
        MatchingHelper.updateSubTripsValues(potentialRoadTrips);
        potentialCacheList = MatchingHelper.convertTwoLinkedListToOneRoadTripLinkedList(potentialRoadTrips, request);

        int requestID = Integer.parseInt(inputMatchingString.split(",")[0]);
        return MatchingHelper.convertToStr(potentialCacheList, request);
    }

    public void makeMatch(RoadTrip roadTrip, String mapName, Integer requestId, Integer suggestId) throws Exception {
        TripRequest tripRequest = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, requestId);
        tripRequest.setMatched(true);
        if(tripRequest.isRequestByStartTime()) {
            tripRequest.setArrivalTime(roadTrip.getArrivalTime());
        }
        else {
            tripRequest.setStartTime(roadTrip.getStartTime());
        }

        tripRequest.setMatchTrip(roadTrip);
        if(!isRequesterHaveEnoughCash(roadTrip.getTotalCost(), mapName, requestId)) {
            throw new Exception("Not enough money exception");
        }
        transferMoneyFromRequesterToSuggester(roadTrip.getTotalCost(), requestId, suggestId, mapName);
        createAndAddAccountTransactions(roadTrip, mapName, requestId, suggestId);
        sendNotificationToSuggester(mapName, requestId, suggestId, roadTrip.getTotalCost());
        updateMapTableEntityDetails(mapName);
    }

    private void createAndAddAccountTransactions(RoadTrip roadTrip, String mapName, Integer requestId, Integer suggestId) {
        LocalDate date = java.time.LocalDate.now();
        Transaction requesterTransaction = createPaymentTransaction(roadTrip.getTotalCost(), requestId, mapName, date);
        String requesterUserName = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, requestId).getNameOfOwner();
        usersManager.addTransactionToUserByUserName(requesterUserName, requesterTransaction);

        Transaction suggesterTransaction = createReceivingTransaction(roadTrip.getTotalCost(), suggestId, mapName, date);
        String suggesterUserName = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName,suggestId).getTripOwnerName();
        usersManager.addTransactionToUserByUserName(suggesterUserName, suggesterTransaction);
    }

    private Transaction createReceivingTransaction(int totalCost, Integer suggestId, String mapName, LocalDate date) {
        String userName = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId).getTripOwnerName();
        double currentUserCash = usersManager.getCurrentUserCashByUserName(userName);
        return new Transaction(Transaction.TransactionType.PaymentTransfer, date, totalCost, currentUserCash, currentUserCash - totalCost);
    }

    private Transaction createPaymentTransaction(int totalCost, Integer requestId, String mapName, LocalDate date) {
        String userName = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName,requestId).getNameOfOwner();
        double currentUserCash = usersManager.getCurrentUserCashByUserName(userName);
        return new Transaction(Transaction.TransactionType.PaymentTransfer, date, totalCost, currentUserCash, currentUserCash - totalCost);
    }


}

//    private String findRouteToRequest(TripSuggest tripSuggest, TripRequest tripRequest) {
//        Station[] stations = tripSuggest.getTripStations();
//        boolean start = false;
//        String sourceStation = tripRequest.getSourceStation();
//        String destinationStation = tripRequest.getDestinationStation();
//        StringBuilder str = new StringBuilder();
//
//        for (int i = 0; i < stations.length; i++) {
//            if (!start) {
//                if (stations[i].equals(sourceStation)) {
//                    start = true;
//                    str.append(stations[i]);
//                    str.append(",");
//                }
//            } else {
//                if (stations[i].equals(destinationStation)) {
//                    str.append(stations[i]);
//                    break;
//                } else {
//                    str.append(stations[i]);
//                    str.append(",");
//                }
//            }
//        }
//
//        return str.toString();
//    }
//
//
//
//    public boolean validateRequestIDIsExist(String input) {
//        Integer requestID = Integer.parseInt(input);
//        return tripRequestUtil.isRequestIDExist(requestID);
//    }
//
//    public List<String> validateRequestIDExistInMatchedRequestTrip(String input) {
//        List<String> errors = new ArrayList<>();
//        int id;
//        try {
//            id = Integer.parseInt(input);
//        } catch (Exception ex) {
//            errors.add("Your choice wasn't a number\n");
//            return errors;
//        }
//        if (tripRequestUtil.isRequestIDExistInMatchedRequestTrips(id)) {
//            return errors;
//        } else {
//            errors.add("Trip request ID isn't exist in the previous list.\n");
//            return errors;
//        }
//    }

//    private void updateSuggestsCapacityPerTimeMap(RoadTrip roadTrip) {
//        LinkedList<Station> stations;
//        Station[] stationsArr;
//        int totalMinutes;
//        for(SubTrip subTrip : roadTrip.getSubTrips()) {
//            stations = subTrip.getRoute();
//            stationsArr = new Station[stations.size() - 1];
//            copyLinkedListToArr(stations, stationsArr);
//            for(int i =0; i < stationsArr.length; i++) {
//                totalMinutes = calcTotalMinutes(stationsArr[i].getDay(), stationsArr[i].getHour(), stationsArr[i].getMinutes());
//                subTrip.getTrip().addNewItemToCapacityPerTimeMap(totalMinutes);
//            }
//        }
//
//    }
//
//    private int calcTotalMinutes(int day, int hour, int minutes) {
//        return (day * 24 * 60) + (hour * 60) + (minutes);
//    }
//
//    private void copyLinkedListToArr(LinkedList<Station> stations, Station[] stationsArr) {
//        int index = 0;
//        for(Station station : stations) {
//            if(stations.getLast().getName().equals(station.getName())) {
//                break;
//            }
//            stationsArr[index] = station;
//            index++;
//        }
//    }
//
//    private boolean validaRoadTripChoice(String inputStr) {
//        int input = 0;
//
//        try {
//            input = Integer.parseInt(inputStr);
//        } catch (Exception ex) {
//            return false;
//        }
//        if (input < 1) {
//            return false;
//        } else if (potentialCacheList.size() < input) {
//            return false;
//        }
//        return true;
//    }
//
//
////---------------------------- RequestValidator Section ----------------------------
//
//
//    public List<String> validateChooseRequestAndAmountOfSuggestedTripsInput(String input) {
//        return validator.validateChooseRequestAndAmountOfSuggestedTripsInput(input);
//    }
//
//    public boolean validateChoosePotentialTripInput(String input, List<RoadTrip> potentialSuggestedTrips) {
//        return validator.validateChoosePotentialTripInput(input, potentialSuggestedTrips);
//    }
//
//
//    public List<String> getListDetailsPerTime() {
//        //String of name, id, path, current station
//        List<String> retList = new ArrayList<>();
//        Map<Integer, TripSuggest> suggestedTrips = tripSuggestUtil.getAllSuggestedTrips();
//
//        for (Map.Entry<Integer, TripSuggest> entry : suggestedTrips.entrySet()) {
//            if (entry.getValue().isActive()) {
//                Station currStation = findTripCurrentStation(entry.getValue());
//
//                String roadTrip = tripSuggestUtil.getStringRoadTripOfSuggestedTrip(entry.getKey().toString());
//                retList.add(String.format("ID: %s, Owner name: %s, Route: %s, Curr Stat: %s",
//                        entry.getValue().getSuggestID(), entry.getValue().getTripOwnerName(),
//                        roadTrip, currStation.getName()));
//            }
//        }
//        return retList;
//    }

//
//    public List<String> getTripSuggestIdsFromTripRequestWhichNotRankYet(String requestIDstr) {
//        int requestID = 0;
//        List<String> retVal = new ArrayList<>();
//        try {
//            requestID = Integer.parseInt(requestIDstr);
//        } catch (Exception ex) {
//            retVal.add("Your choice wasn't a number.\n");
//        }
//        TripRequest request = getTripRequestByID(requestID);
//        RoadTrip requestRoadTrip = request.getMatchTrip();
//        LinkedList<SubTrip> subTrips = requestRoadTrip.getSubTrips();
//
//        for (SubTrip subTrip : subTrips) {
//            if(!subTrip.getIsRanked()) {
//                retVal.add(String.valueOf(subTrip.getTrip().getSuggestID()));
//            }
//        }
//
//        if (retVal.size() == 0) {
//            retVal.add("You already rated all drivers that part of your road trip");
//            return retVal;
//        }
//        else {
//            requestIDCache = requestID;
//        }
//        return retVal;
//    }
//
//    //TripSuggestID, rate, description
//    public List<String> validateInputOfRatingDriverOfSuggestIDAndRating(String tripSuggestId, String rateStr, String description) {
//        List<String> errors = new ArrayList<>();
//
//        int suggestID = 0;
//        int rate;
//
//        try {
//            suggestID = Integer.parseInt(tripSuggestId);
//        } catch (Exception ex) {
//            errors.add("Trip suggest ID isn't a number.\n");
//        }
//        try {
//            rateStr = rateStr.trim();
//            rate = Integer.parseInt(rateStr);
//        } catch (Exception ex) {
//            errors.add("Rating isn't a number.");
//            return errors;
//        }
//        if (tripSuggestUtil.getTripSuggestByID(suggestID) == null) {
//            errors.add("Trip suggest isn't exist.\n");
//        }
//        if (rate < 1 || rate > 5) {
//            errors.add("Please insert a number between 1-5 for rating.\n");
//            return errors;
//        }
//        return errors;
//    }
//










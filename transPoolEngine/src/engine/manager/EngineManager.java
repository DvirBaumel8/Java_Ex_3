package engine.manager;

import engine.dto.mapPage.TripRequestDto;
import engine.dto.mapPage.TripSuggestDto;
import engine.dto.userPage.MapsTableElementDetailsDto;
import engine.dto.userPage.UserTransactionsHistoryDto;
import engine.maps.MapEntity;
import engine.maps.MapsManager;
import engine.maps.graph.GraphBuilder;
import engine.matching.MatchUtil;
import engine.matching.MatchingHelper;
import engine.matching.RoadTrip;
import engine.matching.SubTrip;
import engine.notifications.DriverRatingNotification;
import engine.notifications.MatchNotificationsDetails;
import engine.trips.TripRequest;
import engine.trips.TripSuggest;
import engine.users.Transaction;
import engine.users.User;
import engine.users.UsersManager;
import engine.validations.RequestValidator;
import engine.validations.SuggestValidator;
import engine.validations.UsersValidations;
import engine.xmlLoading.SchemaBasedJAXBMain;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Route;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.TransPool;
import engine.xmlLoading.xmlValidation.XMLValidationsImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EngineManager {
    private MapsManager mapsManager;
    private UsersManager usersManager;
    private List<RoadTrip> potentialCacheList;

    public EngineManager() {
        mapsManager = new MapsManager();
        usersManager = new UsersManager();
    }

    public void handleFileUploadProcess(String fileContent, String userName, String mapName) throws Exception {
        SchemaBasedJAXBMain schemaBasedJAXBMain = new SchemaBasedJAXBMain();
        InputStream stream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
        TransPool transPool = schemaBasedJAXBMain.deserializeFrom(stream);
        XMLValidationsImpl xmlValidator = new XMLValidationsImpl(transPool);
        List<String> validationErrors = new ArrayList<>();
        if (!xmlValidator.validateXmlFile(validationErrors)) {
            StringBuilder errors = new StringBuilder();
            for (String error : validationErrors) {
                errors.append(error);
            }
            throw new Exception(errors.toString());
        } else {
            mapsManager.createNewMap(transPool.getMapDescriptor(), userName, mapName);
        }
    }

    public List<MapsTableElementDetailsDto> getAllMapsTableElementsDetails() {
        return mapsManager.getAllMapsTableElementsDetails();
    }

    public void createNewTripRequest(String mapName, String[] inputsArr) throws Exception {
        RequestValidator requestValidator = new RequestValidator();
        if (requestValidator.validateTripRequestInput(inputsArr)) {
            TripRequest tripRequest = buildNewRequest(inputsArr);
            mapsManager.addTripRequestByMapName(mapName, tripRequest);
        } else {
            String errorMessage = requestValidator.getAddNewTripRequestErrorMessage();
            throw new Exception(errorMessage);
        }
    }

    private TripRequest buildNewRequest(String[] inputsArr) {
        int hour = Integer.parseInt(inputsArr[3].split(":")[0]);
        int minutes = Integer.parseInt(inputsArr[3].split(":")[1]);
        int day = Integer.parseInt(inputsArr[5]);
        return new TripRequest(inputsArr[0], inputsArr[1], inputsArr[2], minutes, hour, day, inputsArr[4].equals("S"));
    }


    public void createNewTripSuggest(String mapName, String[] inputsArr) throws Exception {
        SuggestValidator suggestValidator = new SuggestValidator();
        if (suggestValidator.validateTripSuggestInput(inputsArr, mapsManager.getAllLogicStationsByMapName(mapName))) {
            TripSuggest tripSuggest = buildNewSuggest(inputsArr);
            mapsManager.addTripSuggestByMapName(mapName, tripSuggest);
        } else {
            String errorMessage = suggestValidator.getAddNewTripSuggestErrorMessage();
            throw new Exception(errorMessage);
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

    public void addUser(String userName, String userType) {
        User user = new User(userName, userType);
        usersManager.addNewUser(userName, user);
    }

    public void loadMoneyIntoAccount(String userName, String moneyToLoad) throws Exception {
        StringBuilder error = new StringBuilder();
        UsersValidations.validateLoadMoneyIntoAccountInput(moneyToLoad, error);
        usersManager.loadMoneyIntoUserAccount(userName, Double.parseDouble(moneyToLoad));
    }

    public List<MapsTableElementDetailsDto> getMapsHtmlGraphDto(String mapName, String userName) {
        //return total maps in the system include new map
        MapEntity entity = mapsManager.getMapEntityByMapName(mapName);
        if (isUserRequester(userName)) {
            // return new MapPageDto(createRequestDtoListFromMapEntityForUser(entity, userName), createSuggestDtoListFromMapEntity(entity), entity.getHtmlGraph());
        } else {
            // return new MapPageDto(createRequestDtoListFromMapEntity(entity), createSuggestDtoListFromMapEntityForUser(entity, userName), entity.getHtmlGraph());
        }
        return null;
    }

    private boolean isUserRequester(String userName) {
        return usersManager.getUserByName(userName).getUserType() == User.UserType.Requester;
    }

    private List<TripSuggestDto> createSuggestDtoListFromMapEntity(MapEntity entity) {
        List<TripSuggestDto> suggestsDto = new ArrayList<>();
        List<TripSuggest> tripSuggests = entity.getTripSuggests();
        for (TripSuggest suggest : tripSuggests) {
            int suggestId = suggest.getSuggestID();
            List<String> passengersNames = suggest.getPassengers();
            int tripDay = suggest.getStartingDay();
            String sourceStation = suggest.getFirstStation().getName();
            String destinationStation = suggest.getLastStation().getName();
            double avgRating = suggest.getDriverRating().getRatingAVG();
            int numOfRaters = suggest.getDriverRating().getNumOfRatings();
            List<String> literalRatings = suggest.getDriverRating().getLiterallyRatings();

            TripSuggestDto tripSuggestDto = new TripSuggestDto(suggestId, passengersNames, tripDay, sourceStation, destinationStation, avgRating, numOfRaters, literalRatings);
            suggestsDto.add(tripSuggestDto);
        }
        return suggestsDto;
    }

    private List<TripSuggestDto> createSuggestDtoListFromMapEntityForUser(MapEntity entity, String userName) {
        List<TripSuggestDto> suggestsDto = new ArrayList<>();
        List<TripSuggest> tripSuggests = entity.getTripSuggests();
        for (TripSuggest suggest : tripSuggests) {
            if (suggest.getTripOwnerName().equals(userName)) {
                continue;
            }
            int suggestId = suggest.getSuggestID();
            List<String> passengersNames = suggest.getPassengers();
            int tripDay = suggest.getStartingDay();
            String sourceStation = suggest.getFirstStation().getName();
            String destinationStation = suggest.getLastStation().getName();
            double avgRating = suggest.getDriverRating().getRatingAVG();
            int numOfRaters = suggest.getDriverRating().getNumOfRatings();
            List<String> literalRatings = suggest.getDriverRating().getLiterallyRatings();

            TripSuggestDto tripSuggestDto = new TripSuggestDto(suggestId, passengersNames, tripDay, sourceStation, destinationStation, avgRating, numOfRaters, literalRatings);
            suggestsDto.add(tripSuggestDto);
        }
        return suggestsDto;
    }

    private List<TripRequestDto> createRequestDtoListFromMapEntity(MapEntity entity) {
        List<TripRequestDto> requestDto = new ArrayList<>();
        List<TripRequest> tripRequests = entity.getTripRequests();
        for (TripRequest request : tripRequests) {
            int requestId = request.getRequestID();
            String tripOwnerName = request.getNameOfOwner();
            String sourceStation = request.getSourceStation();
            String destinationStation = request.getDestinationStation();
            boolean isMatched;
            String roadStory = String.valueOf("");
            if (request.isMatched()) {
                isMatched = request.isMatched();
                roadStory = request.getMatchTrip().getRoadStory();
            } else {
                isMatched = false;
            }
            TripRequestDto tripRequestDto = new TripRequestDto(requestId, tripOwnerName, sourceStation, destinationStation, isMatched, roadStory);
            requestDto.add(tripRequestDto);
        }
        return requestDto;
    }

    private List<TripRequestDto> createRequestDtoListFromMapEntityForUser(MapEntity entity, String userName) {
        List<TripRequestDto> requestDto = new ArrayList<>();
        List<TripRequest> tripRequests = entity.getTripRequests();
        for (TripRequest request : tripRequests) {
            if (request.getNameOfOwner().equals(userName)) {
                continue;
            }
            int requestId = request.getRequestID();
            String tripOwnerName = request.getNameOfOwner();
            String sourceStation = request.getSourceStation();
            String destinationStation = request.getDestinationStation();
            boolean isMatched;
            String roadStory = String.valueOf("");
            if (request.isMatched()) {
                isMatched = request.isMatched();
                roadStory = request.getMatchTrip().getRoadStory();
            } else {
                isMatched = false;
            }
            TripRequestDto tripRequestDto = new TripRequestDto(requestId, tripOwnerName, sourceStation, destinationStation, isMatched, roadStory);
            requestDto.add(tripRequestDto);
        }
        return requestDto;
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
        if (currentUserCash - totalCost < 0) {
            return false;
        }
        return true;
    }

    private void updateMapTableEntityDetails(String mapName) {
        mapsManager.addMatchTripRequestToMapByMap(mapName);
    }

    private void sendNotificationToSuggester(String mapName, Integer requestId, Integer suggestId, double totalPayment) {
        MapsTableElementDetailsDto mapsTableElementDetails = mapsManager.getMapTableElementDetailsByMapName(mapName);
        MatchNotificationsDetails matchNotificationsDetails = new MatchNotificationsDetails(mapsTableElementDetails, requestId, totalPayment);
        sendMatchNotification(suggestId, matchNotificationsDetails);
    }

    private void sendMatchNotification(Integer suggestId, MatchNotificationsDetails matchNotificationsDetails) {
        //TODO
    }

    public String getRatingsToSuggest(String mapName, Integer suggestId) {
        return mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId).getDriverRating().getDriverRatingInfo();
    }

    public void rankDriver(String mapName, Integer requestId, Integer suggestId, String[] inputs) {
        TripSuggest suggest = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId);
        TripRequest request = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, requestId);
        RoadTrip roadTrip = request.getMatchTrip();
        LinkedList<SubTrip> subTrips = roadTrip.getSubTrips();
        for (SubTrip subTrip : subTrips) {
            if (subTrip.getTrip().getSuggestID() == suggestId) {
                subTrip.setIsRanked(true);
            }
        }
        if (inputs[2].isEmpty()) {
            suggest.addRatingToDriver(Integer.parseInt(inputs[1]));
        } else {
            suggest.addRatingToDriver(Integer.parseInt(inputs[1]), inputs[2]);
        }

        DriverRatingNotification ratingNotification = new DriverRatingNotification(request.getNameOfOwner(), suggestId, Integer.parseInt(inputs[1]), inputs[2]);
        sendRatingNotification(suggestId, ratingNotification);
    }

    private void sendRatingNotification(Integer suggestId, DriverRatingNotification matchNotificationsDetails) {
        //TODO
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
        TripSuggest suggest = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId);
        suggest.addPassenger(tripRequest.getNameOfOwner());
        tripRequest.setMatched(true);
        tripRequest.setMatchTrip(roadTrip);

        if (tripRequest.isRequestByStartTime()) {
            tripRequest.setArrivalTime(roadTrip.getArrivalTime());
        } else {
            tripRequest.setStartTime(roadTrip.getStartTime());
        }

        if (!isRequesterHaveEnoughCash(roadTrip.getTotalCost(), mapName, requestId)) {
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
        String suggesterUserName = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId).getTripOwnerName();
        usersManager.addTransactionToUserByUserName(suggesterUserName, suggesterTransaction);
    }

    private Transaction createReceivingTransaction(int totalCost, Integer suggestId, String mapName, LocalDate date) {
        String userName = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId).getTripOwnerName();
        double currentUserCash = usersManager.getCurrentUserCashByUserName(userName);
        return new Transaction(Transaction.TransactionType.PaymentTransfer, date, totalCost, currentUserCash, currentUserCash - totalCost);
    }

    private Transaction createPaymentTransaction(int totalCost, Integer requestId, String mapName, LocalDate date) {
        String userName = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, requestId).getNameOfOwner();
        double currentUserCash = usersManager.getCurrentUserCashByUserName(userName);
        return new Transaction(Transaction.TransactionType.PaymentTransfer, date, totalCost, currentUserCash, currentUserCash - totalCost);
    }

    public List<MapsTableElementDetailsDto> getMapsTableElementDetailsDto(String userName) {
        List<MapsTableElementDetailsDto> mapsTableElementDetailsDtoList = mapsManager.getAllMapsTableElementsDetailsCheck();
        return mapsTableElementDetailsDtoList;
    }

    public boolean validateUserLoginParams(String userName, String userTpe, StringBuilder errors) {
        return UsersValidations.validateUserLoginParams(userName, userTpe, errors);
    }

    public boolean isUserExist(String userName) {
        return usersManager.isUserExistInTheSystem(userName);
    }

    public String userTapOnTripRequest(String mapName, int requestId) {
        MapEntity mapEntity = mapsManager.getMapEntityByMapName(mapName);
        String htmlGraph = mapEntity.getHtmlGraph();
        TripRequest request = mapEntity.getTripRequestById(requestId);
        String requestSourceStations = request.getSourceStation();
        String requestDestinationStation = request.getDestinationStation();
        return highlightSourceDestStations(mapEntity.getMapDescriptor(), requestSourceStations, requestDestinationStation);
    }

    private String highlightSourceDestStations(MapDescriptor mapDescriptor, String requestSourceStations, String requestDestinationStation) {
        GraphBuilder graphBuilder = new GraphBuilder(mapDescriptor);
        return graphBuilder.buildHtmlGraphSourceDestHighlight(requestSourceStations, requestDestinationStation);
    }

    public String userTapOnTripSuggest(String mapName, int suggestId) {
        MapEntity mapEntity = mapsManager.getMapEntityByMapName(mapName);
        String htmlGraph = mapEntity.getHtmlGraph();
        TripSuggest suggest = mapEntity.getTripSuggestById(suggestId);
        return highlightSuggestRoute(mapEntity.getMapDescriptor(), htmlGraph, suggest.getTripRoute());
    }

    private String highlightSuggestRoute(MapDescriptor mapDescriptor, String htmlGraph, Route tripRoute) {
        GraphBuilder graphBuilder = new GraphBuilder(mapDescriptor);
        return graphBuilder.buildHtmlGraphHighlightRoute(tripRoute);
    }

    public List<TripRequestDto> getAllTripRequestsDto(String mapName, String userName) {
        MapEntity mapEntity = mapsManager.getMapEntityByMapName(mapName);
        if (isUserRequester(userName)) {
            return createRequestDtoListFromMapEntityForUser(mapEntity, userName);
        } else {
            return createRequestDtoListFromMapEntity(mapEntity);
        }
    }

    public List<TripSuggestDto> getAllTripSuggestsDto(String mapName, String userName) {
        MapEntity mapEntity = mapsManager.getMapEntityByMapName(mapName);
        if (isUserRequester(userName)) {
            return createSuggestDtoListFromMapEntityForUser(mapEntity, userName);
        } else {
            return createSuggestDtoListFromMapEntity(mapEntity);
        }
    }

    public List<UserTransactionsHistoryDto> getUserTransactionsByUserName(String userName) {
        User user = usersManager.getUserByName(userName);
        List<Transaction> transactions = user.getUserTransactions();
        return createTransactionsDtoList(transactions);
    }

    private List<UserTransactionsHistoryDto> createTransactionsDtoList(List<Transaction> transactions) {
        List<UserTransactionsHistoryDto> list = new ArrayList<>();

        for (Transaction transaction : transactions) {
            UserTransactionsHistoryDto newDto = new UserTransactionsHistoryDto(transaction.getTransactionType().toString(), transaction.getTransactionDate().toString(), String.valueOf(transaction.getAmountOfTransfer()), String.valueOf(transaction.getBalanceBeforeTransaction()),
                    String.valueOf(transaction.getBalanceAfterTransaction()));
            list.add(newDto);
        }
        return list;
    }

    public String getUserAccountBalance(String userName) {
        User user = usersManager.getUserByName(userName);
        return String.valueOf(user.getCurrentCash());
    }

    public UserDetailsDto getUserDetailsDto(String userName) {
        List<MapsTableElementDetailsDto> mapsTableElementsInfo = mapsManager.getAllMapsTableElementsDetailsCheck();
        String accountBalance = getUserAccountBalance(userName);
        List<UserTransactionsHistoryDto> userTransactions = getUserTransactionsByUserName(userName);
        return new UserDetailsDto(mapsTableElementsInfo, accountBalance, userTransactions);
    }

    public MapPageDto getMapPageDto(String userName, String mapName) {
        MapEntity mapEntity = mapsManager.getMapEntityByMapName(mapName);
        if (isUserRequester(userName)) {
             return new MapPageDto(createRequestDtoListFromMapEntityForUser(mapEntity, userName), createSuggestDtoListFromMapEntity(mapEntity), mapEntity.getHtmlGraph());
        } else {
             return new MapPageDto(createRequestDtoListFromMapEntity(mapEntity), createSuggestDtoListFromMapEntityForUser(mapEntity, userName), mapEntity.getHtmlGraph());
        }
    }

}











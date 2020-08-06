package engine.manager;

import engine.dto.mapPage.PotentialRoadTripDto;
import engine.dto.mapPage.PotentialTripsResponseDto;
import engine.dto.mapPage.TripRequestDto;
import engine.dto.mapPage.TripRequestResponseDto;
import engine.dto.mapPage.TripSuggestDto;
import engine.dto.mapPage.TripSuggestResponseDto;
import engine.dto.userPage.LoadMoneyIntoAccountResponseDto;
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

    public String handleFileUploadProcess(String fileContent, String userName, String mapName) {
        StringBuilder errors = new StringBuilder();
        SchemaBasedJAXBMain schemaBasedJAXBMain = new SchemaBasedJAXBMain();
        InputStream stream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
        TransPool transPool = schemaBasedJAXBMain.deserializeFrom(stream);
        XMLValidationsImpl xmlValidator = new XMLValidationsImpl(transPool);
        List<String> validationErrors = new ArrayList<>();
        if (!xmlValidator.validateXmlFile(validationErrors)) {
            errors = new StringBuilder();
            for (String error : validationErrors) {
                errors.append(error);
            }
        } else {
            mapsManager.createNewMap(transPool.getMapDescriptor(), userName, mapName, errors);
        }

        return errors.toString();
    }

    public TripRequestResponseDto createNewTripRequest(String mapName, String[] inputsArr, String userName) {
        String errorMessage = "";
        RequestValidator requestValidator = new RequestValidator();
        MapEntity mapEntity = mapsManager.getMapEntityByMapName(mapName);
        if (requestValidator.validateTripRequestInput(inputsArr, mapEntity.getMapDescriptor())) {
            TripRequest tripRequest = buildNewRequest(inputsArr);
            mapsManager.addTripRequestByMapName(mapName, tripRequest);
        } else {
            errorMessage = requestValidator.getAddNewTripRequestErrorMessage();
        }

        if (isUserRequester(userName)) {
            List<TripRequestDto> tripRequestResponseDto = createRequestDtoListFromMapEntityForUser(mapEntity, userName);
            return new TripRequestResponseDto(tripRequestResponseDto, errorMessage);
        } else {
            List<TripRequestDto> tripRequestDto = createRequestDtoListFromMapEntity(mapEntity);
            return new TripRequestResponseDto(tripRequestDto, errorMessage);
        }
    }

    private TripRequest buildNewRequest(String[] inputsArr) {
        int hour = Integer.parseInt(inputsArr[3].split(":")[0]);
        int minutes = Integer.parseInt(inputsArr[3].split(":")[1]);
        int day = Integer.parseInt(inputsArr[5]);
        boolean isRequestByStartTime = inputsArr[4].equals("userRequestDeparture");
        return new TripRequest(inputsArr[0], inputsArr[1], inputsArr[2], minutes, hour, day, isRequestByStartTime);
    }


    public TripSuggestResponseDto createNewTripSuggest(String mapName, String[] inputsArr, String userName) {
        String errorMessage = "";
        SuggestValidator suggestValidator = new SuggestValidator();
        MapEntity mapEntity = mapsManager.getMapEntityByMapName(mapName);
        if (suggestValidator.validateTripSuggestInput(inputsArr, mapsManager.getAllLogicStationsByMapName(mapName), mapEntity.getMapDescriptor())) {
            TripSuggest tripSuggest = buildNewSuggest(inputsArr, mapEntity.getMapDescriptor());
            mapsManager.addTripSuggestByMapName(mapName, tripSuggest);
        } else {
            errorMessage = suggestValidator.getAddNewTripSuggestErrorMessage();
        }

        if (isUserRequester(userName)) {
            List<TripSuggestDto> tripSuggestDto = createSuggestDtoListFromMapEntityForUser(mapEntity, userName);
            return new TripSuggestResponseDto(tripSuggestDto, errorMessage);
        } else {
            List<TripSuggestDto> tripSuggestDto = createSuggestDtoListFromMapEntity(mapEntity);
            return new TripSuggestResponseDto(tripSuggestDto, errorMessage);
        }
    }

    private TripSuggest buildNewSuggest(String[] inputsArr, MapDescriptor mapDescriptor) {
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
        return new TripSuggest(inputsArr[0], newTripSuggestRoute, minutes, hour, day, scheduleTypeInt, ppk, tripCapacity, mapDescriptor);
    }

    public void addUser(String userName, String userType) {
        User user = new User(userName, userType);
        usersManager.addNewUser(userName, user);
    }

    public LoadMoneyIntoAccountResponseDto loadMoneyIntoAccount(String userName, String moneyToLoad) {
        String error = UsersValidations.validateLoadMoneyIntoAccountInput(moneyToLoad);
        if(error != "") {
            return new LoadMoneyIntoAccountResponseDto(getUserAccountBalance(userName), error);
        }
        usersManager.loadMoneyIntoUserAccount(userName, Double.parseDouble(moneyToLoad));
        return new LoadMoneyIntoAccountResponseDto(getUserAccountBalance(userName), error);
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
            if (!suggest.getTripOwnerName().equals(userName)) {
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
            if (!request.getNameOfOwner().equals(userName)) {
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

    private void transferMoneyFromRequesterToSuggesters(RoadTrip roadTrip, Integer requestId, String mapName) {
        String requesterUserName = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, requestId).getNameOfOwner();
        User requesterUser = usersManager.getUserByName(requesterUserName);
        for(SubTrip subTrip : roadTrip.getSubTrips()) {
            transferMoneyFromRequesterToSuggester(subTrip, requesterUser, mapName);
            sendNotificationToSuggester(mapName, requestId, roadTrip.getTotalCost(), subTrip.getTrip().getSuggestID());
        }
    }

    private void transferMoneyFromRequesterToSuggester(SubTrip subTrip, User requesterUser, String mapName) {
        int amountToTransfer = subTrip.getCost();
        TripSuggest tripSuggest = subTrip.getTrip();
        String suggesterUerName = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, tripSuggest.getSuggestID()).getTripOwnerName();
        User SuggesterUser = usersManager.getUserByName(suggesterUerName);
        requesterUser.takeMoney(amountToTransfer);
        SuggesterUser.addMoney(amountToTransfer);
        createAndAddAccountTransactions(subTrip, mapName, requesterUser.getUserName(), tripSuggest.getSuggestID());
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

    private void sendNotificationToSuggester(String mapName, Integer requestId, double totalPayment, int suggestId) {
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

    public String rankDriver(String mapName, Integer requestId, String suggestIdStr, String rateNum, String rateLiteral) {
        String error = validateRatingParams(mapName, requestId, rateNum, suggestIdStr);
        if(error != null) {
            return error;
        }
        Integer suggestId = Integer.parseInt(suggestIdStr);
        TripSuggest suggest = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId);
        TripRequest request = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, requestId);
        RoadTrip roadTrip = request.getMatchTrip();
        LinkedList<SubTrip> subTrips = roadTrip.getSubTrips();
        for (SubTrip subTrip : subTrips) {
            if (subTrip.getTrip().getSuggestID() == suggestId) {
                subTrip.setIsRanked(true);
            }
        }
        if (rateLiteral.isEmpty()) {
            suggest.addRatingToDriver(Integer.parseInt(rateNum));
        } else {
            suggest.addRatingToDriver(Integer.parseInt(rateNum), rateLiteral);
        }

        DriverRatingNotification ratingNotification = new DriverRatingNotification(request.getNameOfOwner(), suggestId, Integer.parseInt(rateNum), rateLiteral);
        sendRatingNotification(suggestId, ratingNotification);
        return error;
    }

    private String validateRatingParams(String mapName, Integer requestId, String rateNum, String suggestIdStr) {
        try {
            Integer.parseInt(rateNum);
        }
        catch (Exception ex) {
            return "Rating isn't an integer";
        }
        try {
            Integer.parseInt(suggestIdStr);
        }
        catch (Exception ex) {
            return "Suggest id isn't an integer";
        }
        int suggestId = Integer.parseInt(suggestIdStr);
        if(!getDriversToRating(mapName, requestId).contains(suggestIdStr)) {
            return "Your choice of suggest Id was not found as possible driver rating";
        }

        int num = Integer.parseInt(rateNum);
        if(num < 1 || num > 5) {
            return "Please rating with a number between 1 to 5";
        }
        return null;
    }

    private void sendRatingNotification(Integer suggestId, DriverRatingNotification matchNotificationsDetails) {
        //TODO
    }

    public PotentialTripsResponseDto findPotentialSuggestedTripsToMatch(String mapName, String requestId, String amountOfPotentials) {
        String error="";
        try {
            Integer.parseInt(amountOfPotentials);
        }
        catch (Exception ex) {
            error = "Amount of potentials trip isn't an integer";
            return new PotentialTripsResponseDto(null, error);
        }
        TripRequest request = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, Integer.parseInt(requestId));
        MatchUtil matchUtil = new MatchUtil();
        LinkedList<LinkedList<SubTrip>> potentialRoadTrips = matchUtil.findPotentialMatches(request, Integer.parseInt(amountOfPotentials), mapsManager.getTripSuggestsByMapName(mapName));
        MatchingHelper.updateSubTripsValues(potentialRoadTrips);
        potentialCacheList = MatchingHelper.convertTwoLinkedListToOneRoadTripLinkedList(potentialRoadTrips, request);

        List<PotentialRoadTripDto> potentialRoadTripDto =  MatchingHelper.convertToStr(potentialCacheList, request);
        return new PotentialTripsResponseDto(potentialRoadTripDto, error);
    }

    public String makeMatch(int indexOfRoadTrip, String mapName, Integer requestId) {

        TripRequest tripRequest = mapsManager.getMapTripRequestByMapNameAndRequestId(mapName, requestId);
        RoadTrip roadTrip = potentialCacheList.get(indexOfRoadTrip - 1);
        if (!isRequesterHaveEnoughCash(roadTrip.getTotalCost(), mapName, requestId)) {
            return "Not enough money exception";
        }
        List<TripSuggest> driversInMatch = roadTrip.getAllTripSuggests();
        for(TripSuggest tripSuggest : driversInMatch) {
            tripSuggest.addPassenger(tripRequest.getNameOfOwner());
        }
        tripRequest.setMatched(true);
        tripRequest.setMatchTrip(roadTrip);

        if (tripRequest.isRequestByStartTime()) {
            tripRequest.setArrivalTime(roadTrip.getArrivalTime());
        } else {
            tripRequest.setStartTime(roadTrip.getStartTime());
        }

        transferMoneyFromRequesterToSuggesters(roadTrip, requestId,  mapName);

        updateMapTableEntityDetails(mapName);
        return null;
    }

    private void createAndAddAccountTransactions(SubTrip subTrip, String mapName, String userName, Integer suggestId) {
        LocalDate date = java.time.LocalDate.now();
        Transaction requesterTransaction = createPaymentTransaction(subTrip.getCost(), userName, date);
        usersManager.addTransactionToUserByUserName(userName, requesterTransaction);

        Transaction suggesterTransaction = createReceivingTransaction(subTrip.getCost(), suggestId, mapName, date);
        String suggesterUserName = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId).getTripOwnerName();
        usersManager.addTransactionToUserByUserName(suggesterUserName, suggesterTransaction);
    }

    private Transaction createReceivingTransaction(int totalCost, Integer suggestId, String mapName, LocalDate date) {
        String userName = mapsManager.getMapTripSuggestByMapNameAndSuggestId(mapName, suggestId).getTripOwnerName();
        double currentUserCash = usersManager.getCurrentUserCashByUserName(userName);
        return new Transaction(Transaction.TransactionType.PaymentTransfer, date, totalCost, currentUserCash, currentUserCash - totalCost);
    }

    private Transaction createPaymentTransaction(int totalCost, String userName, LocalDate date) {
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
        List<MapsTableElementDetailsDto> mapsTableElementsInfo = mapsManager.getAllMapsTableElementsDetails();
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

    public List<String> getDriversToRating(String mapName, int requestId) {
        MapEntity mapEntity = mapsManager.getMapEntityByMapName(mapName);
        TripRequest tripRequest = mapEntity.getTripRequestById(requestId);
        return tripRequest.getMatchTrip().getAllSubTripsDriversNamesStillNotRanked();
    }

}











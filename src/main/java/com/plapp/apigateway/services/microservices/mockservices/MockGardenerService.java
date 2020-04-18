package com.plapp.apigateway.services.microservices.mockservices;

/*
public class MockGardenerService implements GardenerService {

    private ScheduleAction jsonToScheduleActon(JSONObject json) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = formatter.parse((String)json.get("date"));
        } catch (Exception e) {

        }

        ScheduleAction action = new ScheduleAction(
                (Long)json.get("userId"),
                (Long)json.get("plantId"),
                date,
                (String)json.get("action"),
                ((Long)json.get("periodicity")).intValue(),
                ((String)json.get("additionalInfo"))

        );

        return action;
    }

    @Override
    public List<ScheduleAction> getSchedule(long plantId) throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-schedule.json").getFile();
        JSONArray schedules = (JSONArray)parser.parse(new FileReader(jsonFile));

        List<ScheduleAction> scheduleActions = Lists.map(
                schedules.iterator(),
                this::jsonToScheduleActon
        );

        List<ScheduleAction> schedule = Lists.filter(
                scheduleActions,
                p -> p.getPlantId() == plantId
        );

        return schedule;
    }

    @Override
    public List<String> getActions()  {
        return Arrays.asList("Watering", "Manure", "Harvest", "Pruning", "Treating");
    }

    @Override
    public ScheduleAction addScheduleAction(ScheduleAction scheduleAction)  {
        return scheduleAction;
    }

    @Override
    public void removeScheduleAction(ScheduleAction scheduleAction)  {

    }
}*/

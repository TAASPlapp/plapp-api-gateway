package com.plapp.apigateway.services.microservices.mockservices;

/*
public class MockGreenhouseService implements GreenhouseService {

    private Plant jsonToPlant(JSONObject jsonPlant) {
        Plant plant = new Plant((Long) jsonPlant.get("id"));
        plant.setDescription((String) jsonPlant.get("description"));
        plant.setOwner((Long) jsonPlant.get("owner"));
        plant.setName((String) jsonPlant.get("name"));
        plant.setType((String) jsonPlant.get("type"));
        plant.setImage((String) jsonPlant.get("image"));
        return plant;
    }

    private Storyboard jsonToStoryboard(JSONObject jsonStoryboard) {
        Storyboard storyboard = new Storyboard();
        if (!jsonStoryboard.isEmpty()) {
            storyboard.setId((Long) jsonStoryboard.get("id"));

            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                storyboard.setLastModified(formatter.parse((String) jsonStoryboard.get("lastModified")));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //storyboard.setNumLikes(((Long) jsonStoryboard.get("numLikes")).intValue());
            storyboard.setSummary((String) jsonStoryboard.get("summary"));

            JSONObject obj = ((JSONObject) (jsonStoryboard.get("plant")));
            Plant p = new Plant((Long) obj.get("id"),
                    (Long) obj.get("owner"),
                    (String) obj.get("name"),
                    (String) obj.get("description"),
                    (String) obj.get("type"),
                    Plant.PlantHealthStatus.valueOf(((String) obj.get("status")).toUpperCase()),
                    (String) obj.get("image"));

            storyboard.setPlant(p);

            List<StoryboardItem> items = Lists.<JSONObject, StoryboardItem>map(
                    ((JSONArray) jsonStoryboard.get("storyboardItems")).iterator(),
                    json -> {
                        StoryboardItem item = new StoryboardItem((Long) json.get("id"));
                        item.setImage((String) json.get("image"));
                        item.setThumbImage((String) json.get("thumbImage"));
                        item.setDescription((String) json.get("description"));
                        item.setTitle((String) json.get("title"));
                        //item.setNumLikes(((Long) json.get("numLikes")).intValue());
                        item.setStatus(Plant.PlantHealthStatus.valueOf(((String) json.get("status")).toUpperCase()));
                        return item;
                    }
            );

            storyboard.setStoryboardItems(items);
        }

        return storyboard;
    }

    @Override
    public List<Plant> getPlants(long userId)  {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-plants.json").getFile();
        List<JSONObject> jsonPlants = Lists.<JSONObject>filter(
                (JSONArray) parser.parse(new FileReader(jsonFile)),
                p -> ((Long) p.get("owner")) == userId
        );

        List<Plant> plants = Lists.map(
                jsonPlants,
                this::jsonToPlant
        );

        return plants;
    }

    @Override
    public Plant getPlant(long plantId)  {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-plants.json").getFile();
        JSONObject jsonPlant = (JSONObject) Lists.<JSONObject>filter(
                (JSONArray) parser.parse(new FileReader(jsonFile)),
                p -> ((Long) p.get("id")) == plantId
        ).get(0);

        return jsonToPlant(jsonPlant);
    }

    @Override
    public Plant addPlant(Plant plant)  {
        return plant;
    }

    @Override
    public void removePlant(Plant plant)  {

    }

    @Override
    public List<Storyboard> getStoryboards()  {
        JSONParser parser = new JSONParser();
        List<Storyboard> storyboards = new ArrayList<>();
        File jsonFile = new ClassPathResource("mock-response/mock-storyboard.json").getFile();
        JSONArray jsonStoryboards = (JSONArray) parser.parse(new FileReader(jsonFile));

        storyboards = Lists.map(
                jsonStoryboards.iterator(),
                this::jsonToStoryboard
        );
        return storyboards;
    }

    @Override
    public Storyboard getStoryboard(long plantId)  {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-storyboard.json").getFile();
        JSONArray storyboards = (JSONArray) parser.parse(new FileReader(jsonFile));
        List<JSONObject> jsonStoryboards = Lists.<JSONObject>filter(
                storyboards.iterator(),
                p -> ((Long) ((JSONObject) p.get("plant")).get("id")) == plantId
        );
        if (!jsonStoryboards.isEmpty())
            return jsonToStoryboard(jsonStoryboards.get(0));
        else return jsonToStoryboard(new JSONObject());
    }

    @Override
    public Storyboard createStoryboard(Storyboard storyboard)  {
        return storyboard;
    }

    @Override
    public void removeStoryboard(Storyboard storyboard)  {

    }

    @Override
    public Storyboard updateStoryboard(Storyboard storyboard)  {
        return storyboard;
    }

    @Override
    public StoryboardItem addStoryboardItem(StoryboardItem storyboardItem)  {
        return storyboardItem;
    }

    @Override
    public void removeStoryboardItem(StoryboardItem storyboardItem)  {

    }
}
*/

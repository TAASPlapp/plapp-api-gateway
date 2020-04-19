package com.plapp.apigateway.services.microservices;

public final class Authorities {

    /* Social service */
    public static final String SOCIAL_USER = "/social/user/([0-9]+)/((\\badd\b)|(\\bupdate\\b)|(\\bdelete\\b))";
    public static final String SOCIAL_COMMENT = "/social/comment/([0-9]+)/(\\badd\\b)";
    public static final String SOCIAL_LIKE = "/social/like/([0-9]+)/((\\badd\\b)|(\\bremove\\b))";

    /* Greenhouse service */
    public static final String GREENHOUSE_PLANTS = "/greenhouse/([0-9]+)/plants/(\\badd\\b)";
    public static final String GREENHOUSE_PLANT = "/greenhouse/plant/([0-9]+)/((\\bremove\\b) || (\\bstoryboard/create\\b))";
    public static final String GREENHOUSE_STORYBOARD = "/greenhouse/storyboard/([0-9]+)/((\\bupdate\\b)|(\bremove\b)|(\\bitem/add\\b))";
    public static final String GREENHOUSE_STORYBOARD_ITEM = "/greenhouse/storyboard/item/([0-9]+)/(\\bremove\\b)";

    /* Gardener service */



    public static final String[] withUserId = {
            SOCIAL_USER,
            GREENHOUSE_PLANTS
    };

    public static final String[] withOtherValues = {
            SOCIAL_COMMENT,
            SOCIAL_LIKE,
            GREENHOUSE_PLANT,
            GREENHOUSE_STORYBOARD,
            GREENHOUSE_STORYBOARD_ITEM
    };
}

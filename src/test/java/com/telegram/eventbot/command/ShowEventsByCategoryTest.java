package com.telegram.eventbot.command;

import com.telegram.eventbot.api.PredictHQClient;
import com.telegram.eventbot.api.PredictHQQuery;
import com.telegram.eventbot.bean.Category;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShowEventsByCategoryTest {

    @Test
    public void findCategory() {
        assertEquals("second word should be extracted as category name", Category.SPORT, ShowEventsByCategory.findCategory("categorysearch sports").get());
        assertEquals("second word should be extracted as category name", Category.SPORT, ShowEventsByCategory.findCategory("categorysearch sports addition words").get());
        assertFalse("Empty value in case of no second word", ShowEventsByCategory.findCategory("categorysearch").isPresent());
    }

    @Test
    public void searchCategoryExist() {
        ShowEventsByCategory showEventsByCategory = new ShowEventsByCategory(new PredictHQClient());
        PredictHQQuery query = (PredictHQQuery) showEventsByCategory.search("category sports");

        assertTrue(query.getQueryParam().containsKey("category"));
        assertEquals("sports", query.getQueryParam().get("category"));
    }

    @Test
    public void t() {
        System.out.println("https://api.predicthq.com/v1/events/?category=sports&limit=5&offset=5&place.exact=ORD&sort=start&start.gte=2019-12-29".hashCode());
    }
}
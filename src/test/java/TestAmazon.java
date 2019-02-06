import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestAmazon extends Base {

    private final static Logger LOGGER = Logger.getLogger(TestAmazon.class.getName());

    // Get quantity of book
    // Test that the result is not null
    @Test
    public void testBooksQuantity() {
        String result = findSingleElement(By.xpath("//span[@id='s-result-count']")).getText();

        LOGGER.info("BOOKS QUANTITY BOOKS QUANTITY BOOKS QUANTITY");
        LOGGER.info(parsingOutput(result, "over", "results"));
        LOGGER.info("BOOKS QUANTITY BOOKS QUANTITY BOOKS QUANTITY");

        Assert.assertNotNull(result);
    }

    // Find a book with longest name (on a first page)
    // Test that list of the books is not empty
    @Test
    public void testBookWithLongestName() {
        List<WebElement> webElements =
                findListElements(By.cssSelector(".a-size-medium.s-inline.s-access-title.a-text-normal"));

        List<String> titles = new ArrayList<>();

        webElements.forEach(a -> titles.add(a.getText()));

        titles.sort(Comparator.comparing(String::length));

        String longestTitle = titles.get(titles.size()-1);

        LOGGER.info("LONGEST TITLE LONGEST TITLE LONGEST TITLE");
        LOGGER.info(longestTitle);
        LOGGER.info("LONGEST TITLE LONGEST TITLE LONGEST TITLE");

        Assert.assertTrue(webElements.size() > 0);
    }

    // Sort results by reviews
    // Find the last one - it will have lowest rating
    // (Pay attention: the tasks was not completely clear,
    // so I decided that the condition here is the same as in the previous test - only on the first page)
    @Test
    public void testBookWithLowestRating() throws InterruptedException {
        sortPageWithDropDown("review-rank");

        Thread.sleep(500);

        driver.navigate().refresh();

        List<WebElement> webElements =
                findListElements(By.cssSelector(".a-size-medium.s-inline.s-access-title.a-text-normal"));

        List<String> titles = new ArrayList<>();

        webElements.forEach(a -> titles.add(a.getText()));

        String lowestRating = titles.get(titles.size()-1);

        LOGGER.info("LOWEST RATING LOWEST RATING LOWEST RATING");
        LOGGER.info(lowestRating);
        LOGGER.info("LOWEST RATING LOWEST RATING LOWEST RATING");

        Assert.assertFalse(lowestRating.isEmpty());
    }
}

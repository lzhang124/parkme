package core.controllers.data;

import java.util.List;

import core.models.data.SearchHistory;
import core.repositories.data.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class SearchHistoryController {

    @Autowired SearchHistoryRepository repo;

    @RequestMapping(value = "/listSearchHistory", method = RequestMethod.GET)
    public List<SearchHistory> listSearchHistory() {
        return repo.findAll();
    }

    @RequestMapping(value = "/searchHistory", method = RequestMethod.GET)
    public SearchHistory searchHistory(@RequestParam(value="accountId", required=false) String accountId) {
        SearchHistory history = repo.findByAccountId(accountId);
        if (history == null) {
            System.out.println("History for account " + accountId + " was not found.");
            return null;
        } else {
            return history;
        }
    }

    @RequestMapping(value = "/addSearchHistory", method = RequestMethod.POST)
    public SearchHistory addSearchHistory(String accountId, double latitude, double longitude) {
        SearchHistory history = repo.findByAccountId(accountId);
        if (history == null) {
            System.out.println("History for account " + accountId + " was not found.");
            return null;
        } else {
            history.addLocation(latitude, longitude);
            repo.save(history);
            return history;
        }
    }

    @RequestMapping(value = "/deleteAllSearchHistory", method = RequestMethod.DELETE)
    public void deleteAllSearchHistory() {
        repo.deleteAll();
        System.out.println("All search history deleted.");
    }

    @RequestMapping(value = "/deleteSearchHistory", method = RequestMethod.DELETE)
    public void deleteSearchHistory(String accountId) {
        SearchHistory history = repo.findByAccountId(accountId);
        if (history == null) {
            System.out.println("History for account " + accountId + " was not found.");
        } else {
            repo.delete(history);
            System.out.println("History for account " + accountId + " deleted.");
        }
    }
}

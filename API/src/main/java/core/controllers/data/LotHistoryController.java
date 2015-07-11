package core.controllers.data;

import java.util.List;

import core.models.data.LotHistory;
import core.repositories.data.LotHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class LotHistoryController {

    @Autowired LotHistoryRepository repo;

    @RequestMapping(value = "/listLotHistory", method = RequestMethod.GET)
    public List<LotHistory> listLotHistory() {
        return repo.findAll();
    }

    @RequestMapping(value = "/lotHistory", method = RequestMethod.GET)
    public LotHistory lotHistory(String lotId) {
        LotHistory history = repo.findByLotId(lotId);
        if (history == null) {
            System.out.println("History for lot " + lotId + " was not found.");
            return null;
        } else {
            return history;
        }
    }

    @RequestMapping(value = "/deleteAllLotHistory", method = RequestMethod.DELETE)
    public void deleteAllLotHistory() {
        repo.deleteAll();
        System.out.println("All lot history deleted.");
    }

    @RequestMapping(value = "/deleteLotHistory", method = RequestMethod.DELETE)
    public void deleteLotHistory(String lotId) {
        LotHistory history = repo.findByLotId(lotId);
        if (history == null) {
            System.out.println("History for lot " + lotId + " was not found.");
        } else {
            repo.delete(history);
            System.out.println("History for lot " + lotId + " deleted.");
        }
    }
}

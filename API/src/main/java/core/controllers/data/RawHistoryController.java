package core.controllers.data;

import java.util.List;

import core.models.data.RawHistory;
import core.repositories.data.RawHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class RawHistoryController {

    @Autowired RawHistoryRepository repo;

    @RequestMapping(value = "/listRawHistory", method = RequestMethod.GET)
    public List<RawHistory> listRawHistory() {
        return repo.findAll();
    }

    @RequestMapping(value = "/rawHistoryByAccount", method = RequestMethod.GET)
    public List<RawHistory> rawHistoryByAccount(String accountId) {
        return repo.findByAccountId(accountId);
    }

    @RequestMapping(value = "/rawHistoryByLot", method = RequestMethod.GET)
    public List<RawHistory> rawHistoryByLot(String lotId) {
        return repo.findByLotId(lotId);
    }

    @RequestMapping(value = "/rawHistoryByDate", method = RequestMethod.GET)
    public List<RawHistory> rawHistoryByDate(long date) {
        return repo.findByDate(date);
    }
}

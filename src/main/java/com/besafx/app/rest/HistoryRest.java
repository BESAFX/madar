package com.besafx.app.rest;

import com.besafx.app.entity.History;
import com.besafx.app.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/history/")
public class HistoryRest {

    private static final String TAG = HistoryRest.class.getSimpleName();

    private final static Logger LOG = LoggerFactory.getLogger(HistoryRest.class);

    @Autowired
    private HistoryService historyService;

    @GetMapping(value = "findDaily", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findDaily() {
        DateTime startTime = new DateTime().withTimeAtStartOfDay();
        DateTime endTime = new DateTime().plusDays(1).withTimeAtStartOfDay();
        LOG.info("GETTING HISTORY WITHIN THIS DAY...");
        LOG.info("Start TIME: " + startTime.toString());
        LOG.info("End TIME  : " + endTime.toString());
        Specifications specifications = Specifications
                .where((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("modifiedDate"), startTime.toDate()))
                .and((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("modifiedDate"), endTime.toDate()));
        Sort sort = new Sort(Sort.Direction.ASC, "modifiedDate");
        List<History> histories = historyService.findAll(specifications, sort);
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), "*"), histories);
    }
}

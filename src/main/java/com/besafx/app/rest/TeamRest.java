package com.besafx.app.rest;

import com.besafx.app.config.CustomException;
import com.besafx.app.entity.Team;
import com.besafx.app.service.TeamService;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationDegree;
import com.besafx.app.ws.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/api/team/")
public class TeamRest {

    private final static Logger LOG = LoggerFactory.getLogger(TeamRest.class);

    private final String FILTER_TABLE = "" +
            "**," +
            "persons[id]";

    @Autowired
    private TeamService teamService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEAM_CREATE')")
    @Transactional
    public String create(@RequestBody Team team) {
        Team topTeam = teamService.findTopByOrderByCodeDesc();
        if (topTeam == null) {
            team.setCode(1);
        } else {
            team.setCode(topTeam.getCode() + 1);
        }
        team = teamService.save(team);
        notificationService.notifyAll(Notification
                                              .builder()
                                              .message("تم انشاء مجموعة صلاحيات جديدة بنجاح")
                                              .type(NotificationDegree.success).build());
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), team);
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEAM_UPDATE')")
    @Transactional
    public String update(@RequestBody Team team) {
        if (teamService.findByCodeAndIdIsNot(team.getCode(), team.getId()) != null) {
            throw new CustomException("هذا الكود مستخدم سابقاً، فضلاً قم بتغير الكود.");
        }
        Team object = teamService.findOne(team.getId());
        if (object != null) {
            team = teamService.save(team);
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم تعديل بيانات المجموعة بنجاح")
                                                  .type(NotificationDegree.success).build());
            return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), team);
        } else {
            return null;
        }
    }

    @DeleteMapping(value = "delete/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEAM_DELETE')")
    @Transactional
    public void delete(@PathVariable Long id) {
        Team team = teamService.findOne(id);
        if (team != null) {
            if (!team.getPersons().isEmpty()) {
                throw new CustomException("لا يمكن حذف هذة المجموعة لإعتماد بعض المستخدمين عليها.");
            }
            teamService.delete(id);
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم حذف مجموعة الصلاحيات بنجاح")
                                                  .type(NotificationDegree.error).build());
        }
    }

    @GetMapping(value = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findAll() {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       Lists.newArrayList(teamService.findAll()));
    }

    @GetMapping(value = "findOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findOne(@PathVariable Long id) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       teamService.findOne(id));
    }
}

package com.besafx.app.auditing;


import com.besafx.app.component.BeanUtil;
import com.besafx.app.entity.History;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
public class EntityHistoryListener {

    private static final Logger LOG = LoggerFactory.getLogger(EntityHistoryListener.class);

    @Transactional(Transactional.TxType.MANDATORY)
    public void perform(String note) {
        try {
            EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
            History history = new History();
            history.setNote(note);
            entityManager.persist(history);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }
}

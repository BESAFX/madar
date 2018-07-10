package com.besafx.app.rest;

import com.besafx.app.config.DropboxManager;
import com.besafx.app.entity.Attach;
import com.besafx.app.entity.Contract;
import com.besafx.app.entity.ContractAttach;
import com.besafx.app.service.AttachService;
import com.besafx.app.service.ContractAttachService;
import com.besafx.app.service.ContractService;
import com.besafx.app.service.PersonService;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationDegree;
import com.besafx.app.ws.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping(value = "/api/contractAttach/")
public class ContractAttachRest {

    private final static Logger log = LoggerFactory.getLogger(ContractAttachRest.class);

    public static final String FILTER_TABLE = "" +
            "id," +
            "attach[**,person[id,contact[id,shortName]]]," +
            "contract[id]";

    @Autowired
    private PersonService personService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractAttachService contractAttachService;

    @Autowired
    private AttachService attachService;

    @Autowired
    private DropboxManager dropboxManager;

    @Autowired
    private NotificationService notificationService;

    @PostMapping(value = "upload", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTRACT_CREATE')")
    public String upload(
            @RequestParam(value = "contractId") Long contractId,
            @RequestParam(value = "files") MultipartFile[] files,
            Principal principal) throws Exception {

        List<ContractAttach> contractAttaches = new ArrayList<>();
        Contract contract = contractService.findOne(contractId);

        ListIterator<MultipartFile> multipartFileListIterator = Lists.newArrayList(files).listIterator();
        while (multipartFileListIterator.hasNext()) {
            MultipartFile file = multipartFileListIterator.next();
            Attach attach = new Attach();
            attach.setName(FilenameUtils.removeExtension(file.getOriginalFilename()));
            attach.setMimeType(FilenameUtils.getExtension(file.getOriginalFilename()));
            attach.setDescription("");
            attach.setSize(file.getSize());
            attach.setDate(new DateTime().toDate());
            attach.setPerson(personService.findByEmail(principal.getName()));

            String path = "/Madar/Contract_Attaches/" + contractId + "/" + attach.getName() + "." + attach.getMimeType();

            Future<Boolean> uploadContract = dropboxManager.uploadFile(file, path);
            if (uploadContract.get()) {
                Future<String> shareContract = dropboxManager.shareFile(path);
                attach.setLink(shareContract.get());
                attach = attachService.save(attach);

                ContractAttach contractAttach = new ContractAttach();
                contractAttach.setAttach(attach);
                contractAttach.setContract(contract);
                contractAttaches.add(contractAttachService.save(contractAttach));

                notificationService.notifyOne(Notification.builder()
                                                          .message("تم رفع الملف بنجاح")
                                                          .type(NotificationDegree.success)
                                                          .build(), principal.getName());
            }
        }
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), contractAttaches);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTRACT_DELETE')")
    public Boolean delete(@PathVariable Long id, Principal principal) throws ExecutionException, InterruptedException {
        ContractAttach contractAttach = contractAttachService.findOne(id);
        if (contractAttach != null) {

            String path = "/Madar/Contract_Attaches/" +
                    contractAttach.getContract().getId() +
                    "/" + contractAttach.getAttach().getName() +
                    "." +
                    contractAttach.getAttach().getMimeType();

            Future<Boolean> deleteContract = dropboxManager.deleteFile(path);
            if (deleteContract.get()) {
                contractAttachService.delete(contractAttach);
                attachService.delete(contractAttach.getAttach());
                notificationService.notifyOne(Notification.builder()
                                                          .message("تم حذف الملف بنجاح")
                                                          .type(NotificationDegree.error).build(), principal.getName());
                return true;
            } else {
                notificationService.notifyOne(Notification.builder()
                                                          .message("لا يمكن حذف الملف حيث يبدو غير موجود")
                                                          .type(NotificationDegree.error).build(), principal.getName());
                return false;
            }
        } else {
            return false;
        }
    }

    @DeleteMapping(value = "deleteWhatever/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTRACT_DELETE')")
    public void deleteWhatever(@PathVariable Long id, Principal principal) {
        ContractAttach contractAttach = contractAttachService.findOne(id);
        if (contractAttach != null) {
            contractAttachService.delete(contractAttach);
            attachService.delete(contractAttach.getAttach());
            notificationService.notifyOne(Notification.builder()
                                                      .message("تم حذف الملف بنجاح")
                                                      .type(NotificationDegree.success).build(), principal.getName());
        }
    }

    @GetMapping(value = "findByContract/{contractId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findByContract(@PathVariable(value = "contractId") Long contractId) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), contractAttachService.findByContractId(contractId));
    }
}

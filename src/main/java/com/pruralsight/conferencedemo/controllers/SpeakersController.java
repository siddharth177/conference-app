package com.pruralsight.conferencedemo.controllers;

import com.pruralsight.conferencedemo.models.Session;
import com.pruralsight.conferencedemo.models.Speaker;
import com.pruralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/speakers")
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping     //get
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id) {
        return speakerRepository.getOne(id);
    }

    @PostMapping    //create
    public Speaker create(@RequestBody final Speaker speaker) {
        return speakerRepository.saveAndFlush(speaker);     //save and flush all at once
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Session session) {
        Speaker existingSpeaker = speakerRepository.getOne(id);
        BeanUtils.copyProperties(session, existingSpeaker, "session_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)      //requires http delete verb to be present in the api
    public void delete(@PathVariable Long id) {
        //Also need to check for children records before deleting.
        //cascade
        //this will only delete speaker repository without any children in it
        speakerRepository.deleteById(id);
    }
}

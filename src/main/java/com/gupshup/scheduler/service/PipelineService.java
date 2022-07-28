package com.gupshup.scheduler.service;

import com.gupshup.scheduler.model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PipelineService {

    private volatile List<Message> pipeline = new ArrayList();

    public void addMessages (List<Message> messages) {
        // Check if message exists in pipeline
        List<Message> newMessages = messages.stream()
                .filter(msg -> !pipeline.stream()
                        .map(m -> m.getId()).collect(Collectors.toList())
                        .contains(msg.getId()))
                .collect(Collectors.toList());

        pipeline.addAll(newMessages);
        System.out.println("Pipeline: " + pipeline);
    }

    public boolean addMessage (Message message) {
        return pipeline.add(message);
    }

    public boolean removeMessage (Message message) {
        return pipeline.removeIf(x -> x.getId().get().equals(message.getId().get()));
    }

    public List<Message> getPipeline() {
        return pipeline;
    }

}

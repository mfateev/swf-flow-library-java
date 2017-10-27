/*
 * Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazonaws.services.simpleworkflow.flow;

import com.amazonaws.services.simpleworkflow.flow.pojo.POJOWorkflowDefinitionFactoryFactory;
import com.amazonaws.services.simpleworkflow.flow.worker.AsyncDecisionTaskHandler;
import com.amazonaws.services.simpleworkflow.flow.worker.DecisionTaskPoller;
import com.uber.cadence.WorkflowService;

import java.util.Collection;


public class SynchronousWorkflowWorker {

    private final DecisionTaskPoller poller;

    private final POJOWorkflowDefinitionFactoryFactory factoryFactory = new POJOWorkflowDefinitionFactoryFactory();

    public SynchronousWorkflowWorker() {
        poller = new DecisionTaskPoller();
        poller.setDecisionTaskHandler(new AsyncDecisionTaskHandler(factoryFactory));
    }
    
    public SynchronousWorkflowWorker(WorkflowService.Iface service, String domain, String taskListToPoll) {
        poller = new DecisionTaskPoller(service, domain, taskListToPoll, new AsyncDecisionTaskHandler(factoryFactory));
    }

    public String getIdentity() {
        return poller.getIdentity();
    }

    public void setIdentity(String identity) {
        poller.setIdentity(identity);
    }

    public WorkflowService.Iface getService() {
        return poller.getService();
    }

    public String getDomain() {
        return poller.getDomain();
    }

    public void setService(WorkflowService.Iface service) {
        poller.setService(service);
    }

    public void setDomain(String domain) {
        poller.setDomain(domain);
    }

    public String getTaskListToPoll() {
        return poller.getTaskListToPoll();
    }

    public void setTaskListToPoll(String pollTaskList) {
        poller.setTaskListToPoll(pollTaskList);
    }

    public boolean pollAndProcessSingleDecisionTask() throws Exception {
        return poller.pollAndProcessSingleTask();
    }

    public void setConverter(DataConverter converter) {
        factoryFactory.setDataConverter(converter);
    }

    public void addWorkflowImplementationType(Class<?> workflowImplementationType)
            throws InstantiationException, IllegalAccessException {
        factoryFactory.addWorkflowImplementationType(workflowImplementationType);
    }

    public void addWorkflowImplementationType(Class<?> workflowImplementationType, DataConverter converterOverride)
            throws InstantiationException, IllegalAccessException {
        factoryFactory.addWorkflowImplementationType(workflowImplementationType, converterOverride);
    }

    public void setWorkflowImplementationTypes(Collection<Class<?>> workflowImplementationTypes)
            throws InstantiationException, IllegalAccessException {
        factoryFactory.setWorkflowImplementationTypes(workflowImplementationTypes);
    }

    public Collection<Class<?>> getWorkflowImplementationTypes() {
        return factoryFactory.getWorkflowImplementationTypes();
    }
 
}

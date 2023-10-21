package com.eth.refiq.data.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.5.0.
 */
@SuppressWarnings("rawtypes")
public class Topics extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ADDMODERATOR = "addModerator";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_AWARDCONTENT = "awardContent";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_CREATECOMMENT = "createComment";

    public static final String FUNC_CREATEPOST = "createPost";

    public static final String FUNC_CREATETOPIC = "createTopic";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_REMOVECONTENT = "removeContent";

    public static final String FUNC_REMOVEMODERATOR = "removeModerator";

    public static final String FUNC_safeTransferFrom = "safeTransferFrom";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOKENURI = "tokenURI";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_UPDATETOPICINFO = "updateTopicInfo";

    public static final String FUNC_UPDATETOPICPOLICY = "updateTopicPolicy";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event COMMENTCREATED_EVENT = new Event("CommentCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event CONTENTAWARDED_EVENT = new Event("ContentAwarded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CONTENTREMOVED_EVENT = new Event("ContentRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event MODERATORADDED_EVENT = new Event("ModeratorAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event MODERATORREMOVED_EVENT = new Event("ModeratorRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event POSTCREATED_EVENT = new Event("PostCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event TOPICCREATED_EVENT = new Event("TopicCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event TOPICINFOUPDATED_EVENT = new Event("TopicInfoUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event TOPICPOLICYUPDATED_EVENT = new Event("TopicPolicyUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected Topics(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Topics(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Topics(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Topics(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(APPROVAL_EVENT, log));
        }
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
        ApprovalEventResponse typedResponse = new ApprovalEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalEventFromLog(log));
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public static List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, log));
        }
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalForAllEventResponse getApprovalForAllEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, log);
        ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalForAllEventFromLog(log));
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public static List<CommentCreatedEventResponse> getCommentCreatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(COMMENTCREATED_EVENT, log));
        }
        ArrayList<CommentCreatedEventResponse> responses = new ArrayList<CommentCreatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CommentCreatedEventResponse typedResponse = new CommentCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contentId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.parentId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.author = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.contentCid = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CommentCreatedEventResponse getCommentCreatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(COMMENTCREATED_EVENT, log);
        CommentCreatedEventResponse typedResponse = new CommentCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.contentId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.parentId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.author = (String) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.contentCid = (String) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<CommentCreatedEventResponse> commentCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCommentCreatedEventFromLog(log));
    }

    public Flowable<CommentCreatedEventResponse> commentCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(COMMENTCREATED_EVENT));
        return commentCreatedEventFlowable(filter);
    }

    public static List<ContentAwardedEventResponse> getContentAwardedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(CONTENTAWARDED_EVENT, log));
        }
        ArrayList<ContentAwardedEventResponse> responses = new ArrayList<ContentAwardedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ContentAwardedEventResponse typedResponse = new ContentAwardedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contentId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ContentAwardedEventResponse getContentAwardedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CONTENTAWARDED_EVENT, log);
        ContentAwardedEventResponse typedResponse = new ContentAwardedEventResponse();
        typedResponse.log = log;
        typedResponse.contentId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ContentAwardedEventResponse> contentAwardedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getContentAwardedEventFromLog(log));
    }

    public Flowable<ContentAwardedEventResponse> contentAwardedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTENTAWARDED_EVENT));
        return contentAwardedEventFlowable(filter);
    }

    public static List<ContentRemovedEventResponse> getContentRemovedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(CONTENTAWARDED_EVENT, log));
        }
        ArrayList<ContentRemovedEventResponse> responses = new ArrayList<ContentRemovedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ContentRemovedEventResponse typedResponse = new ContentRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contentId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ContentRemovedEventResponse getContentRemovedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CONTENTREMOVED_EVENT, log);
        ContentRemovedEventResponse typedResponse = new ContentRemovedEventResponse();
        typedResponse.log = log;
        typedResponse.contentId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ContentRemovedEventResponse> contentRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getContentRemovedEventFromLog(log));
    }

    public Flowable<ContentRemovedEventResponse> contentRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTENTREMOVED_EVENT));
        return contentRemovedEventFlowable(filter);
    }

    public static List<ModeratorAddedEventResponse> getModeratorAddedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(MODERATORADDED_EVENT, log));
        }
        ArrayList<ModeratorAddedEventResponse> responses = new ArrayList<ModeratorAddedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ModeratorAddedEventResponse typedResponse = new ModeratorAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.moderator = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ModeratorAddedEventResponse getModeratorAddedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MODERATORADDED_EVENT, log);
        ModeratorAddedEventResponse typedResponse = new ModeratorAddedEventResponse();
        typedResponse.log = log;
        typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.moderator = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ModeratorAddedEventResponse> moderatorAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getModeratorAddedEventFromLog(log));
    }

    public Flowable<ModeratorAddedEventResponse> moderatorAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MODERATORADDED_EVENT));
        return moderatorAddedEventFlowable(filter);
    }

    public static List<ModeratorRemovedEventResponse> getModeratorRemovedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(MODERATORREMOVED_EVENT, log));
        }
        ArrayList<ModeratorRemovedEventResponse> responses = new ArrayList<ModeratorRemovedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ModeratorRemovedEventResponse typedResponse = new ModeratorRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.moderator = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ModeratorRemovedEventResponse getModeratorRemovedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MODERATORREMOVED_EVENT, log);
        ModeratorRemovedEventResponse typedResponse = new ModeratorRemovedEventResponse();
        typedResponse.log = log;
        typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.moderator = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ModeratorRemovedEventResponse> moderatorRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getModeratorRemovedEventFromLog(log));
    }

    public Flowable<ModeratorRemovedEventResponse> moderatorRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MODERATORREMOVED_EVENT));
        return moderatorRemovedEventFlowable(filter);
    }

    public static List<PostCreatedEventResponse> getPostCreatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(POSTCREATED_EVENT, log));
        }
        ArrayList<PostCreatedEventResponse> responses = new ArrayList<PostCreatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            PostCreatedEventResponse typedResponse = new PostCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contentId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.author = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.contentCid = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PostCreatedEventResponse getPostCreatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(POSTCREATED_EVENT, log);
        PostCreatedEventResponse typedResponse = new PostCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.contentId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.author = (String) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.contentCid = (String) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<PostCreatedEventResponse> postCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPostCreatedEventFromLog(log));
    }

    public Flowable<PostCreatedEventResponse> postCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(POSTCREATED_EVENT));
        return postCreatedEventFlowable(filter);
    }

    public static List<TopicCreatedEventResponse> getTopicCreatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(TOPICCREATED_EVENT, log));
        }
        ArrayList<TopicCreatedEventResponse> responses = new ArrayList<TopicCreatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TopicCreatedEventResponse typedResponse = new TopicCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.infoCid = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TopicCreatedEventResponse getTopicCreatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TOPICCREATED_EVENT, log);
        TopicCreatedEventResponse typedResponse = new TopicCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.infoCid = (String) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<TopicCreatedEventResponse> topicCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTopicCreatedEventFromLog(log));
    }

    public Flowable<TopicCreatedEventResponse> topicCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOPICCREATED_EVENT));
        return topicCreatedEventFlowable(filter);
    }

    public static List<TopicInfoUpdatedEventResponse> getTopicInfoUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(TOPICINFOUPDATED_EVENT, log));
        }
        ArrayList<TopicInfoUpdatedEventResponse> responses = new ArrayList<TopicInfoUpdatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TopicInfoUpdatedEventResponse typedResponse = new TopicInfoUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.infoCid = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TopicInfoUpdatedEventResponse getTopicInfoUpdatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TOPICINFOUPDATED_EVENT, log);
        TopicInfoUpdatedEventResponse typedResponse = new TopicInfoUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.infoCid = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<TopicInfoUpdatedEventResponse> topicInfoUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTopicInfoUpdatedEventFromLog(log));
    }

    public Flowable<TopicInfoUpdatedEventResponse> topicInfoUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOPICINFOUPDATED_EVENT));
        return topicInfoUpdatedEventFlowable(filter);
    }

    public static List<TopicPolicyUpdatedEventResponse> getTopicPolicyUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(TOPICPOLICYUPDATED_EVENT, log));
        }
        ArrayList<TopicPolicyUpdatedEventResponse> responses = new ArrayList<TopicPolicyUpdatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TopicPolicyUpdatedEventResponse typedResponse = new TopicPolicyUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.ownerShare = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.moderatorsShare = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TopicPolicyUpdatedEventResponse getTopicPolicyUpdatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TOPICPOLICYUPDATED_EVENT, log);
        TopicPolicyUpdatedEventResponse typedResponse = new TopicPolicyUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.topicId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.ownerShare = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.moderatorsShare = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<TopicPolicyUpdatedEventResponse> topicPolicyUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTopicPolicyUpdatedEventFromLog(log));
    }

    public Flowable<TopicPolicyUpdatedEventResponse> topicPolicyUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOPICPOLICYUPDATED_EVENT));
        return topicPolicyUpdatedEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            valueList.add(staticExtractEventParametersWithLog(TRANSFER_EVENT, log));
        }
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferEventResponse getTransferEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
        TransferEventResponse typedResponse = new TransferEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addModerator(BigInteger topicId, String moderator) {
        final Function function = new Function(
                FUNC_ADDMODERATOR, 
                Arrays.<Type>asList(new Uint256(topicId),
                new Address(160, moderator)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new Address(160, to),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> awardContent(BigInteger contentId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_AWARDCONTENT, 
                Arrays.<Type>asList(new Uint256(contentId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String owner) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new Address(160, owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> createComment(BigInteger parentId, String contentCid) {
        final Function function = new Function(
                FUNC_CREATECOMMENT, 
                Arrays.<Type>asList(new Uint256(parentId),
                new Utf8String(contentCid)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createPost(BigInteger topicId, String contentCid) {
        final Function function = new Function(
                FUNC_CREATEPOST, 
                Arrays.<Type>asList(new Uint256(topicId),
                new Utf8String(contentCid)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createTopic(String name, String infoCid) {
        final Function function = new Function(
                FUNC_CREATETOPIC, 
                Arrays.<Type>asList(new Utf8String(name),
                new Utf8String(infoCid)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getApproved(BigInteger tokenId) {
        final Function function = new Function(FUNC_GETAPPROVED, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String owner, String operator) {
        final Function function = new Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new Address(160, owner),
                new Address(160, operator)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ownerOf(BigInteger tokenId) {
        final Function function = new Function(FUNC_OWNEROF, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeContent(BigInteger contentId) {
        final Function function = new Function(
                FUNC_REMOVECONTENT, 
                Arrays.<Type>asList(new Uint256(contentId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeModerator(BigInteger topicId, String moderator) {
        final Function function = new Function(
                FUNC_REMOVEMODERATOR, 
                Arrays.<Type>asList(new Uint256(topicId),
                new Address(160, moderator)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, byte[] data) {
        final Function function = new Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId),
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator, Boolean approved) {
        final Function function = new Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new Address(160, operator),
                new Bool(approved)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> tokenURI(BigInteger tokenId) {
        final Function function = new Function(FUNC_TOKENURI, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateTopicInfo(BigInteger topicId, String infoCid) {
        final Function function = new Function(
                FUNC_UPDATETOPICINFO, 
                Arrays.<Type>asList(new Uint256(topicId),
                new Utf8String(infoCid)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateTopicPolicy(BigInteger topicId, BigInteger ownerShare, BigInteger moderatorsShare) {
        final Function function = new Function(
                FUNC_UPDATETOPICPOLICY, 
                Arrays.<Type>asList(new Uint256(topicId),
                new Uint8(ownerShare),
                new Uint8(moderatorsShare)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Topics load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Topics(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Topics load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Topics(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Topics load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Topics(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Topics load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Topics(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String owner;

        public String operator;

        public Boolean approved;
    }

    public static class CommentCreatedEventResponse extends BaseEventResponse {
        public BigInteger contentId;

        public BigInteger parentId;

        public String author;

        public String contentCid;
    }

    public static class ContentAwardedEventResponse extends BaseEventResponse {
        public BigInteger contentId;

        public BigInteger amount;
    }

    public static class ContentRemovedEventResponse extends BaseEventResponse {
        public BigInteger contentId;
    }

    public static class ModeratorAddedEventResponse extends BaseEventResponse {
        public BigInteger topicId;

        public String moderator;
    }

    public static class ModeratorRemovedEventResponse extends BaseEventResponse {
        public BigInteger topicId;

        public String moderator;
    }

    public static class PostCreatedEventResponse extends BaseEventResponse {
        public BigInteger contentId;

        public BigInteger topicId;

        public String author;

        public String contentCid;
    }

    public static class TopicCreatedEventResponse extends BaseEventResponse {
        public BigInteger topicId;

        public String name;

        public String infoCid;
    }

    public static class TopicInfoUpdatedEventResponse extends BaseEventResponse {
        public BigInteger topicId;

        public String infoCid;
    }

    public static class TopicPolicyUpdatedEventResponse extends BaseEventResponse {
        public BigInteger topicId;

        public BigInteger ownerShare;

        public BigInteger moderatorsShare;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger tokenId;
    }
}

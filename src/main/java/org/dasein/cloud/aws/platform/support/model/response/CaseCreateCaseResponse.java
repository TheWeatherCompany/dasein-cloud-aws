package org.dasein.cloud.aws.platform.support.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Eugene Yaroslavtsev
 * @since 15.08.2014
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseCreateCaseResponse {

    @JsonProperty(value = "caseId")
    private String caseId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}

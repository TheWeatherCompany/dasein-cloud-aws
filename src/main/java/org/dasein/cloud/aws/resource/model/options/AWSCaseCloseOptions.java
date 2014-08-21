package org.dasein.cloud.aws.resource.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.resource.model.options.TicketCloseOptions;

/**
 * @author Eugene Yaroslavtsev
 * @since 15.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class AWSCaseCloseOptions {

//    @JsonProperty(value = "caseId", required = true)
//    private String caseId;

    @JsonIgnore
    private TicketCloseOptions _options;

    private AWSCaseCloseOptions() {
    }

    private AWSCaseCloseOptions(TicketCloseOptions _options) {
        this._options = _options;
    }

    public static AWSCaseCloseOptions getInstance(TicketCloseOptions options) {
        return new AWSCaseCloseOptions(options);
    }

    @JsonProperty(value = "caseId", required = true)
    public String getCaseId() {
        return _options.getTicketId();
    }
}

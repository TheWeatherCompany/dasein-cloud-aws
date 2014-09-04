package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.options.TicketCloseOptions;

/**
 * @author Eugene Yaroslavtsev
 * @since 15.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class CaseCloseOptions {

    @JsonIgnore
    private TicketCloseOptions _options;

    private CaseCloseOptions() {
    }

    private CaseCloseOptions( TicketCloseOptions _options ) {
        this._options = _options;
    }

    public static CaseCloseOptions getInstance( TicketCloseOptions options ) {
        return new CaseCloseOptions(options);
    }

    @JsonProperty(value = "caseId", required = true)
    public String getCaseId() {
        return _options.getTicketId();
    }
}

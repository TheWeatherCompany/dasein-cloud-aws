package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.options.TicketListServicesOptions;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class CaseListServicesOptions {

    @JsonIgnore
    private TicketListServicesOptions _options;

    private CaseListServicesOptions() {
    }

    private CaseListServicesOptions( TicketListServicesOptions _options ) {
        this._options = _options;
    }

    public static CaseListServicesOptions getInstance( TicketListServicesOptions options ) {
        return new CaseListServicesOptions(options);
    }

    @JsonProperty(value = "language")
    public String getLanguage() {
        return _options.getLanguage();
    }

    @JsonProperty(value = "serviceCodeList")
    public String[] getServiceCodeList() {
        return _options.getServiceCodeList();
    }
}

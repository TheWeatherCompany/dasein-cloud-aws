package org.dasein.cloud.aws.resource.model.options;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.resource.model.options.TicketListServicesOptions;

import java.util.Collection;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class AWSCaseListServicesOptions {

//    @JsonProperty(value = "language")
//    String language;
//
//    @JsonProperty(value = "serviceCodeList")
//    Collection<String> serviceCodeList;

    private TicketListServicesOptions _options;

    private AWSCaseListServicesOptions() {
    }

    private AWSCaseListServicesOptions(TicketListServicesOptions _options) {
        this._options = _options;
    }

    public static AWSCaseListServicesOptions getInstance(TicketListServicesOptions options) {
       return new AWSCaseListServicesOptions(options);
    }

    @JsonProperty(value = "language")
    public String getLanguage() {
        return _options.getLanguage();
    }

    @JsonProperty(value = "serviceCodeList")
    public Collection<String> getServiceCodeList() {
        return _options.getServiceCodeList();
    }
}

package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.options.TicketAttachmentDataOptions;

/**
 * @author Eugene Yaroslavtsev
 * @since 19.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class CaseAttachmentDataOptions {

    @JsonIgnore
    private TicketAttachmentDataOptions _options;

    private CaseAttachmentDataOptions() {
    }

    private CaseAttachmentDataOptions(TicketAttachmentDataOptions options) {
        this._options = options;
    }

    public static CaseAttachmentDataOptions getInstance(TicketAttachmentDataOptions data) {
        return new CaseAttachmentDataOptions(data);
    }

    @JsonProperty("data")
    public byte[] getData() {
        return _options.getData();
    }

    @JsonProperty("fileName")
    public String getFileName() {
        return _options.getFileName();
    }
}

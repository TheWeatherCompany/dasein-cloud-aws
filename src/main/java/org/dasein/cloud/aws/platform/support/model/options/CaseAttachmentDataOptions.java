package org.dasein.cloud.aws.platform.support.model.options;

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
//    @JsonProperty("data")
//    private String data;
//    @JsonProperty("fileName")
//    private String fileName;

    private TicketAttachmentDataOptions _data;

    private CaseAttachmentDataOptions() {
    }

    private CaseAttachmentDataOptions(TicketAttachmentDataOptions _data) {
        this._data = _data;
    }

    public static CaseAttachmentDataOptions getInstance(TicketAttachmentDataOptions data) {
        return new CaseAttachmentDataOptions(data);
    }

    @JsonProperty("data")
    public String getData() {
        return _data.getData();
    }

    @JsonProperty("fileName")
    public String getFileName() {
        return _data.getFileName();
    }
}
